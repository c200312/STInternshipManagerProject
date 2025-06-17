<template>
  <div>
    <UserHeader :username="teacherInfo.username"/>

    <el-container class="main-container">
      <el-aside width="200px">
        <el-menu :default-active="currentView" class="menu-container">
          <el-menu-item index="assessment" @click="handleViewChange('assessment')">
            <el-icon>
              <Star/>
            </el-icon>
            <span>评分管理</span>
          </el-menu-item>
          <el-menu-item index="diary" @click="handleViewChange('diary')">
            <el-icon>
              <Document/>
            </el-icon>
            <span>周记管理</span>
          </el-menu-item>
          <el-menu-item index="enterprise" @click="handleViewChange('enterprise')">
            <el-icon>
              <OfficeBuilding/>
            </el-icon>
            <span>企业信息管理</span>
          </el-menu-item>
          <el-menu-item index="report" @click="handleViewChange('report')">
            <el-icon>
              <Document/>
            </el-icon>
            <span>下载报告</span>
          </el-menu-item>

          <!-- 下载所有学生报告按钮 -->
          <div class="download-button-container">
            <el-button
                type="primary"
                :loading="downloading"
                @click="downloadAllReports"
                class="download-all-btn"
                :icon="Download"
            >
              下载所有学生报告
            </el-button>
          </div>

          <!-- 电子签名按钮 -->
          <div class="signature-button-container">
            <el-button
                type="success"
                @click="openSignatureDialog"
                class="signature-btn"
                :icon="Edit"
            >
              电子签名管理
            </el-button>
          </div>

        </el-menu>
      </el-aside>

      <el-container>
        <!-- 学生列表区域 -->
        <el-aside v-if="currentView" width="250px" class="student-list-aside">
          <div class="student-list-header">
            <h3>学生列表</h3>
          </div>
          <el-menu
              :default-active="selectedStudentId"
              class="student-menu"
          >
            <el-menu-item
                v-for="studentView in studentList"
                :key="studentView.student.s_id"
                :index="studentView.student.s_id.toString()"
                @click="handleStudentSelect(studentView)"
            >
              {{ studentView.student.student_name }}
            </el-menu-item>
          </el-menu>
        </el-aside>

        <!-- 主要内容区域 -->
        <el-main>
          <!-- 学生基本信息展示区域 -->
          <el-card v-if="selectedStudentView" class="student-info-card">
            <h3>学生基本信息</h3>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="姓名">{{ selectedStudentView.student.student_name }}</el-descriptions-item>
              <el-descriptions-item label="学号">{{ selectedStudentView.student.student_number }}</el-descriptions-item>
              <el-descriptions-item label="班级">{{ selectedStudentView.student.stu_class }}</el-descriptions-item>
              <el-descriptions-item label="实习单位">{{ enterpriseInfo.company_name || '无' }}</el-descriptions-item>
            </el-descriptions>
          </el-card>

          <!-- 功能面板区域 -->
          <div class="panel-container">
            <StudentDetailPanel
                v-if="currentView === 'diary' && selectedStudentView"
                :studentView="selectedStudentView"
            />
            <StudentAssessmentPanel
                v-else-if="currentView === 'assessment' && selectedStudentView"
                :students="studentList"
                :selectedStudent="selectedStudentView"
            />
            <EnterpriseInfoPanel
                v-else-if="currentView === 'enterprise' && selectedStudentView"
                :student="selectedStudentView?.student"
            />
            <div v-else-if="currentView === 'report' && selectedStudentView" class="report-panel">
              <el-card>
                <template #header>
                  <div class="card-header">
                    <span>Word报告管理</span>
                  </div>
                </template>

                <div class="report-content">
                  <!-- 文档状态检查 -->
                  <el-row :gutter="20" class="status-section">
                    <el-col :span="24">
                      <el-alert
                          v-if="documentStatus === 'exists'"
                          title="文档状态：存在Word报告文档 可下载"
                          type="success"
                          :closable="false"
                          show-icon>
                      </el-alert>
                      <el-alert
                          v-else-if="documentStatus === 'not-found'"
                          title="文档状态：不存在Word报告文档"
                          type="warning"
                          :closable="false"
                          show-icon>
                      </el-alert>
                    </el-col>
                  </el-row>

                  <!-- 操作按钮区域 -->
                  <el-row :gutter="20" class="action-section">
                    <el-col :span="24">
                      <el-button
                          type="success"
                          @click="downloadStudentReport"
                          :disabled="documentStatus !== 'exists'"
                          :loading="downloading"
                          :icon="Download">
                        下载Word报告
                      </el-button>
                    </el-col>
                  </el-row>
                </div>
              </el-card>
            </div>
            <div v-else class="no-selection">
              <p>请选择要查看的学生</p>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>

    <!-- 电子签名对话框 -->
    <el-dialog
        v-model="signatureDialogVisible"
        title="电子签名管理"
        width="600px"
        :close-on-click-modal="false"
    >
      <div class="signature-container">
        <!-- 当前签名显示 -->
        <div v-if="currentSignatureUrl" class="current-signature">
          <h4>当前签名</h4>
          <img :src="currentSignatureUrl" alt="当前签名" class="signature-image"/>
        </div>

        <!-- 上传新签名 -->
        <div class="upload-area">
          <h4>{{ currentSignatureUrl ? '更新签名' : '上传签名' }}</h4>
          <el-upload
              class="signature-uploader"
              :action="uploadUrl"
              :headers="uploadHeaders"
              :before-upload="beforeSignatureUpload"
              :on-success="handleSignatureSuccess"
              :on-error="handleSignatureError"
              :show-file-list="false"
              accept="image/*"
              drag
          >
            <el-icon class="el-icon--upload">
              <Upload/>
            </el-icon>
            <div class="el-upload__text">将签名图片拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
          </el-upload>
        </div>

        <!-- 操作按钮 -->
        <div class="signature-actions">
          <el-button
              v-if="currentSignatureUrl"
              type="danger"
              @click="deleteSignature"
              :loading="deleting"
          >
            删除签名
          </el-button>
        </div>
      </div>
    </el-dialog>

  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue'
import axios from '@/utils/request'
import {ElMessage} from 'element-plus'
import {Download, Star, Document, OfficeBuilding, Edit, Upload} from '@element-plus/icons-vue'
import UserHeader from '@/components/common/UserHeader.vue'
import StudentDetailPanel from '@/components/teacher/StudentDetailPanel.vue'
import StudentAssessmentPanel from '@/components/teacher/StudentAssessmentPanel.vue'
import EnterpriseInfoPanel from '@/components/teacher/EnterpriseInfoPanel.vue'

const teacherInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

const studentList = ref([])
const selectedStudentView = ref(null)
const currentView = ref('assessment')
const downloading = ref(false)
const enterpriseInfo = ref({})
const documentStatus = ref('')
const errorMessage = ref('')

// 电子签名相关参数
const signatureDialogVisible = ref(false)
const currentSignatureUrl = ref('')
const deleting = ref(false)
const uploadUrl = `/api/teacher/signature/${teacherInfo.username}`
const uploadHeaders = {
  'Authorization': localStorage.getItem('token') || ''
}

const selectedStudentId = computed(() => {
  return selectedStudentView.value?.student?.s_id?.toString() || ''
})

const fetchStudents = async () => {
  const res = await axios.get(`/duser/selectbytnumber/${teacherInfo.username}`)
  studentList.value = res.data.data
}

const fetchEnterpriseInfo = async (studentId) => {
  try {
    const res = await axios.get(`/internship/${studentId}`)
    if (res.data?.data?.[0]) {
      enterpriseInfo.value = res.data.data[0]
    }
  } catch (error) {
    console.error('获取企业信息失败:', error)
  }
}

const handleViewChange = async (view) => {
  currentView.value = view
  // 如果有学生列表，选择第一个学生
  if (studentList.value.length > 0) {
    await handleStudentSelect(studentList.value[0])
  } else {
    selectedStudentView.value = null
  }
}

const handleStudentSelect = async (studentView) => {
  selectedStudentView.value = studentView
  await fetchEnterpriseInfo(studentView.student.s_id)
  // 如果当前是报告视图，检查文档状态
  if (currentView.value === 'report') {
    await checkDocument(studentView.student.student_number)
  }
}

const downloadAllReports = async () => {
  try {
    downloading.value = true

    const teacherResponse = await axios.get(`/teacher/${teacherInfo.username}`)
    const teacherId = teacherResponse.data.data.t_id

    if (!teacherId) {
      ElMessage.error('无法获取教师ID')
      return
    }

    const response = await axios.get(`/teacher/download-reports/${teacherId}`, {
      responseType: 'blob'
    })

    const blob = new Blob([response.data], {type: 'application/zip'})
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `学生报告_${new Date().toLocaleDateString()}.zip`
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)

    ElMessage.success('下载成功')
  } catch (error) {
    console.error('下载失败:', error)
    if (error.response?.status === 404) {
      ElMessage.error('未找到学生报告文件')
    } else {
      ElMessage.error('下载失败，请稍后重试')
    }
  } finally {
    downloading.value = false
  }
}

// 检查文档状态
const checkDocument = async (studentNumber) => {
  try {
    const response = await axios.get(`/student/checkdocument/${studentNumber}`)
    if (response.data.code === "200") {
      documentStatus.value = 'exists'
      ElMessage.success(response.data.message || '文档检查成功')
    } else {
      documentStatus.value = 'not-found'
      errorMessage.value = response.data.message || '未找到文档'
      ElMessage.warning(response.data.message || '未找到Word文档')
    }
  } catch (error) {
    documentStatus.value = 'error'
    errorMessage.value = error.response?.data?.message || error.message || '检查失败'
    ElMessage.error('检查文档状态失败：' + errorMessage.value)
  }
}

// 下载学生报告
const downloadStudentReport = async () => {
  if (documentStatus.value !== 'exists' || !selectedStudentView.value) {
    ElMessage.warning('请先检查文档状态')
    return
  }

  downloading.value = true
  try {
    const response = await axios.get(`/student/download/${selectedStudentView.value.student.student_number}`, {
      responseType: 'blob',
      timeout: 30000
    })

    const blob = new Blob([response.data])
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url

    const contentDisposition = response.headers['content-disposition']
    let filename = `${selectedStudentView.value.student.student_number}_实习报告.docx`
    if (contentDisposition) {
      const filenameMatch = contentDisposition.match(/filename="(.+)"/)
      if (filenameMatch) {
        filename = filenameMatch[1]
      }
    }

    link.download = filename
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)

    ElMessage.success('文档下载成功')
  } catch (error) {
    console.error('下载错误详情:', error)
    if (error.response?.status === 404) {
      ElMessage.error('文档不存在或已被删除')
      documentStatus.value = 'not-found'
    } else {
      ElMessage.error('下载失败：' + (error.response?.data?.message || error.message))
    }
  } finally {
    downloading.value = false
  }
}

// 电子签名相关方法
const openSignatureDialog = async () => {
  signatureDialogVisible.value = true
  await loadCurrentSignature()
}
// 加载当前签名
const loadCurrentSignature = async () => {
  try {
    const response = await axios.get(`/teacher/signature/${teacherInfo.username}`)
    if (response.data.code === '200' && response.data.data?.signatureUrl) {
      // 添加时间戳参数避免浏览器缓存
      const timestamp = new Date().getTime()
      currentSignatureUrl.value = `${response.data.data.signatureUrl}?t=${timestamp}`
    } else {
      currentSignatureUrl.value = ''
    }
  } catch (error) {
    console.error('获取签名失败:', error)
    currentSignatureUrl.value = ''
  }
}
// 签名上传前检查
const beforeSignatureUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB!')
    return false
  }
  return true
}
// 签名上传成功
const handleSignatureSuccess = async (response) => {
  if (response.code === '200') {
    ElMessage.success('签名上传成功')
    // 使用上传响应中的signatureUrl 更新currentSignatureUrl
    if (response.data?.signatureUrl) {
      const timestamp = new Date().getTime()
      currentSignatureUrl.value = `${response.data.signatureUrl}?t=${timestamp}`
    }
  } else {
    ElMessage.error(response.message || '上传失败')
  }
}
// 签名上传错误
const handleSignatureError = (error) => {
  console.error('上传错误:', error)
  ElMessage.error('上传失败，请重试')
}
// 删除签名
const deleteSignature = async () => {
  deleting.value = true
  try {
    const response = await axios.delete(`/teacher/signature/${teacherInfo.username}`)
    if (response.data.code === '200') {
      ElMessage.success('签名删除成功')
      currentSignatureUrl.value = ''
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    console.error('删除签名失败:', error)
    ElMessage.error('删除失败，请重试')
  } finally {
    deleting.value = false
  }
}
// 页面加载时获取学生列表 并选择默认显示学生
onMounted(async () => {
  await fetchStudents()
  // 如果有学生，选择第一个学生
  if (studentList.value.length > 0) {
    await handleStudentSelect(studentList.value[0])
  }
})
</script>

<style scoped>
.main-container {
  margin-top: 50px;
  height: calc(100vh - 50px);
  overflow: hidden;
}

.menu-container {
  height: 100%;
  border-right: 1px solid #e6e6e6;
}

.student-list-aside {
  border-right: 1px solid #e6e6e6;
  background-color: #fafafa;
}

.student-list-header {
  padding: 16px;
  border-bottom: 1px solid #e6e6e6;
}

.student-list-header h3 {
  margin: 0;
  font-size: 16px;
  color: #303133;
}

.student-menu {
  border-right: none;
}

.student-info-card {
  margin-bottom: 20px;
}

.panel-container {
  margin-top: 20px;
}

.download-button-container {
  padding: 20px 10px;
  border-top: 1px solid #e6e6e6;
  margin-top: auto;
}

.download-all-btn {
  width: 100%;
  font-size: 14px;
}

.no-selection {
  text-align: center;
  color: #909399;
  padding: 40px 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.report-panel {
  margin-top: 20px;
}

.card-header {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

.report-content {
  padding: 20px;
}

.status-section {
  margin-bottom: 20px;
}

.action-section {
  margin-top: 20px;
}

.signature-button-container {
  padding: 10px;
  margin-top: 10px;
  border-top: 1px solid #e6e6e6;
}

.signature-btn {
  width: 100%;
}

.signature-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  padding: 20px;
}

.current-signature {
  width: 100%;
  text-align: center;
}

.current-signature h4 {
  margin-bottom: 10px;
  color: #606266;
}

.signature-image {
  max-width: 100%;
  max-height: 200px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  background-color: #fff;
}

.upload-area {
  width: 100%;
  text-align: center;
}

.signature-uploader {
  width: 100%;
}

.el-upload__tip {
  margin-top: 10px;
  color: #909399;
  font-size: 12px;
}

.signature-actions {
  display: flex;
  gap: 10px;
  justify-content: center;
  width: 100%;
}
</style>
