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
          <el-menu-item index="student">学生信息</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <ViewStudentInfo
            v-if="currentTab === 'student'"
            />
        </el-main>
        <el-main>
          <SelectRelationshipForm
              v-if="currentTab === 'relation'"
          />
        </el-main>
        <el-main>
          <ImportExcelForm
              v-if="currentTab === 'import'"
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
import ImportExcelForm from "@/components/admain/ImportExcelForm.vue";
import ViewStudentInfo from "@/components/admain/ViewStudentInfo.vue";
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

