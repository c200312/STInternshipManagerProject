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
      <!-- 其他列保持不变 -->
      <el-table-column prop="week" label="周数" width="120">
        <template #default="{ row }">
          {{ getWeekLabel(row.week) }}
        </template>
      </el-table-column>

      <!-- 状态列保持不变 -->
      <el-table-column prop="status" label="状态" width="120">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>

      <!-- 内容列保持不变 -->
      <el-table-column prop="content" label="内容" show-overflow-tooltip>
        <template #default="{ row }">
          <span v-if="row.content">{{ row.content.substring(0, 100) }}{{ row.content.length > 100 ? '...' : '' }}</span>
          <span v-else class="no-content">暂无内容</span>
        </template>
      </el-table-column>

      <!-- 审核意见列保持不变 -->
      <el-table-column prop="reviewComment" label="审核意见" show-overflow-tooltip>
        <template #default="{ row }">
          <span v-if="row.reviewComment">{{ row.reviewComment }}</span>
          <span v-else class="no-comment">-</span>
        </template>
      </el-table-column>

      <!-- 审核时间列保持不变 -->
      <el-table-column prop="reviewTime" label="审核时间" width="180">
        <template #default="{ row }">
          <span v-if="row.reviewTime">{{ formatDate(row.reviewTime) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>

      <!-- 审核人列保持不变 -->
      <el-table-column prop="reviewer" label="审核人" width="120">
        <template #default="{ row }">
          <span v-if="row.reviewer">{{ row.reviewer }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>

      <!-- 修改后的操作列 -->
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <div class="operation-buttons">
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
          </div>
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

// 响应式数据保持不变
const selectedWeek = ref('all')
const diaryList = ref([])
const loading = ref(false)

// 计算属性保持不变
const diaryStatusList = computed(() => {
  if (selectedWeek.value === 'all') {
    return diaryList.value
  }
  return diaryList.value.filter(diary => diary.week === selectedWeek.value ||
      (selectedWeek.value === 17 && (diary.week === 'achievement' || diary.week === 'practice')))
})

// 方法保持不变
const loadDiaryStatus = async () => {
  loading.value = true
  try {
    const res = await axios.get(`/duser/${props.username}`)
    const diaries = res.data?.data?.diary || []

    const statusList = []

    for (let week = 1; week <= 16; week++) {
      const found = diaries.find(d => d.week === `${week}`)
      statusList.push({
        week: week,
        content: found?.content || '',
        status: found?.status || 'DRAFT',
        reviewComment: found?.reviewComment || '',
        reviewTime: found?.reviewTime || '',
        reviewer: found?.reviewer || ''
      })
    }

    const achievementFound = diaries.find(d => d.week === 'achievement')
    const practiceFound = diaries.find(d => d.week === 'practice')

    if (achievementFound) {
      statusList.push({
        week: 'achievement',
        content: achievementFound.content || '',
        status: achievementFound.status || 'DRAFT',
        reviewComment: achievementFound.reviewComment || '',
        reviewTime: achievementFound.reviewTime || '',
        reviewer: achievementFound.reviewer || ''
      })
    }

    if (practiceFound) {
      statusList.push({
        week: 'practice',
        content: practiceFound.content || '',
        status: practiceFound.status || 'DRAFT',
        reviewComment: practiceFound.reviewComment || '',
        reviewTime: practiceFound.reviewTime || '',
        reviewer: practiceFound.reviewer || ''
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

const submitForReview = async (week) => {
  try {
    await axios.post(`/duser/${props.username}/diary/${week}/submit`)
    ElMessage.success('提交审核成功')
    loadDiaryStatus()
  } catch (error) {
    console.error('提交审核失败:', error)
    ElMessage.error('提交审核失败，请稍后重试')
  }
}

const editDiary = (week) => {
  emit('edit-diary', week)
}

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

const getStatusText = (status) => {
  const statusMap = {
    'DRAFT': '草稿',
    'SUBMITTED': '已提交',
    'APPROVED': '已通过',
    'REJECTED': '已拒绝'
  }
  return statusMap[status] || '草稿'
}

const getStatusType = (status) => {
  const typeMap = {
    'DRAFT': 'info',
    'SUBMITTED': 'warning',
    'APPROVED': 'success',
    'REJECTED': 'danger'
  }
  return typeMap[status] || 'info'
}

const formatDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN')
}

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

/* 新增按钮样式 */
.operation-buttons {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 8px;
}

/* 确保表格单元格内容不被截断 */
.el-table__cell {
  padding-right: 0 !important;
}
</style>