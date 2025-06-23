<template>
  <h3>校外实习实践计划</h3>
  <div class="score-form">
    <table class="form-table">
      <tbody>
      <!-- 实习目标和期望 -->
      <tr>
        <td class="form-label">实习目标和期望：</td>
        <td class="form-input">
          <el-input
              v-model="planData.goals"
              type="textarea"
              :rows="4"
              placeholder="请详细描述您的实习目标和期望，包括希望获得的技能、经验和成长目标"
              maxlength="500"
              show-word-limit
          />
        </td>
      </tr>

      <!-- 岗位职责 -->
      <tr>
        <td class="form-label">岗位职责：</td>
        <td class="form-input">
          <el-input
              v-model="planData.jobResponsibilities"
              type="textarea"
              :rows="4"
              placeholder="请描述您在实习岗位上的主要职责和工作内容"
              maxlength="500"
              show-word-limit
          />
        </td>
      </tr>

      <!-- 实习内容与任务 -->
      <tr>
        <td class="form-label">实习内容与任务：</td>
        <td class="form-input">
          <el-input
              v-model="planData.task"
              type="textarea"
              :rows="4"
              placeholder="请详细说明实习期间将要完成的具体任务和项目内容"
              maxlength="500"
              show-word-limit
          />
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 提交按钮 -->
    <div class="button-group">
      <el-button type="primary" native-type="submit" :loading="loading">保存实习计划</el-button>
      <el-button @click="resetForm">重置</el-button>
    </div>
  </div>
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

// 提交表单
const handleSubmit = async () => {
  // 验证逻辑保持不变
  if (!planData.goals || planData.goals.length < 10) {
    ElMessage.error('实习目标和期望至少需要10个字符');
    return;
  }
  if (!planData.jobResponsibilities || planData.jobResponsibilities.length < 10) {
    ElMessage.error('岗位职责至少需要10个字符');
    return;
  }
  if (!planData.task || planData.task.length < 10) {
    ElMessage.error('实习内容与任务至少需要10个字符');
    return;
  }

  loading.value = true;

  try {
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
    ElMessage.error('保存失败，请检查网络连接后重试');
  } finally {
    loading.value = false;
  }
};

// 重置表单
const resetForm = () => {
  planData.goals = '';
  planData.jobResponsibilities = '';
  planData.task = '';
};

// 组件挂载时加载数据
onMounted(() => {
  loadPlanData();
});

// 加载现有的实习计划数据（保持不变）
const loadPlanData = async () => {
  if (!props.username) return;

  try {
    const response = await axios.get(`/duser/${props.username}`);
    if ((response.data.code === "200" || response.data.code === 200) && response.data.data && response.data.data.plan && response.data.data.plan.length > 0) {
      const plan = response.data.data.plan[0]; // 获取第一个计划
      planData.goals = plan.goals || '';
      planData.jobResponsibilities = plan.jobResponsibilities || '';
      planData.task = plan.task || '';
    }
  } catch (error) {
    console.error('加载实习计划数据失败:', error);
    ElMessage.warning('加载现有实习计划数据失败，请检查网络连接');
  }
};
</script>

<style scoped>
.score-form {
  max-width: 1200px;
  margin: 0;
  padding: 20px;
}

.button-group {
  margin-top: 20px;
  text-align: center;
}


/* 新增表格布局样式 */
.form-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.form-table td {
  padding: 10px;
}

.form-label {
  width: 300px; /* 增加标签列宽度为300px */
  text-align: right;
  padding-right: 20px;
  vertical-align: top;
  font-weight: 500;
  white-space: nowrap; /* 禁止文本换行 */
  overflow: visible; /* 允许文本溢出单元格 */
}

.form-input {
  width: 100%;
  vertical-align: top;
}

.el-textarea__inner {
  width: 100%;
}
</style>