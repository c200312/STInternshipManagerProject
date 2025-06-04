<template>
  <div class="score-comment-container">
    <h3 class="title">实习评分与评语</h3>
    <el-form @submit.prevent="handleSubmit" class="score-form">
      <!-- 评分信息 -->
      <h4 class="section-title">评分项（总分范围 0-100 分）</h4>

      <!-- 出勤率评分 -->
      <el-form-item
          label="出勤率评分（0-20 分）"
          required
          :error="fieldErrors.attendance_score"
      >
        <el-input
            v-model.number="assessmentData.attendance_score"
            type="number"
            placeholder="请输入 0-20 的评分"
            min="0"
            max="20"
            :disabled="isReadOnly"
            class="score-input"
        />
      </el-form-item>

      <!-- 任务完成评分 -->
      <el-form-item
          label="任务完成评分（0-60 分）"
          required
          :error="fieldErrors.task_score"
      >
        <el-input
            v-model.number="assessmentData.task_score"
            type="number"
            placeholder="请输入 0-60 的评分"
            min="0"
            max="60"
            :disabled="isReadOnly"
            class="score-input"
        />
      </el-form-item>

      <!-- 职业素养评分 -->
      <el-form-item
          label="职业素养评分（0-20 分）"
          required
          :error="fieldErrors.professionalism_score"
      >
        <el-input
            v-model.number="assessmentData.professionalism_score"
            type="number"
            placeholder="请输入 0-20 的评分"
            min="0"
            max="20"
            :disabled="isReadOnly"
            class="score-input"
        />
      </el-form-item>

      <!-- 评语信息 -->
      <h4 class="section-title">评语项</h4>

      <!-- 新增评语项必填验证 -->
      <el-form-item
          label="实习实践单位评语"
          required
          :error="fieldErrors.practiceComment"
      >
        <el-input
            v-model="assessmentData.practiceComment"
            type="textarea"
            :rows="3"
            placeholder="请输入实习单位综合评语"
            :disabled="isReadOnly"
            class="comment-textarea"
        />
      </el-form-item>

      <el-form-item
          label="校外实习实践内容"
          required
          :error="fieldErrors.practiceContent"
      >
        <el-input
            v-model="assessmentData.practiceContent"
            type="textarea"
            :rows="5"
            placeholder="请详细描述实习实践具体内容"
            :disabled="isReadOnly"
            class="content-textarea"
        />
      </el-form-item>

      <!-- 总分提示 -->
      <div class="total-score-container">
        <div class="total-score">
          当前总分：{{ calculatedTotal }} 分
          <span v-if="totalError" class="error-message">{{ totalError }}</span>
        </div>
      </div>

      <!-- 操作按钮 -->
      <el-form-item class="button-group">
        <el-button
            v-if="!isReadOnly"
            type="primary"
            native-type="submit"
            class="submit-btn"
            :loading="isSubmitting"
        >
          保存评分与评语
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref, onMounted, watch, computed} from 'vue';
import {ElMessage} from 'element-plus';
import axios from '@/utils/request';

const props = defineProps(['userName'])
const emit = defineEmits(['save-success']);

// 数据模型
const assessmentData = ref({
  internship_id: null,
  attendance_score: 0,
  task_score: 0,
  professionalism_score: 0,
  practiceComment: '',
  practiceContent: '',
  user_id: props.userId
});

// 表单验证状态
const isSubmitting = ref(false);
const fieldErrors = ref({
  attendance_score: '',
  task_score: '',
  professionalism_score: '',
  practiceComment: '',
  practiceContent: ''
});
const totalError = ref('');

// 计算总分
const calculatedTotal = computed(() => {
  return (
      Number(assessmentData.value.attendance_score || 0) +
      Number(assessmentData.value.task_score || 0) +
      Number(assessmentData.value.professionalism_score || 0)
  );
});

const isTotalValid = computed(() => {
  return calculatedTotal.value >= 0 && calculatedTotal.value <= 100;
});

// 加载数据
const loadData = async () => {
  try {
    if (!props.userName) {
      console.warn("userName is not provided");
      return;
    }

    const studentRes = await axios.get(`/student/getbyusernumber/${props.userName}`);
    if (studentRes.data.code === '200' && studentRes.data.data) {
      const studentData = studentRes.data.data;
      const [assessmentRes, dUserRes] = await Promise.all([
        axios.get(`/assessments/${studentData.s_id}`),
        axios.get(`/duser/${studentData.s_id}`)
      ]);

      assessmentData.value = {
        internship_id: assessmentData.value.internship_id || null,
        attendance_score: Number(assessmentRes.data.data?.attendance_score || 0),
        task_score: Number(assessmentRes.data.data?.task_score || 0),
        professionalism_score: Number(assessmentRes.data.data?.professionalism_score || 0),
        practiceComment: dUserRes.data.data?.practiceComment || '',
        practiceContent: dUserRes.data.data?.practiceContent || '',
        user_id: studentData.id,
        s_id: studentData.s_id
      };
    }
  } catch (error) {
    console.error('加载数据失败:', error);
    ElMessage.error('数据加载失败: ' + (error.response?.data?.message || error.message));
  }
};

// 表单验证
const validateForm = () => {
  // 重置错误信息
  fieldErrors.value = {
    attendance_score: '',
    task_score: '',
    professionalism_score: '',
    practiceComment: '',
    practiceContent: ''
  };
  totalError.value = '';

  let isValid = true;

  // 验证评分项
  const validateScore = (field, min, max, fieldName) => {
    const value = assessmentData.value[field];
    if (value === '' || value === null || value === undefined) {
      fieldErrors.value[field] = `${fieldName}不能为空`;
      isValid = false;
    } else if (isNaN(value)) {
      fieldErrors.value[field] = `${fieldName}必须为数字`;
      isValid = false;
    } else if (value < min || value > max) {
      fieldErrors.value[field] = `${fieldName}必须在${min}-${max}分之间`;
      isValid = false;
    }
  };

  // 验证评语项
  const validateComment = (field, fieldName) => {
    const value = assessmentData.value[field];
    if (!value || value.trim() === '') {
      fieldErrors.value[field] = `${fieldName}不能为空`;
      isValid = false;
    }
  };

  // 执行验证
  validateScore('attendance_score', 0, 20, '出勤率评分');
  validateScore('task_score', 0, 60, '任务完成评分');
  validateScore('professionalism_score', 0, 20, '职业素养评分');
  validateComment('practiceComment', '实习实践单位评语');
  validateComment('practiceContent', '校外实习实践内容');

  // 验证总分
  if (!isTotalValid.value) {
    totalError.value = '总分必须在0-100分之间';
    isValid = false;
  }

  return isValid;
};

// 保存数据 - 关键修改点在此处
const handleSubmit = async () => {
  if (!validateForm()) {
    ElMessage.warning('请修正表单中的错误');
    return;
  }

  isSubmitting.value = true;

  try {
    // 构造评分数据
    const assessmentPayload = {
      s_id: assessmentData.value.s_id,
      attendance_score: Math.round(assessmentData.value.attendance_score),
      task_score: Math.round(assessmentData.value.task_score),
      professionalism_score: Math.round(assessmentData.value.professionalism_score)
    };

    // 构造评语数据
    const duserPayload = {
      s_id: assessmentData.value.s_id,
      practiceComment: assessmentData.value.practiceComment,
      practiceContent: assessmentData.value.practiceContent
    };

    // 并行提交请求
    const [assessmentRes, duserRes] = await Promise.all([
      axios.put(`/assessments`, assessmentPayload),
      axios.patch(`/duser/${assessmentData.value.s_id}`, duserPayload)
    ]);

    // 使用HTTP状态码检查是否成功（核心修改点）
    const isAssessmentSuccess = assessmentRes.status >= 200 && assessmentRes.status < 300;
    const isDuserSuccess = duserRes.status >= 200 && duserRes.status < 300;

    if (isAssessmentSuccess && isDuserSuccess) {
      ElMessage.success('评分和评语保存成功！');
      emit('save-success', assessmentData.value);
    } else {
      // 提供更详细的错误信息
      const assessmentError = `评分保存失败 (HTTP ${assessmentRes.status})`;
      const duserError = `评语保存失败 (HTTP ${duserRes.status})`;

      throw new Error(`${assessmentError} | ${duserError}`);
    }
  } catch (error) {
    console.error('保存失败详情:', error);

    // 特殊处理：即使被捕获为错误，但状态码在成功范围内（处理异常情况）
    if (error.response) {
      const status = error.response.status;
      if (status >= 200 && status < 300) {
        ElMessage.success('数据已保存！');
        emit('save-success', assessmentData.value);
      } else {
        const message = error.response.data?.message ||
            error.message ||
            '保存失败，请检查网络连接或稍后重试';
        ElMessage.error(`保存失败: ${message}`);
      }
    } else {
      // 网络错误或其他未捕获的错误
      ElMessage.error(`保存失败: ${error.message || '未知错误'}`);
    }
  } finally {
    isSubmitting.value = false;
  }
};

// 监听评分变化自动验证
watch([
  () => assessmentData.value.attendance_score,
  () => assessmentData.value.task_score,
  () => assessmentData.value.professionalism_score,
  () => assessmentData.value.practiceComment,
  () => assessmentData.value.practiceContent
], () => {
  validateForm();
});

onMounted(() => {
  loadData()
})
</script>

<style scoped>

</style>

