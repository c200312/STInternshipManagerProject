<template>
  <div>
    <!-- 顶部头像 + 姓名 -->
    <UserHeader
        :username="userInfo.username"
    />
    <!-- 页面主体部分 -->
    <el-container>
      <el-aside width="200px">
        <el-menu @select="currentTab = $event" :default-active="currentTab">
          <el-menu-item index="relation">选择教师所管理的学生</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <SelectRelationshipForm
              v-if="currentTab === 'relation'"
              :studentData="studentData"
              :teacherData="teacherData"
          />


        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import UserHeader from '@/components/common/UserHeader.vue'
import SelectRelationshipForm from "@/components/admain/SelectRelationshipForm.vue";
import axios from "@/utils/request";
const currentTab = ref('relation') // 当前标签页
//  数据
const studentData = ref([]);
const teacherData = ref([]);

// 用户信息
const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

async function loadStudentData() {
  const res=await axios.get(`/student`);
  studentData.value = res.data.data
  console.log(studentData.value)
}
async function loadTeacherData () {
  const res=await axios.get(`/teacher`);
  teacherData.value = res.data.data
  console.log(teacherData.value)
}
onMounted(() => {
  loadStudentData()
  loadTeacherData()

})
</script>
<style scoped>
/* 主体容器整体布局 */
.el-container {
  margin-top: 50px; /* 留出顶部用户头像栏的空间 */
}
</style>

