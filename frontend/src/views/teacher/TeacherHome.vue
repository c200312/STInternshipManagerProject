<template>
  <div>
    <UserHeader :username="teacherInfo.username" />

    <el-container class="main-container">
      <StudentAsideMenu 
        :students="studentList" 
        :selectedStudent="selectedStudentView"
        @select="selectStudentView" 
        @view-change="handleViewChange"
      />
      <StudentDetailPanel 
        v-if="currentView === 'diary'"
        :studentView="selectedStudentView" 
      />
      <StudentAssessmentPanel 
        v-else-if="currentView === 'assessment'"
        :students="studentList"
        :selectedStudent="selectedStudentView"
      />
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '@/utils/request'
import UserHeader from '@/components/common/UserHeader.vue'
import StudentAsideMenu from '@/components/teacher/StudentAsideMenu.vue'
import StudentDetailPanel from '@/components/teacher/StudentDetailPanel.vue'
import StudentAssessmentPanel from '@/components/teacher/StudentAssessmentPanel.vue'

const teacherInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

const studentList = ref([])
const selectedStudentView = ref(null)
const currentView = ref('diary') // 'diary' 或 'assessment'

const fetchStudents = async () => {
  const res = await axios.get(`/duser/selectbytnumber/${teacherInfo.username}`)
  studentList.value = res.data.data
  
  // 默认选中第一个学生
  if (studentList.value.length > 0) {
    selectedStudentView.value = studentList.value[0]
  }
}

const selectStudentView = (studentView) => {
  selectedStudentView.value = studentView
}

const handleViewChange = (view) => {
  currentView.value = view
  // 评分模式下保持学生选择状态
}

onMounted(fetchStudents)
</script>

<style scoped>
.main-container {
  margin-top: 50px;
  height: calc(100vh - 50px);
  overflow: hidden;
}
</style>
