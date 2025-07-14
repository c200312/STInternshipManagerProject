<template>
  <div class="week-status-container">
    <h3>周记状态查看</h3>
    
    <!-- 筛选条件 -->
    <div class="filter-container">
      <el-select v-model="selectedWeek" placeholder="请选择周" class="week-select" @change="loadDiaryStatus">
        <el-option label="全部" value="all"></el-option>
        <el-option
            v-for="week in 17"
            :key="week"
            :label="week === 17 ? '总结' : `第 ${week} 周`"
            :value="week">
        </el-option>
      </el-select>
    </div>

    <!-- 周记状态列表 -->
    <el-table :data="diaryStatusList" style="width: 100%" v-loading="loading">
      <el-table-column prop="week" label="周数" width="120">
        <template #default="{ row }">
          {{ getWeekLabel(row.week) }}
        </template>
      </el-table-column>
      
      <el-table-column prop="status" label="状态" width="120">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      
      <el-table-column prop="content" label="内容" show-overflow-tooltip>
        <template #default="{ row }">
          <span v-if="row.content">{{ row.content.substring(0, 100) }}{{ row.content.length > 100 ? '...' : '' }}</span>
          <span v-else class="no-content">暂无内容</span>
        </template>
      </el-table-column>
      
      <el-table-column prop="reviewComment" label="审核意见" show-overflow-tooltip>
        <template #default="{ row }">
          <span v-if="row.reviewComment">{{ row.reviewComment }}</span>
          <span v-else class="no-comment">-</span>
        </template>
      </el-table-column>
      
      <el-table-column prop="reviewTime" label="审核时间" width="180">
        <template #default="{ row }">
          <span v-if="row.reviewTime">{{ formatDate(row.reviewTime) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      
      <el-table-column prop="submitTime" label="提交日期" width="180">
        <template #default="{ row }">
          <span v-if="row.submitTime">{{ formatSubmitDate(row.submitTime) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      
      <el-table-column prop="reviewer" label="审核人" width="120">
        <template #default="{ row }">
          <span v-if="row.reviewer">{{ row.reviewer }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button 
            v-if="row.status === 'REJECTED'"
            type="primary" 
            size="small" 
            @click="editDiary(row.week)"
          >
            重新编辑
          </el-button>
          <el-button 
            v-if="row.status === 'DRAFT' || row.status === 'REJECTED'"
            type="success" 
            size="small" 
            @click="submitForReview(row.week)"
          >
            提交审核
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 空状态 -->
    <el-empty v-if="!loading && diaryStatusList.length === 0" description="暂无周记数据" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '../../utils/request'

const props = defineProps(['username'])
const emit = defineEmits(['edit-diary'])

// 响应式数据
const selectedWeek = ref('all')
const diaryList = ref([])
const loading = ref(false)

// 计算属性：过滤后的周记状态列表
const diaryStatusList = computed(() => {
  if (selectedWeek.value === 'all') {
    return diaryList.value
  }
  return diaryList.value.filter(diary => diary.week === selectedWeek.value || 
    (selectedWeek.value === 17 && (diary.week === 'achievement' || diary.week === 'practice')))
})

// 加载周记状态数据
const loadDiaryStatus = async () => {
  loading.value = true
  try {
    const res = await axios.get(`/duser/${props.username}`)
    const diaries = res.data?.data?.diary || []
    
    // 创建完整的周记状态列表（1-16周 + 总结）
    const statusList = []
    
    // 添加1-16周
    for (let week = 1; week <= 16; week++) {
      const found = diaries.find(d => d.week === `${week}`)
      statusList.push({
        week: week,
        content: found?.content || '',
        status: found?.status || 'DRAFT',
        reviewComment: found?.reviewComment || '',
        reviewTime: found?.reviewTime || '',
        reviewer: found?.reviewer || '',
        submitTime: found?.submitTime || '',
        diaryDate: found?.diaryDate || ''
      })
    }
    
    // 添加总结部分
    const achievementFound = diaries.find(d => d.week === 'achievement')
    const practiceFound = diaries.find(d => d.week === 'practice')
    
    if (achievementFound) {
      statusList.push({
        week: 'achievement',
        content: achievementFound.content || '',
        status: achievementFound.status || 'DRAFT',
        reviewComment: achievementFound.reviewComment || '',
        reviewTime: achievementFound.reviewTime || '',
        reviewer: achievementFound.reviewer || '',
        submitTime: achievementFound.submitTime || '',
        diaryDate: achievementFound.diaryDate || ''
      })
    }
    
    if (practiceFound) {
      statusList.push({
        week: 'practice',
        content: practiceFound.content || '',
        status: practiceFound.status || 'DRAFT',
        reviewComment: practiceFound.reviewComment || '',
        reviewTime: practiceFound.reviewTime || '',
        reviewer: practiceFound.reviewer || '',
        submitTime: practiceFound.submitTime || '',
        diaryDate: practiceFound.diaryDate || ''
      })
    }
    
    diaryList.value = statusList
  } catch (error) {
    console.error('加载周记状态失败:', error)
    ElMessage.error('加载周记状态失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 提交审核
const submitForReview = async (week) => {
  try {
    await axios.post(`/duser/${props.username}/diary/${week}/submit`)
    ElMessage.success('提交审核成功')
    loadDiaryStatus() // 重新加载数据
  } catch (error) {
    console.error('提交审核失败:', error)
    ElMessage.error('提交审核失败，请稍后重试')
  }
}

// 编辑周记
const editDiary = (week) => {
  emit('edit-diary', week)
}

// 获取周数标签
const getWeekLabel = (week) => {
  if (typeof week === 'number') {
    return `第 ${week} 周`
  } else if (week === 'achievement') {
    return '成果总结'
  } else if (week === 'practice') {
    return '实践总结'
  }
  return week
}

// 获取状态显示文本
const getStatusText = (status) => {
  const statusMap = {
    'DRAFT': '草稿',
    'SUBMITTED': '已提交',
    'APPROVED': '已通过',
    'REJECTED': '已拒绝'
  }
  return statusMap[status] || '草稿'
}

// 获取状态标签类型
const getStatusType = (status) => {
  const typeMap = {
    'DRAFT': 'info',
    'SUBMITTED': 'warning',
    'APPROVED': 'success',
    'REJECTED': 'danger'
  }
  return typeMap[status] || 'info'
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN')
}

// 格式化提交日期（只显示年月日）
const formatSubmitDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN')
}

// 页面加载时获取数据
onMounted(() => {
  loadDiaryStatus()
})
</script>

<style scoped>
.week-status-container {
  padding: 20px;
}

.filter-container {
  margin-bottom: 20px;
}

.week-select {
  width: 200px;
}

.no-content {
  color: #999;
  font-style: italic;
}

.no-comment {
  color: #999;
}

.el-table {
  margin-top: 20px;
}
</style>