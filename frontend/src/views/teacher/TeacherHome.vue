<template>
  <div>
    <UserHeader :username="teacherInfo.username" />

    <el-container class="main-container">
      <!-- 左侧菜单 -->
      <el-aside width="200px" class="left-aside">
        <el-menu :default-active="currentView" class="menu-container">
          <el-menu-item index="assessment" @click="handleViewChange('assessment')">
            <el-icon><Document /></el-icon>
            <span>评分管理</span>
          </el-menu-item>
          <el-menu-item index="diary" @click="handleViewChange('diary')">
            <el-icon><Notebook /></el-icon>
            <span>周记管理</span>
          </el-menu-item>
          <el-menu-item index="enterprise" @click="handleViewChange('enterprise')">
            <el-icon><Office /></el-icon>
            <span>企业信息管理</span>
          </el-menu-item>
          <el-menu-item index="reports" @click="handleViewChange('reports')">
            <el-icon><Download /></el-icon>
            <span>下载报告</span>
          </el-menu-item>
        </el-menu>
        
        <!-- 下载所有学生报告按钮 -->
        <div class="download-button-container">
          <el-button 
            type="primary" 
            :loading="downloadingAll" 
            @click="downloadAllReports"
            class="download-all-btn"
            :icon="Download"
          >
            下载所有学生报告
          </el-button>
        </div>
      </el-aside>

      <!-- 中间和右侧内容区域 -->
      <el-container class="content-container">
        <!-- 学生列表 -->
        <div class="student-list-container" :class="{ 'student-list-hidden': isStudentListHidden }">
          <h2>{{ getViewTitle() }}</h2>
          <el-table 
            :data="studentList" 
            style="width: 100%"
            @row-click="handleStudentSelect"
            highlight-current-row
            :max-height="600"
          >
            <el-table-column prop="student.student_name" label="姓名" width="100" />
            <el-table-column prop="student.student_number" label="学号" width="120" />
            <el-table-column prop="student.stu_class" label="班级" width="120" />
          </el-table>
        </div>

        <!-- 右侧功能面板 -->
        <el-main class="function-panel" :class="{ 'function-panel-expanded': isStudentListHidden }">
          <template v-if="selectedStudentView">
            <div class="detail-header">
              <h2>{{ selectedStudentView.student.student_name }} - {{ getViewTitle() }}</h2>
            </div>

            <!-- 学生基本信息展示区域 -->
            <el-card class="student-info-card">
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
                v-if="currentView === 'diary'"
                :studentView="selectedStudentView" 
              />
              <StudentAssessmentPanel 
                v-else-if="currentView === 'assessment'"
                :students="studentList"
                :selectedStudent="selectedStudentView"
              />
              <EnterpriseInfoPanel
                v-else-if="currentView === 'enterprise'"
                :student="selectedStudentView?.student"
              />
              <div v-else-if="currentView === 'reports'" class="reports-container">
                <el-button 
                  type="primary" 
                  :loading="downloading" 
                  @click="downloadStudentReport"
                  :icon="Download"
                >
                  下载学生报告
                </el-button>
              </div>
            </div>
          </template>
          <div v-else class="no-selection">
            <el-empty description="请从左侧列表选择学生" />
          </div>

          <!-- 展开学生列表按钮 -->
          <el-button
            v-if="isStudentListHidden"
            class="toggle-student-list-btn"
            type="primary"
            :icon="ArrowRight"
            @click="toggleStudentList"
          >
            展开学生列表
          </el-button>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '@/utils/request'
import { ElMessage } from 'element-plus'
import { Download, Document, Notebook, Office, ArrowRight } from '@element-plus/icons-vue'
import UserHeader from '@/components/common/UserHeader.vue'
import StudentDetailPanel from '@/components/teacher/StudentDetailPanel.vue'
import StudentAssessmentPanel from '@/components/teacher/StudentAssessmentPanel.vue'
import EnterpriseInfoPanel from '@/components/teacher/EnterpriseInfoPanel.vue'

const teacherInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

const studentList = ref([])
const selectedStudentView = ref(null)
const currentView = ref('diary')
const downloading = ref(false)
const downloadingAll = ref(false)
const enterpriseInfo = ref({})
const isStudentListHidden = ref(false)

const getViewTitle = () => {
  const titles = {
    assessment: '评分管理',
    diary: '周记管理',
    enterprise: '企业信息管理',
    reports: '下载报告'
  }
  return titles[currentView.value] || ''
}

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

const handleViewChange = (view) => {
  currentView.value = view
  selectedStudentView.value = null
}

const handleStudentSelect = async (studentView) => {
  selectedStudentView.value = studentView
  await fetchEnterpriseInfo(studentView.student.s_id)
  isStudentListHidden.value = true
}

const downloadStudentReport = async () => {
  if (!selectedStudentView.value) return
  
  try {
    downloading.value = true
    const response = await axios.get(`/teacher/download-student-report/${selectedStudentView.value.student.s_id}`, {
      responseType: 'blob'
    })
    
    const blob = new Blob([response.data], { type: 'application/zip' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `${selectedStudentView.value.student.student_name}_报告.zip`
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

const downloadAllReports = async () => {
  try {
    downloadingAll.value = true
    
    // 先通过教师用户名获取教师信息，获取t_id
    const teacherResponse = await axios.get(`/teacher/${teacherInfo.username}`)
    const teacherId = teacherResponse.data.data.t_id
    
    if (!teacherId) {
      ElMessage.error('无法获取教师ID')
      return
    }
    
    const response = await axios.get(`/teacher/download-reports/${teacherId}`, {
      responseType: 'blob'
    })
    
    const blob = new Blob([response.data], { type: 'application/zip' })
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
    downloadingAll.value = false
  }
}

const toggleStudentList = () => {
  isStudentListHidden.value = !isStudentListHidden.value
}

onMounted(fetchStudents)
</script>

<style scoped>
.main-container {
  margin-top: 50px;
  height: calc(100vh - 50px);
  overflow: hidden;
}

.left-aside {
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
  z-index: 2;
}

.content-container {
  display: flex;
  height: 100%;
  position: relative;
  flex: 1;
  overflow: hidden;
}

.menu-container {
  border-right: 1px solid #e6e6e6;
}

.download-button-container {
  padding: 10px;
  border-top: 1px solid #e6e6e6;
  border-bottom: 1px solid #e6e6e6;
}

.download-all-btn {
  width: 100%;
  font-size: 14px;
}

.student-list-container {
  width: 400px;
  min-width: 400px;
  max-width: 400px;
  background-color: white;
  border-right: 1px solid #e6e6e6;
  padding: 20px;
  transition: margin-left 0.3s, width 0.3s;
  height: 100%;
  overflow-y: auto;
  position: relative;
  z-index: 1;
  margin-left: 0;
  flex-shrink: 0;
}

.student-list-hidden {
  margin-left: -400px;
}

.function-panel {
  flex: 1;
  padding: 20px;
  background-color: #f5f7fa;
  transition: all 0.3s;
  position: relative;
  height: 100%;
  overflow-y: auto;
  min-width: 0;
}

.function-panel-expanded {
  margin-left: 0;
}

.toggle-student-list-btn {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  border-radius: 0 4px 4px 0;
  padding: 20px 10px;
  height: auto;
  writing-mode: vertical-lr;
  letter-spacing: 2px;
  z-index: 3;
}

.student-info-card {
  margin-bottom: 20px;
}

.panel-container {
  margin-top: 20px;
}

.detail-header {
  margin-bottom: 20px;
}

.reports-container {
  display: flex;
  justify-content: center;
  padding: 40px;
}

.no-selection {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

:deep(.el-menu) {
  border-right: none;
}

:deep(.el-menu-item) {
  display: flex;
  align-items: center;
  gap: 8px;
}

:deep(.el-table__row) {
  cursor: pointer;
}
</style>
