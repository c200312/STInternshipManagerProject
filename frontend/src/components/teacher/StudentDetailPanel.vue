<template>
  <el-main class="right-main">
    <el-card v-if="studentView">
      <!-- 周记状态总览列表 -->
      <el-card style="margin: 20px 0;">
        <template #header>
          <h3>周记状态总览</h3>
        </template>
        
        <!-- 周记状态列表 -->
        <div class="diary-list">
          <!-- 表头 -->
          <div class="diary-item header">
            <div class="diary-item-content">
              <div class="header-cell week">周次</div>
              <div class="header-cell time">审核时间</div>
              <div class="header-cell reviewer">审核人</div>
              <div class="header-cell comment">审核原因</div>
              <div class="header-cell action">操作</div>
            </div>
          </div>

          <!-- 周记项目 -->
          <div v-for="item in allDiaryItems" :key="item.key" class="diary-item">
            <div class="diary-item-content">
              <div class="content-cell week">
                <span class="week-label">{{ item.label }}</span>
                <el-tag :type="getStatusType(getDiaryStatus(item.week))" size="small">
                  {{ getStatusText(getDiaryStatus(item.week)) }}
                </el-tag>
              </div>
              <div class="content-cell time">
                <span class="info-text">
                  {{ getDiaryInfo(item.week, 'reviewTime') || '-' }}
                </span>
              </div>
              <div class="content-cell reviewer">
                <span class="info-text">
                  {{ getDiaryInfo(item.week, 'reviewer') || '-' }}
                </span>
              </div>
              <div class="content-cell comment">
                <span class="info-text">
                  {{ getDiaryInfo(item.week, 'reviewComment') || '-' }}
                </span>
              </div>
              <div class="content-cell action">
                <el-button 
                  type="primary" 
                  size="small" 
                  @click="openDiaryManagement(getDiaryByWeek(item.week))"
                  :disabled="!getDiaryByWeek(item.week)"
                >
                  查看周记
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 周记管理对话框 -->
      <el-dialog 
        v-model="diaryManagementVisible" 
        title="周记管理" 
        width="800px"
        :destroy-on-close="true"
      >
        <div v-if="currentDiaryPeriod">
          <h4>{{ getWeekLabel(currentDiaryPeriod.weeks[0]) }} - {{ getWeekLabel(currentDiaryPeriod.weeks[1]) }}</h4>
          
          <!-- 周记列表 -->
          <el-card v-if="currentPeriodDiaries.length" style="margin: 10px 0;">
            <div v-for="item in currentPeriodDiaries" :key="item.week" class="diary-detail-item">
              <div class="diary-detail-header">
                <strong>{{ getWeekLabel(item.week) }}：</strong>
                <div class="diary-detail-actions">
                  <el-tag :type="getStatusType(item.status)">{{ getStatusText(item.status) }}</el-tag>
                  <el-button 
                    v-if="item.status === 'SUBMITTED'"
                    type="primary" 
                    size="small" 
                    @click="openReviewDialog(item)"
                  >
                    审核
                  </el-button>
                </div>
              </div>
              <p class="diary-content">{{ item.content }}</p>
              <div v-if="item.reviewComment" class="review-comment">
                <strong>审核意见：</strong>{{ item.reviewComment }}
                <div class="review-meta">
                  审核人：{{ item.reviewer }} | 审核时间：{{ formatDate(item.reviewTime) }}
                </div>
              </div>
            </div>
          </el-card>

          <!-- 评语部分 -->
          <div class="comment-section">
            <el-button type="primary" @click="generateComment" :disabled="!currentDiaryPeriod || currentPeriodDiaries.length === 0">
              生成评语
            </el-button>

            <el-input
              type="textarea"
              v-model="comment"
              rows="10"
              class="comment-textarea"
              placeholder="生成的评语将显示在这里"
            />
            <el-button
              type="success"
              class="save-comment-btn"
              @click="saveComment"
            >
              保存评语
            </el-button>
          </div>
        </div>
      </el-dialog>

      <!-- 审核对话框 -->
      <el-dialog v-model="reviewDialogVisible" title="审核周记" width="500px">
        <h4>第 {{ currentReviewDiary?.week }} 周周记</h4>
        <p class="review-diary-content">{{ currentReviewDiary?.content }}</p>

        <el-form :model="reviewForm" label-width="80px">
          <el-form-item label="审核结果">
            <el-radio-group v-model="reviewForm.status">
              <el-radio label="APPROVED">通过</el-radio>
              <el-radio label="REJECTED">拒绝</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="审核意见">
            <el-select v-model="reviewForm.reviewCommentType" placeholder="请选择审核意见" style="width: 100%;">
              <el-option v-for="item in quickComments" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-input
                v-model="reviewForm.reviewComment"
                type="textarea"
                rows="4"
                class="review-comment-input"
                placeholder="请输入审核意见"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="reviewDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitReview">提交审核</el-button>
        </template>
      </el-dialog>
    </el-card>
  </el-main>
</template>

<script setup>
import { ref, watch, computed } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

const props = defineProps({
  studentView: Object
})

// 周记管理相关
const diaryManagementVisible = ref(false)
const currentDiaryPeriod = ref(null)
const currentPeriodDiaries = ref([])

// 评语相关
const comment = ref('')

// 审核相关
const reviewDialogVisible = ref(false)
const currentReviewDiary = ref(null)
const reviewForm = ref({
  status: 'APPROVED',
  reviewComment: '',
  reviewCommentType: ''
})

// 快速审核意见选项
const quickComments = [
  { value: 'good', label: '表现良好，继续保持' },
  { value: 'count', label: '字数不符合要求' },
  { value: 'ontime', label: '注意按时提交' },
  { value: 'language', label: '未使用专业规范语言' },
  { value: 'custom', label: '自定义意见' }
]

// 审核意见模板
const commentTemplates = {
  good: '表现良好，继续保持。',
  count: '字数不符合要求。',
  ontime: '注意按时提交周记。',
  language: '周记未使用专业规范语言，缺乏工作具体内容。'
}

// 监听审核意见类型变化
watch(() => reviewForm.value.reviewCommentType, (val) => {
  reviewForm.value.reviewComment = commentTemplates[val] || ''
})

const weekPeriodOptions = [
  { label: '第1-2周', weeks: [1, 2] },
  { label: '第3-4周', weeks: [3, 4] },
  { label: '第5-6周', weeks: [5, 6] },
  { label: '第7-8周', weeks: [7, 8] },
  { label: '第9-10周', weeks: [9, 10] },
  { label: '第11-12周', weeks: [11, 12] },
  { label: '第13-14周', weeks: [13, 14] },
  { label: '第15-16周', weeks: [15, 16] },
  { label: '实习总结（第17周）', weeks: ["achievement","practice"] }
]

// 所有周记项目列表
const allDiaryItems = computed(() => {
  const items = []
  // 常规周记 1-16周
  for (let week = 1; week <= 16; week++) {
    items.push({
      key: `week-${week}`,
      week: week,
      label: `第 ${week} 周`
    })
  }
  // 成果总结和实践总结
  items.push(
    { key: 'achievement', week: 'achievement', label: '成果总结' },
    { key: 'practice', week: 'practice', label: '实践总结' }
  )
  return items
})

// 周记排序函数
const sortDiaries = (diaries) => {
  return diaries.sort((a, b) => {
    if (!isNaN(Number(a.week)) && !isNaN(Number(b.week))) {
      return Number(a.week) - Number(b.week)
    }
    return a.week.localeCompare(b.week)
  })
}

// 打开周记管理对话框
const openDiaryManagement = (diary) => {
  const normalizeWeek = (week, isNumeric) => isNumeric ? Number(week) : week
  
  const period = weekPeriodOptions.find(p => {
    const isNumeric = typeof p.weeks[0] === 'number'
    return p.weeks.includes(normalizeWeek(diary.week, isNumeric))
  })
  
  if (!period) return
  
  currentDiaryPeriod.value = period
  const isNumeric = typeof period.weeks[0] === 'number'
  const filteredDiaries = props.studentView.duser.diary.filter(d => 
    period.weeks.includes(normalizeWeek(d.week, isNumeric))
  )
  currentPeriodDiaries.value = sortDiaries(filteredDiaries)
  
  // 加载评语
  const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username
  const allComments = props.studentView.duser.comment || []
  // 对于成果总结和实践总结，使用week值17
  const originalWeek = period.weeks[0]
  const searchWeek = (originalWeek === 'achievement' || originalWeek === 'practice') ? 17 : originalWeek
  const match = allComments.find(c => c.week === searchWeek && c.teachername === teacherName)
  comment.value = match?.content || ''
  
  diaryManagementVisible.value = true
}

// 获取周数标签
const getWeekLabel = (week) => {
  if (typeof week === 'number' || !isNaN(Number(week))) {
    return `第 ${week} 周`
  } else if (week === 'achievement') {
    return '成果总结'
  } else if (week === 'practice') {
    return '实践总结'
  }
  return week
}

// 状态配置
const statusConfig = {
  DRAFT: { text: '草稿', type: 'info' },
  SUBMITTED: { text: '已提交', type: 'warning' },
  APPROVED: { text: '已通过', type: 'success' },
  REJECTED: { text: '已拒绝', type: 'danger' }
}

// 获取状态显示文本
const getStatusText = (status) => statusConfig[status]?.text || '草稿'

// 获取状态标签类型
const getStatusType = (status) => statusConfig[status]?.type || 'info'

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN')
}

// 打开审核对话框
const openReviewDialog = (diary) => {
  currentReviewDiary.value = diary
  reviewForm.value = {
    status: 'APPROVED',
    reviewComment: '',
    reviewCommentType: ''
  }
  reviewDialogVisible.value = true
}

// 生成评语
const generateComment = async () => {
  if (!props.studentView?.student || !currentDiaryPeriod.value) {
    ElMessage.warning('请选择学生和周期')
    return
  }

  try {
    const res = await axios.post('/dteacher/generate-comment', {
      studentId: props.studentView.student.student_number,
      weeks: currentDiaryPeriod.value.weeks
    })
    comment.value = res.data.data
  } catch {
    ElMessage.error('生成评语失败')
  }
}

// 保存评语
const saveComment = async () => {
  if (
    !comment.value ||
    !props.studentView?.student?.student_number ||
    !currentDiaryPeriod.value?.weeks?.[0]
  ) {
    ElMessage.warning('信息不完整，无法保存')
    return
  }

  // 对于成果总结和实践总结，使用week值17
  const originalWeek = currentDiaryPeriod.value.weeks[0]
  const week = (originalWeek === 'achievement' || originalWeek === 'practice') ? 17 : originalWeek
  const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username

  try {
    await axios.patch(`/duser/${props.studentView.student.student_number}`, {
      comment: [
        {
          week,
          teachername: teacherName,
          content: comment.value
        }
      ]
    })
    
    if (props.studentView.duser) {
      if (!props.studentView.duser.comment) {
        props.studentView.duser.comment = []
      }
      
      const existingIndex = props.studentView.duser.comment.findIndex(
        c => c.week === week && c.teachername === teacherName
      )
      
      const newComment = {
        week,
        teachername: teacherName,
        content: comment.value
      }
      
      if (existingIndex >= 0) {
        props.studentView.duser.comment[existingIndex] = newComment
      } else {
        props.studentView.duser.comment.push(newComment)
      }
    }
    
    ElMessage.success('保存成功')
  } catch (err) {
    ElMessage.error('保存失败')
    console.error(err)
  }
}

// 提交审核
const submitReview = async () => {
  if (!currentReviewDiary.value || !reviewForm.value.status) {
    ElMessage.warning('请选择审核结果')
    return
  }

  const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username
  
  try {
    await axios.post(`/duser/${props.studentView.student.student_number}/diary/${currentReviewDiary.value.week}/review`, {
      status: reviewForm.value.status,
      reviewComment: reviewForm.value.reviewComment,
      reviewer: teacherName
    })
    
    // 更新本地数据
    if (props.studentView.duser && props.studentView.duser.diary) {
      const diaryIndex = props.studentView.duser.diary.findIndex(
        d => d.week === currentReviewDiary.value.week
      )
      if (diaryIndex >= 0) {
        props.studentView.duser.diary[diaryIndex].status = reviewForm.value.status
        props.studentView.duser.diary[diaryIndex].reviewComment = reviewForm.value.reviewComment
        props.studentView.duser.diary[diaryIndex].reviewer = teacherName
        props.studentView.duser.diary[diaryIndex].reviewTime = new Date().toISOString()
      }
    }
    
    // 更新显示的周记列表
    const displayIndex = currentPeriodDiaries.value.findIndex(
      d => d.week === currentReviewDiary.value.week
    )
    if (displayIndex >= 0) {
      currentPeriodDiaries.value[displayIndex].status = reviewForm.value.status
      currentPeriodDiaries.value[displayIndex].reviewComment = reviewForm.value.reviewComment
      currentPeriodDiaries.value[displayIndex].reviewer = teacherName
      currentPeriodDiaries.value[displayIndex].reviewTime = new Date().toISOString()
    }
    
    reviewDialogVisible.value = false
    ElMessage.success('审核完成')
  } catch (err) {
    ElMessage.error('审核失败')
    console.error(err)
  }
}

// 获取指定周次的周记数据
const getDiaryByWeek = (week) => {
  if (!props.studentView?.duser?.diary) return null
  return props.studentView.duser.diary.find(d => d.week === week.toString())
}

// 统一获取周记信息的函数
const getDiaryInfo = (week, field) => {
  const diary = getDiaryByWeek(week)
  if (!diary) return null
  
  if (field === 'reviewTime') {
    return diary.reviewTime ? formatDate(diary.reviewTime) : null
  }
  return diary[field] || null
}

// 获取指定周次的周记状态
const getDiaryStatus = (week) => {
  const diary = getDiaryByWeek(week)
  return diary?.status || 'DRAFT'
}
</script>

<style scoped>
.right-main {
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}



.diary-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.diary-item {
  padding: 12px 16px;
  border: 1px solid #eee;
  border-radius: 4px;
  background-color: #fff;
  transition: all 0.3s ease;
}

.diary-item.header {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  font-weight: 500;
}

.diary-item:hover:not(.header) {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.diary-item-content {
  display: grid;
  grid-template-columns: 180px 160px 120px 240px 100px;
  gap: 16px;
  align-items: center;
}

.header-cell {
  font-size: 14px;
  color: #606266;
  font-weight: 500;
  padding: 0 8px;
}

.content-cell {
  display: flex;
  align-items: center;
  padding: 0 8px;
}

.content-cell.week {
  display: flex;
  align-items: center;
  gap: 12px;
}

.week-label {
  font-size: 14px;
  color: #303133;
  font-weight: 500;
  width: 80px;
}

.content-cell.time,
.content-cell.reviewer,
.content-cell.comment {
  color: #606266;
  font-size: 13px;
}

.info-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 100%;
}

.content-cell.action {
  display: flex;
  justify-content: flex-end;
}

.diary-detail-item {
  margin-bottom: 15px;
  border: 1px solid #eee;
  padding: 10px;
  border-radius: 5px;
}

.diary-detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.diary-detail-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.diary-content {
  white-space: pre-line;
}

.review-comment {
  margin-top: 10px;
  padding: 8px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.review-meta {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
}

.comment-section {
  margin-top: 20px;
}

.comment-textarea {
  margin-top: 10px;
}

.save-comment-btn {
  margin-top: 10px;
}

.review-diary-content {
  white-space: pre-line;
  margin: 10px 0;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 4px;
}

.review-comment-input {
  margin-top: 8px;
}
</style>
