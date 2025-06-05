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
            <el-input-number
                v-model="assessmentForm.school_score"
                :min="0"
                :max="25"
                placeholder="0-50分"
            />
            <span style="margin-left: 10px; color: #666;">（满分50分）</span>
          </el-form-item>
          
          <el-form-item label="总分：">
            <span style="font-size: 18px; font-weight: bold; color: #409eff;">
              {{ totalScore }} / 100
            </span>
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

const assessmentForm = ref({
  performance_score: 0,
  summary_score: 0,
  practice_result_score: 0,
  school_score: 0,
  teachingUnitComment: ''
})

// 计算总分
const totalScore = computed(() => {
  return (
    Number(assessmentForm.value.performance_score || 0) +
    Number(assessmentForm.value.summary_score || 0) +
    Number(assessmentForm.value.practice_result_score || 0) +
    Number(assessmentForm.value.school_score || 0)
  )
})



// 监听学生选择变化，加载评分数据
watch(() => props.selectedStudent, async (newStudent) => {
  if (!newStudent) {
    resetForm()
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
    
    assessmentForm.value = {
      performance_score: assessmentData.performance_score || 0,
      summary_score: assessmentData.summary_score || 0,
      practice_result_score: assessmentData.practice_result_score || 0,
      school_score: assessmentData.school_score || 0,
      teachingUnitComment: duserData.teachingUnitComment || ''
    }
  } catch (error) {
    console.log('暂无评分数据，使用默认值')
    resetForm()
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
      school_score: Math.round(assessmentForm.value.school_score)
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
    school_score: 0,
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