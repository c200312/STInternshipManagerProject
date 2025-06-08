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
          <el-menu-item index="import">导入学生及教师信息</el-menu-item>
          <el-menu-item index="student">学生实习信息</el-menu-item>
          <el-menu-item index="assessment">学生成绩信息</el-menu-item>
          <el-menu-item index="diary">学生周记</el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
        <ViewStudentInfoBase v-if="currentTab === 'student'"/>
        <SelectRelationshipForm v-if="currentTab === 'relation'"/>
        <ImportExcelForm v-if="currentTab === 'import'"/>
        <ViewStudentAssessment v-if="currentTab === 'assessment'"/>
        <ViewStudentInfoDiary v-if="currentTab === 'diary'"/>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { ref } from 'vue'
import UserHeader from '@/components/common/UserHeader.vue'
import SelectRelationshipForm from "@/components/admain/SelectRelationshipForm.vue";
import ImportExcelForm from "@/components/admain/ImportExcelForm.vue";
import ViewStudentInfoDiary from "@/components/admain/ViewStudentInfoDiary.vue";
import ViewStudentInfoBase from "@/components/admain/ViewStudentInfoBase.vue";
import ViewStudentAssessment from "@/components/admain/ViewStudentAssessment.vue";
const currentTab = ref('student' +
    '') // 当前标签页


// 用户信息
const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')


</script>
<style scoped>
/* 主体容器整体布局 */
.el-container {
  margin-top: 50px; /* 留出顶部用户头像栏的空间 */
}
</style>

