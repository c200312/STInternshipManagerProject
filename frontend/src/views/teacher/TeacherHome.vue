<template>
  <div>
    <UserHeader :username="teacherInfo.username" />

    <el-container class="main-container">
      <StudentAsideMenu :students="studentList" @select="selectStudentView" />
      <StudentDetailPanel :studentView="selectedStudentView" />
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '@/utils/request'
import UserHeader from '@/components/common/UserHeader.vue'
import StudentAsideMenu from '@/components/teacher/StudentAsideMenu.vue'
import StudentDetailPanel from '@/components/teacher/StudentDetailPanel.vue'

const teacherInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

const studentList = ref([])
const selectedStudentView = ref(null)

const fetchStudents = async () => {
  const res = await axios.get(`/duser/selectbytnumber/${teacherInfo.username}`)
  studentList.value = res.data.data
}

const selectStudentView = (studentView) => {
  selectedStudentView.value = studentView
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
