<template>
  <el-main class="right-main">
    <el-card v-if="studentView" class="diary-status-card">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <h3>周记状态总览</h3>
        </div>
      </template>
      
      <!-- 周记状态列表 -->
      <div class="diary-list">
        <!-- 表头 -->
        <div class="diary-item header">
          <div class="diary-item-content">
            <div class="header-cell week">周次</div>
            <div class="header-cell status">状态</div>
            <div class="header-cell time">审核时间</div>
            <div class="header-cell comment">审核原因</div>
            <div class="header-cell action">操作</div>
          </div>
        </div>

        <!-- 常规周记 -->
        <div v-for="week in 16" :key="week" class="diary-item">
          <div class="diary-item-content">
            <div class="content-cell week">
              <span class="week-label">第 {{ week }} 周</span>
            </div>
            <div class="content-cell status">
              <el-tag :type="getStatusType(getDiaryStatus(week))" size="small">
                {{ getStatusText(getDiaryStatus(week)) }}
              </el-tag>
            </div>
            <div class="content-cell time">
              <span v-if="getDiaryReviewTime(week)" class="info-text">
                {{ formatDate(getDiaryReviewTime(week)) }}
              </span>
              <span v-else class="info-text">-</span>
            </div>
            <div class="content-cell comment">
              <span v-if="getDiaryReviewComment(week)" class="info-text">
                {{ getDiaryReviewComment(week) }}
              </span>
              <span v-else class="info-text">-</span>
            </div>
            <div class="content-cell action">
              <template v-if="isShowViewCommentBtn(week)">
                <el-button type="primary" size="small" @click="openDiaryManagement(getDiaryByWeek(week))" :disabled="!getDiaryByWeek(week)">查看周记/评语</el-button>
              </template>
              <template v-else-if="isShowReReviewBtn(week)">
                <el-button type="warning" size="small" @click="openReviewDialog(getDiaryByWeek(week))" :disabled="!getDiaryByWeek(week)">重新审核</el-button>
              </template>
              <template v-else-if="isShowReviewBtn(week)">
                <el-button type="warning" size="small" @click="openReviewDialog(getDiaryByWeek(week))" :disabled="!getDiaryByWeek(week)">去审核</el-button>
              </template>
              <template v-else>
                -
              </template>
            </div>
          </div>
        </div>

        <!-- 成果总结 -->
        <div class="diary-item">
          <div class="diary-item-content">
            <div class="content-cell week">
              <span class="week-label">成果总结</span>
            </div>
            <div class="content-cell status">
              <el-tag :type="getStatusType(getDiaryStatus('achievement'))" size="small">
                {{ getStatusText(getDiaryStatus('achievement')) }}
              </el-tag>
            </div>
            <div class="content-cell time">
              <span v-if="getDiaryReviewTime('achievement')" class="info-text">
                {{ formatDate(getDiaryReviewTime('achievement')) }}
              </span>
              <span v-else class="info-text">-</span>
            </div>
            <div class="content-cell comment">
              <span v-if="getDiaryReviewComment('achievement')" class="info-text">
                {{ getDiaryReviewComment('achievement') }}
              </span>
              <span v-else class="info-text">-</span>
            </div>
            <div class="content-cell action">
              <template v-if="isShowViewCommentBtn('achievement')">
                <el-button type="primary" size="small" @click="openDiaryManagement(getDiaryByWeek('achievement'))" :disabled="!getDiaryByWeek('achievement')">查看周记/评语</el-button>
              </template>
              <template v-else-if="isShowReReviewBtn('achievement')">
                <el-button type="warning" size="small" @click="openReviewDialog(getDiaryByWeek('achievement'))" :disabled="!getDiaryByWeek('achievement')">重新审核</el-button>
              </template>
              <template v-else-if="isShowReviewBtn('achievement')">
                <el-button type="warning" size="small" @click="openReviewDialog(getDiaryByWeek('achievement'))" :disabled="!getDiaryByWeek('achievement')">去审核</el-button>
              </template>
              <template v-else>
                -
              </template>
            </div>
          </div>
        </div>

        <!-- 实践总结 -->
        <div class="diary-item">
          <div class="diary-item-content">
            <div class="content-cell week">
              <span class="week-label">实践总结</span>
            </div>
            <div class="content-cell status">
              <el-tag :type="getStatusType(getDiaryStatus('practice'))" size="small">
                {{ getStatusText(getDiaryStatus('practice')) }}
              </el-tag>
            </div>
            <div class="content-cell time">
              <span v-if="getDiaryReviewTime('practice')" class="info-text">
                {{ formatDate(getDiaryReviewTime('practice')) }}
              </span>
              <span v-else class="info-text">-</span>
            </div>
            <div class="content-cell comment">
              <span v-if="getDiaryReviewComment('practice')" class="info-text">
                {{ getDiaryReviewComment('practice') }}
              </span>
              <span v-else class="info-text">-</span>
            </div>
            <div class="content-cell action">
              <template v-if="isShowViewCommentBtn('practice')">
                <el-button type="primary" size="small" @click="openDiaryManagement(getDiaryByWeek('practice'))" :disabled="!getDiaryByWeek('practice')">查看周记/评语</el-button>
              </template>
              <template v-else-if="isShowReReviewBtn('practice')">
                <el-button type="warning" size="small" @click="openReviewDialog(getDiaryByWeek('practice'))" :disabled="!getDiaryByWeek('practice')">重新审核</el-button>
              </template>
              <template v-else-if="isShowReviewBtn('practice')">
                <el-button type="warning" size="small" @click="openReviewDialog(getDiaryByWeek('practice'))" :disabled="!getDiaryByWeek('practice')">去审核</el-button>
              </template>
              <template v-else>
                -
              </template>
            </div>
          </div>
        </div>
      </div>

      <!-- 周记管理对话框 -->
      <el-dialog 
        v-model="diaryManagementVisible" 
        title="周记管理" 
        width="1000px"
        :destroy-on-close="true"
        top="5vh"
      >
        <div v-if="currentDiaryPeriod" class="diary-manage-flex">
          <div class="diary-manage-left">
            <div class="student-info">
              <span>学生：{{ props.studentView?.student?.student_name || '-' }}</span>
              <span style="margin-left: 32px;">学号：{{ props.studentView?.student?.student_number || '-' }}</span>
            </div>
            <div v-for="item in currentPeriodDiaries" :key="item.week" class="diary-content-box">
              <div class="diary-title">{{ getWeekLabel(item.week) }}</div>
              <el-scrollbar height="120">
                <div class="diary-content-text">{{ item.content || '暂无内容' }}</div>
              </el-scrollbar>
            </div>
          </div>
          <div class="diary-manage-right">
            <div class="comment-title">评语</div>
            <el-input
              type="textarea"
              v-model="comment"
              rows="10"
              class="comment-input"
              placeholder="生成的评语将显示在这里"
            />
            <div class="comment-btns">
              <el-button type="primary" @click="generateComment" :disabled="isGenerateCommentDisabled">生成评语</el-button>
              <el-button type="success" @click="saveComment" style="margin-left: 16px;">保存评语</el-button>
            </div>
          </div>
        </div>
      </el-dialog>

      <!-- 审核对话框 -->
      <el-dialog v-model="reviewDialogVisible" title="审核周记" width="500px">
        <div>
          <h4>第 {{ currentReviewDiary?.week }} 周周记</h4>
          <p style="white-space: pre-line; margin: 10px 0; padding: 10px; background-color: #f9f9f9; border-radius: 4px;">{{ currentReviewDiary?.content }}</p >

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
                  v-if="reviewForm.reviewCommentType === 'custom'"
                  v-model="reviewForm.reviewComment"
                  type="textarea"
                  rows="4"
                  style="margin-top: 8px;"
                  placeholder="请输入自定义审核意见"
              />
              <el-input
                  v-else
                  v-model="reviewForm.reviewComment"
                  type="textarea"
                  rows="4"
                  style="margin-top: 8px;"
                  :readonly="true"
              />
            </el-form-item>
          </el-form>
        </div>
        <template #footer>
    <span class="dialog-footer">
      <el-button @click="reviewDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReview">提交审核</el-button>
    </span>
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

// 监听审核意见类型变化
watch(() => reviewForm.value.reviewCommentType, (val) => {
  if (val === 'count') {
    reviewForm.value.reviewComment = '字数不符合要求。'
  } else if (val === 'ontime') {
    reviewForm.value.reviewComment = '注意按时提交周记。'
  } else if (val === 'language') {
    reviewForm.value.reviewComment = '周记未使用专业规范语言，缺乏工作具体内容。'
  } else if (val === 'good') {
    reviewForm.value.reviewComment = '表现良好，继续保持。'
  } else if (val === 'custom') {
    reviewForm.value.reviewComment = ''
  } else {
    reviewForm.value.reviewComment = ''
  }
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

// 计算周记状态列表
const diaryStatusList = computed(() => {
  if (!props.studentView?.duser?.diary) return []
  
  return props.studentView.duser.diary.map(diary => ({
    week: diary.week,
    status: diary.status,
    reviewTime: diary.reviewTime,
    reviewer: diary.reviewer
  })).sort((a, b) => {
    if (!isNaN(Number(a.week)) && !isNaN(Number(b.week))) {
      return Number(a.week) - Number(b.week)
    }
    return a.week.localeCompare(b.week)
  })
})

// 打开周记管理对话框
const openDiaryManagement = (diary) => {
  const period = weekPeriodOptions.find(p => {
    if (typeof p.weeks[0] === 'number') {
      return p.weeks.includes(Number(diary.week))
    }
    return p.weeks.includes(diary.week)
  })
  
  if (period) {
    currentDiaryPeriod.value = period
    currentPeriodDiaries.value = props.studentView.duser.diary
      .filter(d => {
        if (typeof period.weeks[0] === 'number') {
          return period.weeks.includes(Number(d.week))
        }
        return period.weeks.includes(d.week)
      })
      .sort((a, b) => {
        if (!isNaN(Number(a.week)) && !isNaN(Number(b.week))) {
          return Number(a.week) - Number(b.week)
        }
        return a.week.localeCompare(b.week)
      })
    
    // 加载评语
    const week = period.weeks[0]
    const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username
    const allComments = props.studentView.duser.comment || []
    const match = allComments.find(
      c => c.week === week && c.teachername === teacherName
    )
    comment.value = match ? match.content : ''
    
    diaryManagementVisible.value = true
  }
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

// 获取状态显示文本
const getStatusText = (status) => {
  const statusMap = {
    'DRAFT': '未完成',
    'SUBMITTED': '待审核',
    'APPROVED': '已通过',
    'REJECTED': '已拒绝'
  }
  return statusMap[status] || '未完成'
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

  const week = currentDiaryPeriod.value.weeks[0]
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

// 获取指定周次的周记状态
const getDiaryStatus = (week) => {
  if (!props.studentView?.duser?.diary) return 'DRAFT'
  const diary = props.studentView.duser.diary.find(d => d.week === week.toString())
  return diary?.status || 'DRAFT'
}

// 获取指定周次的审核时间
const getDiaryReviewTime = (week) => {
  if (!props.studentView?.duser?.diary) return null
  const diary = props.studentView.duser.diary.find(d => d.week === week.toString())
  return diary?.reviewTime
}

// 获取指定周次的审核人
const getDiaryReviewer = (week) => {
  if (!props.studentView?.duser?.diary) return null
  const diary = props.studentView.duser.diary.find(d => d.week === week.toString())
  return diary?.reviewer
}

// 获取指定周次的周记数据
const getDiaryByWeek = (week) => {
  if (!props.studentView?.duser?.diary) return null
  return props.studentView.duser.diary.find(d => d.week === week.toString())
}

// 获取指定周次的审核原因
const getDiaryReviewComment = (week) => {
  if (!props.studentView?.duser?.diary) return null
  const diary = props.studentView.duser.diary.find(d => d.week === week.toString())
  return diary?.reviewComment
}

// 生成评语按钮禁用逻辑
const isGenerateCommentDisabled = computed(() => {
  if (!currentDiaryPeriod.value || !currentPeriodDiaries.value.length) return true;
  const weeks = currentDiaryPeriod.value.weeks;
  // 只有两周都为已通过才可用
  return !weeks.every(w => getDiaryStatus(w) === 'APPROVED');
});

// 新增按钮显示逻辑方法
const isShowViewCommentBtn = (week) => {
  // 获取当前周期
  const period = weekPeriodOptions.find(p => p.weeks.includes(Number(week)));
  if (!period) return false;
  const diaries = period.weeks.map(w => getDiaryStatus(w));
  // 只要当前周为已通过就显示按钮
  return getDiaryStatus(week) === 'APPROVED';
};
const isShowReReviewBtn = (week) => {
  // 当前周为已拒绝
  return getDiaryStatus(week) === 'REJECTED';
};
const isShowReviewBtn = (week) => {
  // 当前周为待审核
  return getDiaryStatus(week) === 'SUBMITTED';
};
</script>

<style scoped>
.right-main {
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}

.diary-status-card {
  margin-bottom: 20px;
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
  grid-template-columns: 100px 100px 160px 1fr 140px;
  gap: 8px;
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
  max-width: 260px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
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

.el-table {
  margin-top: 10px;
}

.diary-manage-flex {
  display: flex;
  flex-direction: row;
  min-height: 500px;
  max-height: 70vh;
  gap: 32px;
}
.diary-manage-left {
  flex: 1.2;
  display: flex;
  flex-direction: column;
  min-width: 320px;
}
.student-info {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 16px;
}
.diary-content-box {
  background: #fafbfc;
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  margin-bottom: 16px;
  padding: 12px 16px;
  min-height: 150px;
  max-height: 180px;
  display: flex;
  flex-direction: column;
}
.diary-title {
  font-weight: 600;
  margin-bottom: 8px;
}
.diary-content-text {
  font-size: 14px;
  color: #333;
  white-space: pre-line;
}
.diary-manage-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 320px;
  max-width: 400px;
  height: 100%;
}
.comment-title {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 12px;
}
.comment-input {
  width: 100%;
  min-height: 220px;
  margin-bottom: 24px;
}
.comment-btns {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  gap: 16px;
}
</style>
