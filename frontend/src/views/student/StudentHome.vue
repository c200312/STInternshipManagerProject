<template>
  <div>
    <!-- 顶部头像 + 姓名 -->
    <UserHeader :username="userName" />

    <!-- 页面主体部分 -->
    <el-container>
      <el-aside width="200px">
        <el-menu @select="currentTab = $event" :default-active="currentTab">
          <el-menu-item index="diary">填写周记</el-menu-item>
          <el-menu-item index="info">填写基本信息</el-menu-item>
          <el-menu-item index="internship">填写实习信息</el-menu-item>
          <!-- 新增评分与评语标签 -->
          <el-menu-item index="evaluation">填写评分与评语</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <DiaryForm v-if="currentTab === 'diary'" :userName="userName" />
          <BasicInfoForm v-else-if="currentTab === 'info'" :s_id="s_id" />
          <InternshipInfoForm v-else-if="currentTab === 'internship'" :s_id="s_id" />
          <!-- 新增评分与评语组件 -->
          <ScoreCommentForm
              v-else-if="currentTab === 'evaluation'"
              :s_id="s_id"
              :isReadOnly="false"
              @save-success="handleEvaluationSave"
          />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import DiaryForm from '../../components/student/DiaryForm.vue'
import BasicInfoForm from '../../components/student/BasicInfoForm.vue'
import InternshipInfoForm from '../../components/student/InternshipInfoForm.vue'
import ScoreCommentForm from '../../components/student/ScoreCommentForm.vue'  // 新增组件导入
import UserHeader from '../../components/common/UserHeader.vue'
import axios from '@/utils/request';

// 用户信息
const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = userInfo.username
const userId = userInfo.user_id
const currentTab = ref('diary')  // 当前标签页，可根据需求初始化为其他值
const s_id = ref()
const loadS_id =async () => {
  const res=await axios.get(`/student/getbyusername/${userName}`);

  s_id.value = res.data.data.s_id

}
// 处理评分保存事件
const handleEvaluationSave = (data) => {
  console.log('评分与评语保存成功，数据：', data)
  // 可添加后续逻辑（如同步到父组件/刷新其他模块）
}
onMounted(() => {
  loadS_id()
})
</script>

<style scoped>
/* 主体容器整体布局 */
.el-container {
  margin-top: 50px; /* 留出顶部用户头像栏的空间 */
}

/* 侧边栏菜单样式优化 */
.el-menu {
  border-right: none;
  background-color: #f8f9fa;
}

.el-menu-item.is-active {
  color: #409eff;
  background-color: #e7f3ff;
}
</style>
