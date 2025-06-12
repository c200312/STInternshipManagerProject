<template>
  <div>
    <UserHeader :username="teacherInfo.username" />

    <el-container class="main-container">
      <el-aside width="200px">
        <el-menu :default-active="selectedStudentId" class="menu-container">
          <el-sub-menu v-for="studentView in studentList" :key="studentView.student.s_id" :index="studentView.student.s_id.toString()">
            <template #title>{{ studentView.student.student_name }}</template>
            <el-menu-item index="assessment" @click="handleMenuClick(studentView, 'assessment')">评分管理</el-menu-item>
            <el-menu-item index="diary" @click="handleMenuClick(studentView, 'diary')">周记管理</el-menu-item>
            <el-menu-item index="enterprise" @click="handleMenuClick(studentView, 'enterprise')">企业信息管理</el-menu-item>
          </el-sub-menu>
          
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
        </el-menu>
      </el-aside>

      <el-main>
        <!-- 学生基本信息展示区域 -->
        <el-card v-if="selectedStudentView" class="student-info-card">
          <h3>学生基本信息</h3>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="姓名">{{ selectedStudentView.student.student_name }}</el-descriptions-item>
            <el-descriptions-item label="学号">{{ selectedStudentView.student.student_number }}</el-descriptions-item>
            <el-descriptions-item label="班级">{{ selectedStudentView.student.stu_class }}</el-descriptions-item>
            <el-descriptions-item label="实习单位">{{ selectedStudentView.duser?.company?.[0]?.name || '无' }}</el-descriptions-item>
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
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from '@/utils/request'
import { ElMessage } from 'element-plus'
import { Download } from '@element-plus/icons-vue'
import UserHeader from '@/components/common/UserHeader.vue'
import StudentDetailPanel from '@/components/teacher/StudentDetailPanel.vue'
import StudentAssessmentPanel from '@/components/teacher/StudentAssessmentPanel.vue'
import EnterpriseInfoPanel from '@/components/teacher/EnterpriseInfoPanel.vue'

const teacherInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

const studentList = ref([])
const selectedStudentView = ref(null)
const currentView = ref('diary')
const downloading = ref(false)

const selectedStudentId = computed(() => {
  return selectedStudentView.value?.student.s_id.toString() || ''
})

const fetchStudents = async () => {
  const res = await axios.get(`/duser/selectbytnumber/${teacherInfo.username}`)
  studentList.value = res.data.data
  
  // 默认选中第一个学生
  if (studentList.value.length > 0) {
    selectedStudentView.value = studentList.value[0]
  }
}

const handleMenuClick = (studentView, view) => {
  selectedStudentView.value = studentView
  currentView.value = view
}

const downloadAllReports = async () => {
  try {
    downloading.value = true
    
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
    
    // 创建下载链接
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
    downloading.value = false
  }
}

onMounted(fetchStudents)
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

.student-info-card {
  margin-bottom: 20px;
}

.panel-container {
  margin-top: 20px;
}

:deep(.el-menu) {
  border-right: none;
}

:deep(.el-sub-menu .el-menu-item) {
  min-width: 0;
  padding-left: 40px !important;
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
</style>
