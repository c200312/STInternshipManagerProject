<template>
  <el-main class="assessment-main">
    <el-card v-if="props.selectedStudent">
      <h2>成绩信息</h2>
      
      <!-- 总分展示 -->
      <div class="total-score-section">
        <h3>校外实习实践综合成绩评定</h3>
        <div class="total-score-value">
          {{ totalScore }} 分
        </div>
      </div>

      <!-- 分数展示区域 -->
      <div class="score-display-container">
        <!-- 教学单位成绩评定 -->
        <el-card class="score-section">
          <template #header>
            <div class="score-section-header">
              <h3>教学单位成绩评定</h3>
              <el-button 
                type="primary" 
                @click="openSchoolScoreDialog"
                size="small"
              >
                {{ hasSchoolScores ? '修改分数' : '去评分' }}
              </el-button>
            </div>
          </template>
          
          <div v-if="hasSchoolScores" class="score-list">
            <div class="score-item">
              <span class="score-label">学生实习表现评定：</span>
              <span class="score-value">{{ assessmentData.performance_score || 0 }}分</span>
            </div>
            <div class="score-item">
              <span class="score-label">学生校外实习实践总结评定：</span>
              <span class="score-value">{{ assessmentData.summary_score || 0 }}分</span>
            </div>
            <div class="score-item">
              <span class="score-label">学生校外实习实践成果评定：</span>
              <span class="score-value">{{ assessmentData.practice_result_score || 0 }}分</span>
            </div>
            <div class="score-item total">
              <span class="score-label">教学单位成绩：</span>
              <span class="score-value">{{ assessmentData.school_score || 0 }}分</span>
            </div>
          </div>
          <div v-else class="no-score-tip">
            当前未给该学生评分
          </div>
        </el-card>

        <!-- 实习实践单位成绩评定 -->
        <el-card class="score-section">
          <template #header>
            <div class="score-section-header">
              <h3>实习实践单位成绩评定</h3>
              <el-button 
                type="primary" 
                @click="openCompanyScoreDialog"
                size="small"
              >
                {{ hasCompanyScores ? '修改分数' : '去评分' }}
              </el-button>
            </div>
          </template>
          
          <div v-if="hasCompanyScores" class="score-list">
            <div class="score-item">
              <span class="score-label">出勤率评定：</span>
              <span class="score-value">{{ assessmentData.attendance_score || 0 }}分</span>
            </div>
            <div class="score-item">
              <span class="score-label">任务完成情况评定：</span>
              <span class="score-value">{{ assessmentData.task_score || 0 }}分</span>
            </div>
            <div class="score-item">
              <span class="score-label">职业素养养成评定：</span>
              <span class="score-value">{{ assessmentData.professionalism_score || 0 }}分</span>
            </div>
            <div class="score-item total">
              <span class="score-label">校外实习实践单位成绩：</span>
              <span class="score-value">{{ assessmentData.company_score || 0 }}分</span>
            </div>
          </div>
          <div v-else class="no-score-tip">
            当前未给该学生评分
          </div>
        </el-card>
      </div>

      <!-- 教学单位评分对话框 -->
      <el-dialog
        v-model="schoolScoreDialogVisible"
        title="教学单位成绩评定"
        width="600px"
      >
        <el-form :model="schoolScoreForm" label-position="top">
          <div class="score-form-item">
            <div class="score-form-header">
              <h4>1. 学生实习表现评定</h4>
              <span class="score-max">满分20分</span>
            </div>
            <p class="score-description">
              校外实习实践期间，遵守实习实践单位的规章制度，按时出勤，不迟到，不早退，无旷工现象。
              校外实习实践期间听从指导教师的指挥，有吃苦耐劳和较强的团队合作精神，尊重师长，谦虚好学，对校外实习实践任务积极主动，有较强的安全意识。
            </p>
            <el-input-number
              v-model="schoolScoreForm.performance_score"
              :min="0"
              :max="20"
              size="large"
            />
          </div>

          <div class="score-form-item">
            <div class="score-form-header">
              <h4>2. 学生校外实习实践总结评定</h4>
              <span class="score-max">满分40分</span>
            </div>
            <p class="score-description">
              按校外实习实践总结的有关要求及评价标准等进行评价。
            </p>
            <el-input-number
              v-model="schoolScoreForm.summary_score"
              :min="0"
              :max="40"
              size="large"
            />
          </div>

          <div class="score-form-item">
            <div class="score-form-header">
              <h4>3. 学生校外实习实践成果评定</h4>
              <span class="score-max">满分40分</span>
            </div>
            <p class="score-description">
              按校外实习实践成果的有关要求及评价标准等进行评价。
            </p>
            <el-input-number
              v-model="schoolScoreForm.practice_result_score"
              :min="0"
              :max="40"
              size="large"
            />
          </div>

          <div class="score-form-item total-score">
            <div class="score-form-header">
              <h4>4. 教学单位成绩</h4>
            </div>
            <div class="auto-calculated-score">
              {{ schoolScore }} 分
            </div>
          </div>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="schoolScoreDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="saveSchoolScores" :loading="saving">
              保存评分
            </el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 实习实践单位评分对话框 -->
      <el-dialog
        v-model="companyScoreDialogVisible"
        title="实习实践单位成绩评定"
        width="600px"
      >
        <el-form :model="companyScoreForm" label-position="top">
          <div class="score-form-item">
            <div class="score-form-header">
              <h4>1. 出勤率评定</h4>
              <span class="score-max">满分20分</span>
            </div>
            <p class="score-description">
              校外实习实践期间，遵守实习实践单位的规章制度，按时出勤，不迟到，不早退，无旷工现象。
              迟到或早退一次扣2分，旷工一次扣6分。病事假需持有关证明，凡病事假累计超过三分之一者，此栏成绩为0分。
            </p>
            <el-input-number
              v-model="companyScoreForm.attendance_score"
              :min="0"
              :max="20"
              size="large"
            />
          </div>

          <div class="score-form-item">
            <div class="score-form-header">
              <h4>2. 任务完成情况评定</h4>
              <span class="score-max">满分60分</span>
            </div>
            <p class="score-description">
              按校外实习实践计划完成任务。在校外实习实践过程中勤于思考，能发现问题并及时解决。
              凡病事假累计超过三分之一者，此栏成绩为0分。
            </p>
            <el-input-number
              v-model="companyScoreForm.task_score"
              :min="0"
              :max="60"
              size="large"
            />
          </div>

          <div class="score-form-item">
            <div class="score-form-header">
              <h4>3. 职业素养养成评定</h4>
              <span class="score-max">满分20分</span>
            </div>
            <p class="score-description">
              校外实习实践期间听从指导教师的指挥，有吃苦耐劳和较强的团队合作精神，尊重师长，谦虚好学，对实习实践任务积极主动，有较强的安全意识。
            </p>
            <el-input-number
              v-model="companyScoreForm.professionalism_score"
              :min="0"
              :max="20"
              size="large"
            />
          </div>

          <div class="score-form-item total-score">
            <div class="score-form-header">
              <h4>4. 校外实习实践单位成绩</h4>
            </div>
            <div class="auto-calculated-score">
              {{ companyScore }} 分
            </div>
          </div>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="companyScoreDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="saveCompanyScores" :loading="saving">
              保存评分
            </el-button>
          </span>
        </template>
      </el-dialog>
    </el-card>

    <el-empty v-else description="请选择学生查看成绩" />
  </el-main>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

const props = defineProps({
  selectedStudent: {
    type: Object,
    default: null
  }
})

// 数据相关
const saving = ref(false)
const assessmentData = ref({
  performance_score: 0,
  summary_score: 0,
  practice_result_score: 0,
  school_score: 0,
  attendance_score: 0,
  task_score: 0,
  professionalism_score: 0,
  company_score: 0,
  total_score: 0
})

// 对话框相关
const schoolScoreDialogVisible = ref(false)
const companyScoreDialogVisible = ref(false)

// 表单数据
const schoolScoreForm = ref({
  performance_score: 0,
  summary_score: 0,
  practice_result_score: 0
})

const companyScoreForm = ref({
  attendance_score: 0,
  task_score: 0,
  professionalism_score: 0
})

// 计算属性
const hasSchoolScores = computed(() => {
  return assessmentData.value.performance_score > 0 ||
         assessmentData.value.summary_score > 0 ||
         assessmentData.value.practice_result_score > 0
})

const hasCompanyScores = computed(() => {
  return assessmentData.value.attendance_score > 0 ||
         assessmentData.value.task_score > 0 ||
         assessmentData.value.professionalism_score > 0
})

const schoolScore = computed(() => {
  return Math.round(
    Number(schoolScoreForm.value.performance_score || 0) +
    Number(schoolScoreForm.value.summary_score || 0) +
    Number(schoolScoreForm.value.practice_result_score || 0)
  )
})

const companyScore = computed(() => {
  return Math.round(
    Number(companyScoreForm.value.attendance_score || 0) +
    Number(companyScoreForm.value.task_score || 0) +
    Number(companyScoreForm.value.professionalism_score || 0)
  )
})

// 计算总分（学校评分*50% + 企业评分*50%）
const totalScore = computed(() => {
  const schoolPart = (assessmentData.value.school_score || 0) * 0.5
  const companyPart = (assessmentData.value.company_score || 0) * 0.5
  return Math.round(schoolPart + companyPart)
})

// 方法
const loadAssessmentData = async () => {
  if (!props.selectedStudent?.student?.s_id) return
  
  try {
    const res = await axios.get(`/assessments/${props.selectedStudent.student.s_id}`)
    if (res.data?.data) {
      assessmentData.value = {
        ...assessmentData.value,
        ...res.data.data
      }
    } else {
      // 如果没有数据，重置为默认值
      assessmentData.value = {
        performance_score: 0,
        summary_score: 0,
        practice_result_score: 0,
        school_score: 0,
        attendance_score: 0,
        task_score: 0,
        professionalism_score: 0,
        company_score: 0,
        total_score: 0
      }
    }
  } catch (error) {
    console.error('加载评分数据失败:', error)
    ElMessage.error('加载评分数据失败')
  }
}

const openSchoolScoreDialog = () => {
  schoolScoreForm.value = {
    performance_score: assessmentData.value.performance_score || 0,
    summary_score: assessmentData.value.summary_score || 0,
    practice_result_score: assessmentData.value.practice_result_score || 0
  }
  schoolScoreDialogVisible.value = true
}

const openCompanyScoreDialog = () => {
  companyScoreForm.value = {
    attendance_score: assessmentData.value.attendance_score || 0,
    task_score: assessmentData.value.task_score || 0,
    professionalism_score: assessmentData.value.professionalism_score || 0
  }
  companyScoreDialogVisible.value = true
}

const saveSchoolScores = async () => {
  if (!props.selectedStudent?.student?.s_id) {
    ElMessage.warning('请选择学生')
    return
  }

  saving.value = true
  try {
    const payload = {
      s_id: props.selectedStudent.student.s_id,
      performance_score: Math.round(schoolScoreForm.value.performance_score),
      summary_score: Math.round(schoolScoreForm.value.summary_score),
      practice_result_score: Math.round(schoolScoreForm.value.practice_result_score),
      school_score: schoolScore.value,
      total_score: Math.round(schoolScore.value * 0.5 + (assessmentData.value.company_score || 0) * 0.5)
    }

    const res = await axios.put('/assessments', payload)
    if (res.status >= 200 && res.status < 300) {
      ElMessage.success('保存成功')
      schoolScoreDialogVisible.value = false
      await loadAssessmentData() // 重新加载数据以更新显示
    } else {
      throw new Error('保存失败')
    }
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error('保存失败，请重试')
  } finally {
    saving.value = false
  }
}

const saveCompanyScores = async () => {
  if (!props.selectedStudent?.student?.s_id) {
    ElMessage.warning('请选择学生')
    return
  }

  saving.value = true
  try {
    const payload = {
      s_id: props.selectedStudent.student.s_id,
      attendance_score: Math.round(companyScoreForm.value.attendance_score),
      task_score: Math.round(companyScoreForm.value.task_score),
      professionalism_score: Math.round(companyScoreForm.value.professionalism_score),
      company_score: companyScore.value,
      total_score: Math.round((assessmentData.value.school_score || 0) * 0.5 + companyScore.value * 0.5)
    }

    const res = await axios.put('/assessments', payload)
    if (res.status >= 200 && res.status < 300) {
      ElMessage.success('保存成功')
      companyScoreDialogVisible.value = false
      await loadAssessmentData() // 重新加载数据以更新显示
    } else {
      throw new Error('保存失败')
    }
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error('保存失败，请重试')
  } finally {
    saving.value = false
  }
}

// 监听学生选择变化
watch(() => props.selectedStudent?.student?.s_id, (newId) => {
  if (newId) {
    loadAssessmentData()
  }
}, { immediate: true })
</script>

<style scoped>
.assessment-main {
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}

.total-score-section {
  text-align: center;
  margin: 20px 0 30px;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.total-score-section h3 {
  color: #303133;
  margin-bottom: 15px;
}

.total-score-value {
  font-size: 36px;
  font-weight: bold;
  color: #409EFF;
}

.score-display-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-top: 20px;
}

.score-section {
  height: 100%;
}

.score-section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.score-section-header h3 {
  margin: 0;
  font-size: 16px;
}

.score-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.score-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.score-item.total {
  margin-top: 10px;
  background-color: #ecf5ff;
  color: #409EFF;
}

.score-label {
  font-size: 14px;
  color: #606266;
}

.score-value {
  font-weight: bold;
  color: #303133;
}

.no-score-tip {
  text-align: center;
  padding: 30px;
  color: #909399;
  font-size: 14px;
}

/* 评分表单样式 */
.score-form-item {
  margin-bottom: 25px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.score-form-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.score-form-header h4 {
  margin: 0;
  color: #303133;
}

.score-max {
  color: #909399;
  font-size: 14px;
}

.score-description {
  margin: 10px 0 15px;
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
}

.total-score {
  background-color: #ecf5ff;
}

.auto-calculated-score {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  text-align: center;
  margin-top: 10px;
}
</style>