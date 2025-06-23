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
          <el-menu-item index="plan">填写实习计划</el-menu-item>
          <el-menu-item index="diary">填写周记</el-menu-item>
          <el-menu-item index="status">周记状态查看</el-menu-item>
          <el-menu-item index="evaluation">实习单位鉴定</el-menu-item>
          <el-menu-item index="wordreport">Word报告管理</el-menu-item> <!-- 添加Word报告管理菜单项 -->
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <!-- 周记表单组件-->
          <DiaryForm
              v-if="currentTab === 'diary' && loaded"
              :userName="userName"
              :initialWeek="editingWeek"
          />
          <BasicInfoForm v-else-if="currentTab === 'info'" :s_id="s_id" />
          <InternshipInfoForm v-else-if="currentTab === 'internship'" :s_id="s_id" :username="userName" />
          <DPlanForm v-else-if="currentTab === 'plan'" :username="userName" />
          <!-- 评分评语组件 -->
          <ScoreCommentForm
              v-else-if="currentTab === 'evaluation'"
              :assessmentData="{ s_id: s_id }"
              :isReadOnly="false"

          />
          <!-- 周记状态查看组件 -->
          <WeekStatus
              v-else-if="currentTab === 'status'"
              :username="userName"
              @edit-diary="handleEditDiary"
          />
          <!-- Word报告管理组件 -->
          <WordReportManagement v-else-if="currentTab === 'wordreport'" :studentNumber="userName" :s_id="s_id" />
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
import DPlanForm from '../../components/student/DPlanForm.vue'
import ScoreCommentForm from '../../components/student/ScoreCommentForm.vue'
import UserHeader from '../../components/common/UserHeader.vue'
import WeekStatus from '../../components/student/WeekStatus.vue' // 导入周记状态查看组件
import WordReportManagement from '../../components/student/WordReportManagement.vue' // 导入Word报告管理组件
import axios from '@/utils/request'
import { ElMessage } from 'element-plus'

// 用户信息
const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = userInfo.username
const currentTab = ref('diary') // 当前标签页，默认为填写周记
const editingWeek = ref(null) // 要编辑的周数
const s_id = ref()
const loaded = ref(false) // 数据加载状态

// 加载学生ID
const loadS_id = async () => {
  try {
    const res = await axios.get(`/student/getbyusername/${userName}`)
    s_id.value = res.data.data.s_id
    loaded.value = true
  } catch (error) {
    console.error('加载学生信息失败:', error)
    ElMessage.error('加载学生信息失败，请稍后重试')
  }
}


// 处理编辑周记事件
const handleEditDiary = (week) => {
  // 设置要编辑的周数，总结周记统一设为17
  if (week === 'achievement' || week === 'practice') {
    editingWeek.value = 17
  } else {
    editingWeek.value = parseInt(week)
  }
  // 切换到周记编辑标签页
  currentTab.value = 'diary'
  console.log('编辑周记:', week, '-> 设置为:', editingWeek.value)
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