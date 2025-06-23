<template>
  <h3>校外实习实践计划</h3>
  <el-form
      @submit.prevent="handleSubmit"
      :model="planData"
      ref="planFormRef"
      :rules="rules"
      class="form-container"
  >
    <table class="form-table">
      <tbody>
      <!-- 实习目标和期望 -->
      <tr>
        <td class="form-label">
          <label>实习目标和期望：</label>
        </td>
        <td class="form-input">
          <el-form-item prop="goals">
            <el-input
                v-model="planData.goals"
                type="textarea"
                :rows="4"
                placeholder="请详细描述您的实习目标和期望，包括希望获得的技能、经验和成长目标"
                maxlength="500"
                show-word-limit
            />
          </el-form-item>
        </td>
      </tr>

      <!-- 岗位职责 -->
      <tr>
        <td class="form-label">
          <label>岗位职责：</label>
        </td>
        <td class="form-input">
          <el-form-item prop="jobResponsibilities">
            <el-input
                v-model="planData.jobResponsibilities"
                type="textarea"
                :rows="4"
                placeholder="请描述您在实习岗位上的主要职责和工作内容"
                maxlength="500"
                show-word-limit
            />
          </el-form-item>
        </td>
      </tr>

      <!-- 实习内容与任务 -->
      <tr>
        <td class="form-label">
          <label>实习内容与任务：</label>
        </td>
        <td class="form-input">
          <el-form-item prop="task">
            <el-input
                v-model="planData.task"
                type="textarea"
                :rows="4"
                placeholder="请详细说明实习期间将要完成的具体任务和项目内容"
                maxlength="500"
                show-word-limit
            />
          </el-form-item>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 提交按钮 -->
    <div class="button-group">
      <el-button type="primary" native-type="submit" :loading="loading">保存实习计划</el-button>
      <el-button @click="resetForm">重置</el-button>
    </div>
  </el-form>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '../../utils/request';

const props = defineProps(['username']);
const emit = defineEmits(['submit']);

const planFormRef = ref();
const loading = ref(false);

// 表单数据
const planData = reactive({
  goals: '',
  jobResponsibilities: '',
  task: ''
});

// 表单验证规则
const rules = {
  goals: [
    { required: true, message: '请填写实习目标和期望', trigger: 'blur' },
    { min: 10, message: '实习目标和期望至少需要10个字符', trigger: 'blur' }
  ],
  jobResponsibilities: [
    { required: true, message: '请填写岗位职责', trigger: 'blur' },
    { min: 10, message: '岗位职责至少需要10个字符', trigger: 'blur' }
  ],
  task: [
    { required: true, message: '请填写实习内容与任务', trigger: 'blur' },
    { min: 10, message: '实习内容与任务至少需要10个字符', trigger: 'blur' }
  ]
};

// 加载现有的实习计划数据
const loadPlanData = async () => {
  if (!props.username) return;

  try {
    const response = await axios.get(`/duser/${props.username}`);
    if ((response.data.code === "200" || response.data.code === 200) &&
        response.data.data &&
        response.data.data.plan &&
        response.data.data.plan.length > 0) {
      const plan = response.data.data.plan[0];
      planData.goals = plan.goals || '';
      planData.jobResponsibilities = plan.jobResponsibilities || '';
      planData.task = plan.task || '';
    }
  } catch (error) {
    console.error('加载实习计划数据失败:', error);
    ElMessage.warning('加载现有实习计划数据失败，请检查网络连接');
  }
};

// 提交表单
const handleSubmit = async () => {
  if (!planFormRef.value) return;

  try {
    // 表单验证
    await planFormRef.value.validate();

    loading.value = true;

    // 构造提交数据
    const submitData = {
      plan: [{
        goals: planData.goals,
        jobResponsibilities: planData.jobResponsibilities,
        task: planData.task
      }]
    };

    // 提交到后端
    const response = await axios.patch(`/duser/${props.username}`, submitData);

    if (response.data.code === "200" || response.data.code === 200) {
      ElMessage.success('实习计划保存成功！');
      emit('submit', planData);
    } else {
      ElMessage.error(response.data.msg || '保存失败，请重试');
    }
  } catch (error) {
    console.error('提交实习计划失败:', error);
    if (error.message) {
      ElMessage.error('表单验证失败，请检查输入内容');
    } else {
      ElMessage.error('保存失败，请检查网络连接后重试');
    }
  } finally {
    loading.value = false;
  }
};

// 重置表单
const resetForm = () => {
  if (planFormRef.value) {
    planFormRef.value.resetFields();
  }
};

// 组件挂载时加载数据
onMounted(() => {
  loadPlanData();
});
</script>

<style scoped>
.form-container {
  max-width: 1200px;
  margin: 0;
  padding: 20px;
}

.form-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.form-table td {
  padding: 10px;
  vertical-align: top;
}

.form-label {
  width: 300px;
  text-align: right;
  padding-right: 20px;
  font-weight: 500;
  white-space: nowrap;
}

.form-input {
  width: 100%;
}

.el-form-item {
  margin-bottom: 0;
}

.el-textarea {
  width: 100%;
}


.button-group {
  text-align: center;
  margin-top: 20px;
}

.el-button {
  margin-right: 15px;
  padding: 10px 25px;
}
</style>