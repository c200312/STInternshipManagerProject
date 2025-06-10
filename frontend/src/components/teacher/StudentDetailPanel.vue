<template>
  <el-main class="right-main">
    <el-card v-if="studentView">
      <h2>{{ studentView.student.student_name}} 的信息</h2>
      <p>学号: {{ studentView.student.student_number }}</p>
      <p>班级: {{ studentView.student.stu_class}}</p>
      <p>实习单位: {{ studentView.duser?.company?.[0]?.name || '无' }}</p>
      
      <div class="week-selector" style="margin: 15px 0;">
        <label style="margin-right: 10px;">评语周期：</label>
        <el-select v-model="selectedLabel" placeholder="请选择评语周期" style="width: 200px;">
          <el-option
              v-for="option in weekPeriodOptions"
              :key="option.label"
              :label="option.label"
              :value="option.label"
          />
        </el-select>
      </div>

      <el-card v-if="displayedDiaries.length" style="margin: 10px 0;">
        <h4>参考周记：</h4>
        <div
            v-for="item in displayedDiaries"
            :key="item.week"
            style="margin-bottom: 15px; border: 1px solid #eee; padding: 10px; border-radius: 5px;"
        >
          <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
             <strong>{{ getWeekLabel(item.week) }}：</strong>
            <div style="display: flex; align-items: center; gap: 10px;">
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
          <p style="white-space: pre-line;">{{ item.content }}</p>
          <div v-if="item.reviewComment" style="margin-top: 10px; padding: 8px; background-color: #f5f5f5; border-radius: 4px;">
            <strong>审核意见：</strong>{{ item.reviewComment }}
            <div style="font-size: 12px; color: #666; margin-top: 5px;">
              审核人：{{ item.reviewer }} | 审核时间：{{ formatDate(item.reviewTime) }}
            </div>
          </div>
        </div>
      </el-card>

      <el-button type="primary" @click="generateComment" :disabled="!selectedPeriod || displayedDiaries.length === 0"
      >
        生成评语
      </el-button>

      <el-input
          type="textarea"
          v-model="comment"
          rows="10"
          style="margin-top: 10px;"
          placeholder="生成的评语将显示在这里"
      />
      <el-button
          type="success"
          style="margin-top: 10px;"
          @click="saveComment"
      >
        保存评语
      </el-button>

    </el-card>

    <!-- 审核对话框 -->
    <el-dialog v-model="reviewDialogVisible" title="审核周记" width="500px">
      <div>
        <h4>第 {{ currentReviewDiary?.week }} 周周记</h4>
        <p style="white-space: pre-line; margin: 10px 0; padding: 10px; background-color: #f9f9f9; border-radius: 4px;">{{ currentReviewDiary?.content }}</p>
        
        <el-form :model="reviewForm" label-width="80px">
          <el-form-item label="审核结果">
            <el-radio-group v-model="reviewForm.status">
              <el-radio label="APPROVED">通过</el-radio>
              <el-radio label="REJECTED">拒绝</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="审核意见">
            <el-input
              v-model="reviewForm.reviewComment"
              type="textarea"
              rows="4"
              placeholder="请输入审核意见"
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

  </el-main>
</template>

<script setup>
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

const props = defineProps({
  studentView: Object
})

const selectedLabel = ref('第1-2周')
const selectedPeriod = ref(null)
const displayedDiaries = ref([])
const comment = ref('')

// 审核相关
const reviewDialogVisible = ref(false)
const currentReviewDiary = ref(null)
const reviewForm = ref({
  status: 'APPROVED',
  reviewComment: ''
})

const weekPeriodOptions = [
  { label: '第1-2周', weeks: [1, 2] },
  { label: '第3-4周', weeks: [3, 4] },
  { label: '第5-6周', weeks: [5, 6] },
  { label: '第7-8周', weeks: [7, 8] },
  { label: '第9-10周', weeks: [9, 10] },
  { label: '第11-12周', weeks: [11, 12] },
  { label: '实习总结（第13周）', weeks: ["achievement","practice"] }
]

watch(selectedLabel, () => {
  if (
      !selectedLabel.value ||
      !props.studentView ||
      !props.studentView.duser ||
      !Array.isArray(props.studentView.duser.diary)
  ) {
    displayedDiaries.value = []
    selectedPeriod.value = null
    comment.value = ''
    return
  }

  const period = weekPeriodOptions.find(o => o.label === selectedLabel.value)
  if (!period) return

  selectedPeriod.value = period

  displayedDiaries.value = props.studentView.duser.diary
      .filter(d => {
        // 对于数字周期，转换为数字进行比较
        if (typeof period.weeks[0] === 'number') {
          return period.weeks.includes(Number(d.week))
        }
        // 对于字符串周期（如achievement, practice），直接比较
        return period.weeks.includes(d.week)
      })
      .sort((a, b) => {
        // 数字周期按数字排序
        if (!isNaN(Number(a.week)) && !isNaN(Number(b.week))) {
          return Number(a.week) - Number(b.week)
        }
        // 字符串周期按字母排序
        return a.week.localeCompare(b.week)
      })

  const week = period.weeks[0]
  const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username
  const allComments = props.studentView.duser.comment || []
  const match = allComments.find(
      c => c.week === week && c.teachername === teacherName
  )
  comment.value = match ? match.content : ''
})
watch(
    () => props.studentView,
    (newVal) => {
      if (!newVal) return

      // 重置状态
      selectedLabel.value = '第1-2周'
      selectedPeriod.value = null
      displayedDiaries.value = []
      comment.value = ''
      
      // 手动触发selectedLabel的处理逻辑
      if (newVal.duser && Array.isArray(newVal.duser.diary)) {
        const period = weekPeriodOptions.find(o => o.label === '第1-2周')
        if (period) {
          selectedPeriod.value = period
          displayedDiaries.value = newVal.duser.diary
              .filter(d => {
                // 对于数字周期，转换为数字进行比较
                if (typeof period.weeks[0] === 'number') {
                  return period.weeks.includes(Number(d.week))
                }
                // 对于字符串周期（如achievement, practice），直接比较
                return period.weeks.includes(d.week)
              })
              .sort((a, b) => {
                // 数字周期按数字排序
                if (!isNaN(Number(a.week)) && !isNaN(Number(b.week))) {
                  return Number(a.week) - Number(b.week)
                }
                // 字符串周期按字母排序
                return a.week.localeCompare(b.week)
              })
          
          const week = period.weeks[0]
          const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username
          const allComments = newVal.duser.comment || []
          const match = allComments.find(
              c => c.week === week && c.teachername === teacherName
          )
          comment.value = match ? match.content : ''
        }
      }
    },
    { immediate: true }
)


const generateComment = async () => {
  if (!props.studentView?.student || !selectedPeriod.value) {
    ElMessage.warning('请选择学生和周期')
    return
  }

  try {
    const res = await axios.post('/dteacher/generate-comment', {
      studentId: props.studentView.student.student_number,
      weeks: selectedPeriod.value.weeks
    })
    comment.value = res.data.data
  } catch {
    ElMessage.error('生成评语失败')
  }
}
const saveComment = async () => {
  if (
      !comment.value ||
      !props.studentView?.student?.student_number ||
      !selectedPeriod.value?.weeks?.[0]
  ) {
    ElMessage.warning('信息不完整，无法保存')
    return
  }

  const week = selectedPeriod.value.weeks[0] // 保存为当前周期第一周
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
    
    // 更新本地数据，避免需要刷新页面
    if (props.studentView.duser) {
      if (!props.studentView.duser.comment) {
        props.studentView.duser.comment = []
      }
      
      // 查找是否已存在相同周期和教师的评语
      const existingIndex = props.studentView.duser.comment.findIndex(
        c => c.week === week && c.teachername === teacherName
      )
      
      const newComment = {
        week,
        teachername: teacherName,
        content: comment.value
      }
      
      if (existingIndex >= 0) {
        // 更新现有评语
        props.studentView.duser.comment[existingIndex] = newComment
      } else {
        // 添加新评语
        props.studentView.duser.comment.push(newComment)
      }
    }
    
    ElMessage.success('保存成功')
  } catch (err) {
    ElMessage.error('保存失败')
    console.error(err)
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

// 打开审核对话框
const openReviewDialog = (diary) => {
  currentReviewDiary.value = diary
  reviewForm.value = {
    status: 'APPROVED',
    reviewComment: ''
  }
  reviewDialogVisible.value = true
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
    const displayIndex = displayedDiaries.value.findIndex(
      d => d.week === currentReviewDiary.value.week
    )
    if (displayIndex >= 0) {
      displayedDiaries.value[displayIndex].status = reviewForm.value.status
      displayedDiaries.value[displayIndex].reviewComment = reviewForm.value.reviewComment
      displayedDiaries.value[displayIndex].reviewer = teacherName
      displayedDiaries.value[displayIndex].reviewTime = new Date().toISOString()
    }
    
    reviewDialogVisible.value = false
    ElMessage.success('审核完成')
  } catch (err) {
    ElMessage.error('审核失败')
    console.error(err)
  }
}

</script>

<style scoped>
.right-main {
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}

</style>
