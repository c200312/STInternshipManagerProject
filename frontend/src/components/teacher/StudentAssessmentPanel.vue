<template>
  <el-main class="assessment-main">
    <el-card v-if="students.length">
      <h2>学生评分管理</h2>
      
      <!-- 评分表单 -->
      <div v-if="props.selectedStudent">
        <h3>{{ props.selectedStudent.student.student_name }} 的评分</h3>
        <p>学号: {{ props.selectedStudent.student.student_number }}</p>
        <p>班级: {{ props.selectedStudent.student.stu_class }}</p>
        
        <el-form :model="assessmentForm" label-width="120px" style="max-width: 600px;">
          <el-form-item label="岗位表现：">
            <el-input-number
                v-model="assessmentForm.performance_score"
                :min="0"
                :max="25"
                placeholder="0-20分"
            />
            <span style="margin-left: 10px; color: #666;">（满分20分）</span>
          </el-form-item>
          
          <el-form-item label="总结报告：">
            <el-input-number
                v-model="assessmentForm.summary_score"
                :min="0"
                :max="25"
                placeholder="0-40分"
            />
            <span style="margin-left: 10px; color: #666;">（满分40分）</span>
          </el-form-item>
          
          <el-form-item label="实习成果：">
            <el-input-number
                v-model="assessmentForm.practice_result_score"
                :min="0"
                :max="25"
                placeholder="0-40分"
            />
            <span style="margin-left: 10px; color: #666;">（满分40分）</span>
          </el-form-item>
          
          <el-form-item label="学校评分：">
            <span style="font-size: 16px; font-weight: bold; color: #409eff;">
              {{ schoolScore }} 分
            </span>
            <span style="margin-left: 10px; color: #666;">（满分50）</span>
          </el-form-item>
          

          
          <el-form-item label="教学单位评语：">
            <el-input
                v-model="assessmentForm.teachingUnitComment"
                type="textarea"
                :rows="4"
                placeholder="请输入教学单位评语"
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="saveAssessment" :loading="saving">
              保存评分
            </el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <el-empty v-else description="请选择学生进行评分" />
    </el-card>
    
    <el-empty v-else description="暂无学生数据" />
  </el-main>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

const props = defineProps({
  students: {
    type: Array,
    default: () => []
  },
  selectedStudent: {
    type: Object,
    default: null
  }
})

const saving = ref(false)

// 用于缓存每个学生的评分数据
const studentAssessmentCache = ref(new Map())

const assessmentForm = ref({
  performance_score: 0,
  summary_score: 0,
  practice_result_score: 0,
  teachingUnitComment: ''
})

// 计算学校评分（其他三项总和除以2）
const schoolScore = computed(() => {
  const otherScores = (
    Number(assessmentForm.value.performance_score || 0) +
    Number(assessmentForm.value.summary_score || 0) +
    Number(assessmentForm.value.practice_result_score || 0)
  )
  return Math.round(otherScores / 2 * 100) / 100 // 保留两位小数
})

// 计算总分
const totalScore = computed(() => {
  return (
    Number(assessmentForm.value.performance_score || 0) +
    Number(assessmentForm.value.summary_score || 0) +
    Number(assessmentForm.value.practice_result_score || 0) +
    schoolScore.value
  )
})



// 监听学生选择变化，加载评分数据
watch(() => props.selectedStudent, async (newStudent, oldStudent) => {
  // 保存当前学生的评分数据到缓存
  if (oldStudent && oldStudent.student) {
    studentAssessmentCache.value.set(oldStudent.student.s_id, {
      performance_score: assessmentForm.value.performance_score,
      summary_score: assessmentForm.value.summary_score,
      practice_result_score: assessmentForm.value.practice_result_score,
      teachingUnitComment: assessmentForm.value.teachingUnitComment
    })
  }
  
  if (!newStudent) {
    resetForm()
    return
  }
  
  // 检查缓存中是否有该学生的数据
  const cachedData = studentAssessmentCache.value.get(newStudent.student.s_id)
  if (cachedData) {
    // 使用缓存数据
    assessmentForm.value = {
      performance_score: cachedData.performance_score || 0,
      summary_score: cachedData.summary_score || 0,
      practice_result_score: cachedData.practice_result_score || 0,
      teachingUnitComment: cachedData.teachingUnitComment || ''
    }
    return
  }
  
  try {
    // 并行加载学生的评分数据和用户数据
    const [assessmentRes, duserRes] = await Promise.all([
      axios.get(`/assessments/${newStudent.student.s_id}`),
      axios.get(`/duser/${newStudent.student.student_number}`)
    ])
    
    // 初始化表单数据
    const assessmentData = assessmentRes.data && assessmentRes.data.data ? assessmentRes.data.data : {}
    const duserData = duserRes.data && duserRes.data.data ? duserRes.data.data : {}
    
    const formData = {
      performance_score: assessmentData.performance_score || 0,
      summary_score: assessmentData.summary_score || 0,
      practice_result_score: assessmentData.practice_result_score || 0,
      teachingUnitComment: duserData.teachingUnitComment || ''
    }
    
    assessmentForm.value = formData
    
    // 将数据存入缓存
    studentAssessmentCache.value.set(newStudent.student.s_id, formData)
  } catch (error) {
    console.log('暂无评分数据，使用默认值')
    const defaultData = {
      performance_score: 0,
      summary_score: 0,
      practice_result_score: 0,
      teachingUnitComment: ''
    }
    assessmentForm.value = defaultData
    // 将默认数据存入缓存
    studentAssessmentCache.value.set(newStudent.student.s_id, defaultData)
  }
}, { immediate: true })

// 保存评分
const saveAssessment = async () => {
  if (!props.selectedStudent) {
    ElMessage.warning('请选择学生')
    return
  }
  
  if (totalScore.value > 100) {
    ElMessage.warning('总分不能超过100分')
    return
  }
  
  saving.value = true
  
  try {
    // 构造评分数据
    const assessmentPayload = {
      s_id: props.selectedStudent.student.s_id,
      performance_score: Math.round(assessmentForm.value.performance_score),
      summary_score: Math.round(assessmentForm.value.summary_score),
      practice_result_score: Math.round(assessmentForm.value.practice_result_score),
      school_score: schoolScore.value
    }
    
    // 构造教学单位评语数据
    const duserPayload = {
      s_id: props.selectedStudent.student.s_id,
      teachingUnitComment: assessmentForm.value.teachingUnitComment
    }
    
    // 并行提交请求
    const [assessmentRes, duserRes] = await Promise.all([
      axios.put(`/assessments`, assessmentPayload),
      axios.patch(`/duser/${props.selectedStudent.student.student_number}`, duserPayload)
    ])
    
    // 检查响应状态
    const isAssessmentSuccess = assessmentRes.status >= 200 && assessmentRes.status < 300
    const isDuserSuccess = duserRes.status >= 200 && duserRes.status < 300
    
    if (isAssessmentSuccess && isDuserSuccess) {
      ElMessage.success('评分保存成功！')
      
      // 更新缓存中的数据
      if (props.selectedStudent) {
        studentAssessmentCache.value.set(props.selectedStudent.student.s_id, {
          performance_score: assessmentForm.value.performance_score,
          summary_score: assessmentForm.value.summary_score,
          practice_result_score: assessmentForm.value.practice_result_score,
          teachingUnitComment: assessmentForm.value.teachingUnitComment
        })
      }
    } else {
      throw new Error('保存失败')
    }
  } catch (error) {
    console.error('保存失败详情:', error)
    ElMessage.error('保存失败，请重试')
  } finally {
    saving.value = false
  }
}

// 重置表单
const resetForm = () => {
  assessmentForm.value = {
    performance_score: 0,
    summary_score: 0,
    practice_result_score: 0,
    teachingUnitComment: ''
  }
}
</script>

<style scoped>
.assessment-main {
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input-number {
  width: 150px;
}
</style>