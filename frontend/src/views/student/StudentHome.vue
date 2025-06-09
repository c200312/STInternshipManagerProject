<template>
  <div>
    <!-- 顶部头像 + 姓名 -->
    <UserHeader v-if="loaded" :username="userName" />

    <!-- 页面主体部分 -->
    <el-container>
      <el-aside width="200px">
        <el-menu @select="currentTab = $event" :default-active="currentTab">
          <el-menu-item index="info">填写基本信息</el-menu-item>
          <el-menu-item index="internship">填写实习信息</el-menu-item>
          <el-menu-item index="diary">填写周记</el-menu-item>
          <el-menu-item index="status">周记状态查看</el-menu-item> <!-- 添加周记状态查看菜单项 -->
          <el-menu-item index="evaluation">实习单位鉴定</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <DiaryForm v-if="currentTab === 'diary'" :userName="userName" />
          <BasicInfoForm v-else-if="currentTab === 'info'" :s_id="s_id" />
          <InternshipInfoForm v-else-if="currentTab === 'internship'" :s_id="s_id" />
          <!-- 评分评语组件 -->
          <ScoreCommentForm
              v-else-if="currentTab === 'evaluation'"
              :s_id="s_id"
              :isReadOnly="false"
              @save-success="handleEvaluationSave"
          />
          <!-- 周记状态查看组件 -->
          <WeekStatus v-else-if="currentTab === 'status'" :username="userName" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import DiaryForm from '../../components/student/DiaryForm.vue'
import BasicInfoForm from '../../components/student/BasicInfoForm.vue'
import InternshipInfoForm from '../../components/student/InternshipInfoForm.vue'
import ScoreCommentForm from '../../components/student/ScoreCommentForm.vue' // 导入评分评语组件
import UserHeader from '../../components/common/UserHeader.vue'
import WeekStatus from '../../components/student/DiaryStutas.vue' // 导入周记状态查看组件
import axios from '@/utils/request'

// 用户信息
const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = userInfo.username
const userId = userInfo.user_id
const currentTab = ref('diary') // 当前标签页，默认为基本信息
const s_id = ref()
const loaded = ref(false) // 添加加载状态

// 加载学生 ID
const loadS_id = async () => {
  try {
    const res = await axios.get(`/student/getbyusername/${userName}`)
    s_id.value = res.data.data.s_id
    loaded.value = true // 数据加载完成后设置加载状态为 true
  } catch (error) {
    console.error('加载学生信息失败:', error)
    ElMessage.error('加载学生信息失败，请稍后重试')
  }
}

// 处理评分保存事件
const handleEvaluationSave = (data) => {
  console.log('评分与评语保存成功，数据：', data)
  // 可添加后续逻辑
}

onMounted(() => {
  loadS_id()
})
</script>

<style scoped>
.el-container {
  margin-top: 50px;
}

.el-menu {
  border-right: none;
  background-color: #f8f9fa;
}

.el-menu-item.is-active {
  color: #409eff;
  background-color: #e7f3ff;
}
</style>