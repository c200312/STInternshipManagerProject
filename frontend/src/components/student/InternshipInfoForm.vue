<template>
  <h3>企业信息</h3>
  <el-form @submit.prevent="handleSave">
    <!-- Status Display -->
    <el-form-item label="当前状态">
      <el-tag :type="statusTagType">{{ statusText }}</el-tag>
    </el-form-item>

    <!-- 审核信息显示 -->
    <div v-if="internshipData.approval_status >= 2 && internshipData.review_comments" class="review-info">
      <el-divider content-position="left">审核信息</el-divider>
      <el-form-item label="审核人">
        <span>{{ internshipData.reviewer_name || '未知' }}</span>
      </el-form-item>
      <el-form-item label="审核时间">
        <span>{{ internshipData.review_time ? new Date(internshipData.review_time).toLocaleString() : '未知' }}</span>
      </el-form-item>
      <el-form-item label="审核意见">
        <el-input
            v-model="internshipData.review_comments"
            type="textarea"
            :rows="3"
            readonly
            placeholder="暂无审核意见"
        />
      </el-form-item>
    </div>

    <!-- 基础信息 -->
    <el-form-item label="学生学号" required>
      <el-input v-model="internshipData.s_id" disabled placeholder="学生学号"/>
    </el-form-item>

    <el-form-item label="实习单位名称" required>
      <el-input
          v-model="internshipData.company_name"
          placeholder="请输入实习单位全称"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="是否校外实践基地">
      <el-switch
          v-model="internshipData.is_practice_base"
          active-text="是"
          inactive-text="否"
          active-color="#409eff"
          inactive-color="#c0c4cc"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="所属实践基地名称">
      <el-input
          v-model="internshipData.practice_base_name"
          placeholder="请输入实践基地名称"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <!-- 企业资质 -->
    <el-form-item label="统一社会信用代码">
      <el-input
          v-model="internshipData.credit_code"
          placeholder="请输入18位统一社会信用代码"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="实习地区">
      <el-input
          v-model="internshipData.practice_region"
          placeholder="例：北京市-朝阳区"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <!-- 时间信息 -->
    <el-form-item label="实习时间范围" required>
      <el-date-picker
          v-model="internshipData.start_date"
          type="date"
          placeholder="开始日期"
          style="width: 48%"
          @change="calculateActualDays"
          :disabled="isReadOnly"
      />
      <span class="date-separator">-</span>
      <el-date-picker
          v-model="internshipData.end_date"
          type="date"
          placeholder="结束日期"
          style="width: 48%"
          @change="calculateActualDays"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="实际实习天数">
      <el-input
          v-model.number="internshipData.actual_days"
          type="number"
          placeholder="自动计算"
          min="0"
          disabled
      />
    </el-form-item>

    <!-- 岗位信息 -->
    <el-form-item label="实习岗位">
      <el-input
          v-model="internshipData.position"
          placeholder="例：软件测试实习生"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="实习方式">
      <el-select
          v-model="internshipData.internship_mode"
          placeholder="请选择实习方式"
          :disabled="isReadOnly"
      >
        <el-option label="集中实习" value="集中实习"/>
        <el-option label="分散实习" value="分散实习"/>
        <el-option label="跟岗实习" value="跟岗实习"/>
      </el-select>
    </el-form-item>

    <el-form-item label="实习类型">
      <el-select
          v-model="internshipData.internship_type"
          placeholder="请选择实习类型"
          :disabled="isReadOnly"
      >
        <el-option label="专业实习" value="专业实习"/>
        <el-option label="毕业实习" value="毕业实习"/>
        <el-option label="认知实习" value="认知实习"/>
      </el-select>
    </el-form-item>

    <!-- 企业指导 -->
    <el-form-item label="企业指导人员姓名">
      <el-input
          v-model="internshipData.company_advisor_name"
          placeholder="请输入指导人员姓名"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="企业指导人员职务">
      <el-input
          v-model="internshipData.company_advisor_position"
          placeholder="例：技术主管"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <!-- 联系信息 -->
    <el-form-item label="实习单位地址">
      <el-input
          v-model="internshipData.address"
          type="textarea"
          rows=2
          placeholder="请输入详细地址"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="单位联系人">
      <el-input
          v-model="internshipData.contact_person"
          placeholder="请输入联系人姓名"
          :disabled="isReadOnly"
      />
    </el-form-item>

    <el-form-item label="联系电话">
      <el-input
          v-model="internshipData.company_phone"
          placeholder="请输入联系电话"
          maxlength="13"
          show-word-limit
          :disabled="isReadOnly"
      />
    </el-form-item>

    <div class="button-group">
      <el-button
          type="primary"
          @click="handleSave"
          :disabled="isReadOnly"
      >
        保存
      </el-button>
      <el-button
          type="success"
          @click="handleSubmit"
          :disabled="isSubmitted"
      >
        提交
      </el-button>
      <el-button
          type="warning"
          @click="handleWithdraw"
          :disabled="!canWithdraw"
      >
        撤回
      </el-button>
      <el-button
          type="info"
          @click="applyForModification"
          :disabled="!canApplyForModification"
      >
        申请修改
      </el-button>
    </div>
  </el-form>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import axios from '@/utils/request';

// 定义组件属性和事件
const props = defineProps(['s_id'])
const emit = defineEmits(['submit']);

// 缓存节假日数据，避免重复请求
const holidayCache = ref({});

// 状态映射配置
const STATUS_CONFIG = {
  approval: {
    0: { text: '草稿状态（可修改）', type: 'info' },
    1: { text: '已提交（等待审核）', type: 'warning' },
    2: { text: '已审核通过', type: 'success' },
    3: { text: '已驳回（可修改）', type: 'danger' },
    5: { text: '已撤回（可修改）', type: 'info' }
  },
  modification: {
    1: { text: ' - 修改申请处理中', type: 'warning' },
    2: { text: ' - 修改申请已通过', type: 'success' },
    3: { text: ' - 修改申请已拒绝', type: 'danger' }
  }
};

// 初始化实习数据
const createInitialData = () => ({
  internship_id: null,
  company_name: '',
  practice_base_name: '',
  is_practice_base: false,
  base_notes: '',
  credit_code: '',
  practice_region: '',
  approval_status: 0,
  review_comments: '',
  reviewer_name: '',
  review_time: null,
  start_date: null,
  end_date: null,
  actual_days: null,
  company_count: 1,
  position: '',
  salary: null,
  internship_mode: '',
  internship_type: '',
  company_advisor_name: '',
  company_advisor_position: '',
  address: '',
  contact_person: '',
  company_phone: '',
  s_id: props.s_id,
  modification_reason: '',
  modification_status: 0
});

const internshipData = ref(createInitialData());

// 计算属性
const isReadOnly = computed(() => {
  const { approval_status, modification_status } = internshipData.value;
  return [1, 2].includes(approval_status) || modification_status === 1;
});

const isSubmitted = computed(() => isReadOnly.value);

const canApplyForModification = computed(() => {
  const { approval_status, modification_status } = internshipData.value;
  return approval_status === 2 && modification_status === 0;
});

const canWithdraw = computed(() => internshipData.value.approval_status === 1);

const statusText = computed(() => {
  const { approval_status, modification_status } = internshipData.value;
  const approvalConfig = STATUS_CONFIG.approval[approval_status];
  const modificationConfig = STATUS_CONFIG.modification[modification_status];
  
  let text = approvalConfig?.text || '未知状态';
  if (modificationConfig) {
    text += modificationConfig.text;
  }
  
  return text;
});

const statusTagType = computed(() => {
  const { approval_status, modification_status } = internshipData.value;
  const modificationConfig = STATUS_CONFIG.modification[modification_status];
  
  if (modificationConfig) {
    return modificationConfig.type;
  }
  
  return STATUS_CONFIG.approval[approval_status]?.type || '';
});

const loadInternshipData = async () => {
  try {
    const res = await axios.get(`/internship/${props.s_id}`);
    
    if (!res.data.data[0]) {
      // 如果没有数据，使用初始化数据
      internshipData.value = createInitialData();
      return;
    }

    // 合并后端数据和默认值
    internshipData.value = {
      ...createInitialData(),
      ...res.data.data[0],
      approval_status: res.data.data[0].approval_status ?? 0,
      modification_status: res.data.data[0].modification_status ?? 0,
      modification_reason: res.data.data[0].modification_reason ?? ''
    };

    // 计算实习天数
    await calculateActualDays();
    
    // 清除可能的本地缓存
    localStorage.removeItem(`internship_${props.s_id}`);
  } catch (error) {
    console.error('加载实习信息失败:', error);
    ElMessage.error('加载实习信息失败');
  }
};

// 通用API响应处理
const handleApiResponse = (response, successMsg, errorMsg = '操作失败') => {
  if (response.data.code === '200') {
    if (successMsg) ElMessage.success(successMsg);
    return true;
  } else {
    ElMessage.error(response.data.msg || errorMsg);
    return false;
  }
};

// 检查日期是否为工作日
const isWorkingDay = async (date) => {
  const dateStr = new Date(date).toISOString().split('T')[0];

  // 优先使用缓存数据
  if (holidayCache.value[dateStr] !== undefined) {
    return holidayCache.value[dateStr] === 0;
  }

  try {
    const response = await fetch(`https://timor.tech/api/holiday/info/${dateStr}`);
    const data = await response.json();
    holidayCache.value[dateStr] = data.type.type;
    return data.type.type === 0;
  } catch (error) {
    console.error('获取节假日信息失败:', error);
    // 默认处理：周六日为非工作日
    const day = new Date(date).getDay();
    return day !== 0 && day !== 6;
  }
};

// 计算实际实习天数，跳过法定节假日，加上调休的日子
const calculateActualDays = async () => {
  if (!internshipData.value.start_date || !internshipData.value.end_date) {
    return;
  }

  const start = new Date(internshipData.value.start_date);
  const end = new Date(internshipData.value.end_date);

  // 显示加载状态
  internshipData.value.actual_days = '计算中...';

  let days = 0;
  let currentDate = new Date(start);

  // 按顺序检查每一天，避免并发请求过多
  while (currentDate <= end) {
    const isWorkday = await isWorkingDay(currentDate);
    if (isWorkday) {
      days++;
    }
    currentDate.setDate(currentDate.getDate() + 1);
  }

  internshipData.value.actual_days = days;
};

const handleSave = async () => {
  try {
    // 保存时设置为草稿状态（如果是可编辑状态）
    const editableStatuses = [undefined, 0, 3, 5];
    if (editableStatuses.includes(internshipData.value.approval_status)) {
      internshipData.value.approval_status = 0;
    }

    // 始终使用PUT方法，后端会验证并新建数据行
    const response = await axios.put('/internship', internshipData.value);

    // 如果是新建的数据，更新internship_id
    if (!internshipData.value.internship_id && response.data.data?.internship_id) {
      internshipData.value.internship_id = response.data.data.internship_id;
    }

    ElMessage.success('保存成功');
    emit('submit', internshipData.value);
  } catch (error) {
    console.error('保存失败:', error);
    ElMessage.error('保存失败');
  }
};

const handleSubmit = async () => {
  try {
    await ElMessageBox.confirm(
        '确认提交实习信息吗？提交后将不能修改，等待教师审核。',
        '提示',
        { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }
    );

    // 先保存数据
    await handleSave();

    // 调用提交审核接口
    const response = await axios.post(`/internship/${internshipData.value.s_id}/submit`);
    
    if (handleApiResponse(response, '提交成功，等待教师审核', '提交失败')) {
      internshipData.value.approval_status = 1;
      emit('submit', internshipData.value);
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error);
      ElMessage.error('提交失败');
    }
  }
};

const handleWithdraw = async () => {
  try {
    await ElMessageBox.confirm(
        '确认撤回实习信息吗？撤回后可以重新修改和提交。',
        '提示',
        { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }
    );

    const response = await axios.post(`/internship/${internshipData.value.s_id}/withdraw`);
    
    if (handleApiResponse(response, '撤回成功，可以重新修改和提交', '撤回失败')) {
      internshipData.value.approval_status = 5;
      emit('submit', internshipData.value);
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('撤回失败:', error);
      ElMessage.error('撤回失败');
    }
  }
};

const applyForModification = async () => {
  try {
    const { value: reason } = await ElMessageBox.prompt(
        '请输入申请修改的原因',
        '申请修改',
        {
          confirmButtonText: '提交申请',
          cancelButtonText: '取消',
          inputPlaceholder: '请输入修改原因',
          inputValidator: (value) => value ? true : '请输入修改原因'
        }
    );

    if (reason) {
      internshipData.value.modification_reason = reason;
      internshipData.value.modification_status = 1;

      const response = await axios.put(
        `/internship/${internshipData.value.internship_id}/apply-modification`,
        { modification_reason: reason }
      );

      if (handleApiResponse(response, '修改申请已提交，等待教师审核', '修改申请提交失败')) {
        emit('submit', internshipData.value);
        await loadInternshipData();
      }
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交修改申请失败:', error);
      ElMessage.error('提交修改申请失败');
    }
  }
};

onMounted(() => {
  loadInternshipData();
});
</script>

<style scoped>
.button-group {
  margin-top: 20px;
}
.button-group .el-button {
  margin-right: 10px;
}
.date-separator {
  display: inline-block;
  width: 4%;
  text-align: center;
}
.review-info {
  margin: 20px 0;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 6px;
  border-left: 4px solid #409eff;
}
.review-info .el-form-item {
  margin-bottom: 10px;
}
.review-info .el-divider {
  margin: 0 0 15px 0;
}
</style>