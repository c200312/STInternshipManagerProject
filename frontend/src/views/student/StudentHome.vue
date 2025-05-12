<template>
  <div>
    <!-- 顶部头像 + 姓名 -->
    <UserHeader
        :username="userName"
    />

    <!-- 页面主体部分 -->
    <el-container>
      <el-aside width="200px">
        <el-menu @select="currentTab = $event" :default-active="currentTab">
          <el-menu-item index="diary">填写周记</el-menu-item>
          <el-menu-item index="info">填写基本信息</el-menu-item>
          <el-menu-item index="company">填写企业信息</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <DiaryForm
              v-if="currentTab === 'diary'"
              :userName="userName"
          />
          <BasicInfoForm
              v-else-if="currentTab === 'info'"
              :userName="userName"

          />

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../../utils/request'
import DiaryForm from '../../components/DiaryForm.vue'
import BasicInfoForm from '../../components/BasicInfoForm.vue'
import CompanyInfoForm from '../../components/CompanyInfoForm.vue'
import UserHeader from '../../components/UserHeader.vue'
import { ElMessage } from 'element-plus'

// 用户信息
const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = userInfo.username
const userId = userInfo.user_id

// Vue实例相关
const router = useRouter()

// 数据定义
const currentTab = ref('diary') // 当前标签页

onMounted(() => {
})


</script>

<style scoped>
/* 主体容器整体布局 */
.el-container {
  margin-top: 50px; /* 留出顶部用户头像栏的空间 */
}
</style>
