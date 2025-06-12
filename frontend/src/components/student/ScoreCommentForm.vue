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
            v-model.number="localAssessmentData.attendance_score"
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
            v-model.number="localAssessmentData.task_score"
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
            v-model.number="localAssessmentData.professionalism_score"
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
            v-model="localAssessmentData.practiceComment"
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
            v-model="localAssessmentData.practiceContent"
            type="textarea"
            :rows="5"
            placeholder="请详细描述实习实践具体内容"
            :disabled="isReadOnly"
            class="content-textarea"
        />
      </el-form-item>

      <!-- 校外实践单位评分提示 -->
      <div class="total-score-container">
        <div class="total-score">
          校外实践单位评分：{{ calculatedTotal }} 分
          <span v-if="totalError" class="error-message">{{ totalError }}</span>
        </div>
      </div>

      <!-- 添加文件上传组件 -->
      <h4 class="section-title">实习单位鉴定材料</h4>
      <div class="file-upload-container">
        <el-upload
          class="upload-demo"
          :action="`/api/files/upload/${localAssessmentData.s_id}`"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-success="handleSuccess"
          :on-error="handleError"
          :before-upload="beforeUpload"
          :file-list="fileList"
          :limit="5"
          multiple
          accept=".jpg,.jpeg,.png,.gif,.pdf"
        >
          <template #trigger>
            <el-button type="primary">选择文件</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip">
              支持的文件类型：jpg、jpeg、png、gif、pdf，单个文件大小不超过10MB
            </div>
          </template>
        </el-upload>

        <!-- 文件预览对话框 -->
        <el-dialog v-model="previewVisible" title="文件预览" width="50%">
          <div v-if="previewUrl" class="preview-container">
            <img v-if="isImage" :src="previewUrl" class="preview-image" />
            <iframe v-else :src="previewUrl" class="preview-iframe"></iframe>
          </div>
        </el-dialog>
      </div>

      <!-- 显示已上传文件列表 -->
      <el-form-item v-if="fileList.length > 0">
        <el-table :data="fileList" style="width: 100%">
          <el-table-column prop="name" label="文件名" />
          <el-table-column prop="size" label="大小" width="120">
            <template #default="scope">
              {{ formatFileSize(scope.row.size) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" link @click="handleDownload(scope.row)" :disabled="isReadOnly">
                下载
              </el-button>
              <el-button type="danger" link @click="handleDelete(scope.row)" :disabled="isReadOnly">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>

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

const props = defineProps({
  assessmentData: {
    type: Object,
    required: true
  }
});

const emit = defineEmits(['save-success']);

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

// 创建本地响应式数据
const localAssessmentData = ref({
  s_id: props.assessmentData.s_id,
  attendance_score: props.assessmentData.attendance_score || 0,
  task_score: props.assessmentData.task_score || 0,
  professionalism_score: props.assessmentData.professionalism_score || 0,
  practiceComment: props.assessmentData.practiceComment || '',
  practiceContent: props.assessmentData.practiceContent || ''
});

// 监听props变化
watch(() => props.assessmentData, (newVal) => {
  localAssessmentData.value = {
    s_id: newVal.s_id,
    attendance_score: newVal.attendance_score || 0,
    task_score: newVal.task_score || 0,
    professionalism_score: newVal.professionalism_score || 0,
    practiceComment: newVal.practiceComment || '',
    practiceContent: newVal.practiceContent || ''
  };
}, { deep: true });

// 计算校外实践单位评分
const calculatedTotal = computed(() => {
  const total = (
      Number(localAssessmentData.value.attendance_score || 0) +
      Number(localAssessmentData.value.task_score || 0) +
      Number(localAssessmentData.value.professionalism_score || 0)
  );
  return Math.round(total / 2 * 100) / 100;
});

const isTotalValid = computed(() => {
  return calculatedTotal.value >= 0 && calculatedTotal.value <= 50; // 最大值改为50
});

// 文件列表
const fileList = ref([])
const previewVisible = ref(false)
const previewUrl = ref('')
const isImage = ref(false)

// 格式化文件大小
const formatFileSize = (size) => {
  if (size < 1024) {
    return size + 'B'
  } else if (size < 1024 * 1024) {
    return (size / 1024).toFixed(2) + 'KB'
  } else {
    return (size / (1024 * 1024)).toFixed(2) + 'MB'
  }
}

// 文件上传前的验证
const beforeUpload = (file) => {
  const allowedTypes = [
    'image/jpeg',
    'image/png',
    'image/gif',
    'application/pdf'
  ]
  
  const isAllowedType = allowedTypes.includes(file.type)
  const isLt10M = file.size / 1024 / 1024 < 10

  if (!isAllowedType) {
    ElMessage.error('只支持图片（jpg、jpeg、png、gif）和PDF格式！')
    return false
  }
  if (!isLt10M) {
    ElMessage.error('文件大小不能超过 10MB！')
    return false
  }
  return true
}

// 文件上传成功处理
const handleSuccess = (response, file) => {
  if (response.code === "200") {
    ElMessage.success('文件上传成功')
    loadFiles() // 重新加载文件列表
  } else {
    ElMessage.error(response.msg || '文件上传失败')
  }
}

// 文件上传失败处理
const handleError = (error) => {
  console.error('文件上传失败:', error)
  ElMessage.error('文件上传失败，请重试')
}

// 文件预览处理
const handlePreview = async (file) => {
  try {
    const response = await axios.get(`/files/download/${localAssessmentData.value.s_id}/${file.name}`, {
      responseType: 'blob'
    })
    
    const url = window.URL.createObjectURL(new Blob([response.data]))
    previewUrl.value = url
    isImage.value = file.type.startsWith('image/')
    previewVisible.value = true
  } catch (error) {
    console.error('文件预览失败:', error)
    ElMessage.error('文件预览失败')
  }
}

// 文件删除处理
const handleRemove = async (file) => {
  try {
    const response = await axios.delete(`/files/delete/${localAssessmentData.value.s_id}/${file.name}`)
    if (response.data.code === "200") {
      ElMessage.success('文件删除成功')
      loadFiles() // 重新加载文件列表
    } else {
      ElMessage.error(response.data.msg || '文件删除失败')
    }
  } catch (error) {
    console.error('删除文件失败:', error)
    ElMessage.error('删除文件失败')
  }
}

// 加载文件列表
const loadFiles = async () => {
  try {
    const response = await axios.get(`/files/list/${localAssessmentData.value.s_id}`)
    if (response.data.code === "200") {
      fileList.value = response.data.data
    } else {
      ElMessage.error(response.data.msg || '获取文件列表失败')
    }
  } catch (error) {
    console.error('获取文件列表失败:', error)
    ElMessage.error('获取文件列表失败')
  }
}

// 下载文件
const handleDownload = async (file) => {
  try {
    const response = await axios.get(`/files/download/${localAssessmentData.value.s_id}/${file.name}`, { responseType: 'blob' });
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', file.name);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  } catch (error) {
    console.error('下载文件失败:', error);
    ElMessage.error('下载文件失败');
  }
};

// 删除文件
const handleDelete = async (file) => {
  try {
    const response = await axios.delete(`/files/delete/${localAssessmentData.value.s_id}/${file.name}`);
    if (response.data.code === "200") {
      ElMessage.success('文件删除成功');
      await loadFiles(); // 重新加载文件列表
    } else {
      ElMessage.error(response.data.msg || '文件删除失败');
    }
  } catch (error) {
    console.error('删除文件失败:', error);
    ElMessage.error('删除文件失败');
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
    const value = localAssessmentData.value[field];
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
    const value = localAssessmentData.value[field];
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

  // 验证校外实践单位评分
  if (!isTotalValid.value) {
    totalError.value = '校外实践单位评分必须在0-50分之间';
    isValid = false;
  }

  return isValid;
};

// 保存数据
const handleSubmit = async () => {
  if (!validateForm()) {
    ElMessage.warning('请修正表单中的错误');
    return;
  }

  isSubmitting.value = true;

  try {
    // 构造评分数据
    const assessmentPayload = {
      s_id: localAssessmentData.value.s_id,
      internship_id: 1,
      t_id: 1,
      attendance_score: Math.round(localAssessmentData.value.attendance_score),
      task_score: Math.round(localAssessmentData.value.task_score),
      professionalism_score: Math.round(localAssessmentData.value.professionalism_score),
      company_score: calculatedTotal.value
    };

    // 构造评语数据
    const duserPayload = {
      practiceComment: localAssessmentData.value.practiceComment,
      practiceContent: localAssessmentData.value.practiceContent
    };

    // 并行提交请求
    const [assessmentRes, duserRes] = await Promise.all([
      axios.put(`/assessments`, assessmentPayload),
      axios.patch(`/duser/${localAssessmentData.value.s_id}`, duserPayload)
    ]);

    // 检查响应状态
    const assessmentSuccess = assessmentRes.data && (assessmentRes.data.code === 200 || assessmentRes.data.code === "200");
    const duserSuccess = duserRes.data && (duserRes.data.code === 200 || duserRes.data.code === "200");

    if (assessmentSuccess && duserSuccess) {
      ElMessage.success('保存成功！');
      emit('save-success');
      // 重新加载数据以确保显示最新状态
      await loadAssessmentData();
    } else {
      throw new Error('保存失败');
    }
  } catch (error) {
    console.error('保存失败:', error);
    ElMessage.error('保存失败，请重试');
  } finally {
    isSubmitting.value = false;
  }
};

// 在组件挂载时加载数据
onMounted(async () => {
  await loadAssessmentData();
  await loadFiles();
});

// 加载评分和评语数据
const loadAssessmentData = async () => {
  try {
    // 并行加载评分和评语数据
    const [assessmentRes, duserRes] = await Promise.all([
      axios.get(`/assessments/${localAssessmentData.value.s_id}`),
      axios.get(`/duser/${localAssessmentData.value.s_id}`)
    ]);

    // 更新评分数据
    if (assessmentRes.data && assessmentRes.data.data) {
      const assessmentData = assessmentRes.data.data;
      localAssessmentData.value.attendance_score = assessmentData.attendance_score || 0;
      localAssessmentData.value.task_score = assessmentData.task_score || 0;
      localAssessmentData.value.professionalism_score = assessmentData.professionalism_score || 0;
    }

    // 更新评语数据
    if (duserRes.data && duserRes.data.data) {
      const duserData = duserRes.data.data;
      localAssessmentData.value.practiceComment = duserData.practiceComment || '';
      localAssessmentData.value.practiceContent = duserData.practiceContent || '';
    }
  } catch (error) {
    console.error('加载数据失败:', error);
    ElMessage.error('加载数据失败');
  }
};

// 监听评分变化自动验证
watch(() => localAssessmentData.value.attendance_score,
  () => localAssessmentData.value.task_score,
  () => localAssessmentData.value.professionalism_score,
  () => localAssessmentData.value.practiceComment,
  () => localAssessmentData.value.practiceContent,
  () => validateForm()
);
</script>

<style scoped>
.score-form {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.button-group {
  margin-top: 20px;
  text-align: center;
}

.upload-demo {
  width: 100%;
}

.el-upload__tip {
  color: #666;
  font-size: 12px;
  margin-top: 8px;
}

.file-upload-container {
  margin: 20px 0;
}

.preview-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 300px;
}

.preview-image {
  max-width: 100%;
  max-height: 500px;
  object-fit: contain;
}

.preview-iframe {
  width: 100%;
  height: 500px;
  border: none;
}
</style>

