<template>
  <h3>企业信息</h3>
  <el-form @submit.prevent="handleSave">
    <!-- Status Display -->
    <el-form-item label="当前状态">
      <el-tag :type="statusTagType">{{ statusText }}</el-tag>
    </el-form-item>

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
import { ref, onMounted, computed, watch } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import axios from '@/utils/request';

// 定义组件属性
const props = defineProps(['s_id'])
// 定义组件事件
const emit = defineEmits(['submit']);

// 缓存节假日数据，避免重复请求
const holidayCache = ref({});
// 公司信息响应式变量
const internshipData = ref({
  internship_id: null,
  company_name: '',
  practice_base_name: '',
  is_practice_base: false,
  base_notes: '',
  credit_code: '',
  practice_region: '',
  approval_status: 0, // 0-未审核 1-通过 2-驳回
  start_date: null,
  end_date: null,
  actual_days: null,
  company_count: 1, // 默认无变更
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
  status: 0, // 0-保存状态 1-已提交 2-已审核通过 3-已驳回
  modification_reason: '', // 申请修改原因
  modification_status: 0, // 修改申请状态: 0-无申请 1-申请中 2-申请通过 3-申请拒绝
});

// 从localStorage加载状态
const loadStateFromStorage = () => {
  try {
    const savedState = localStorage.getItem(`internship_${props.s_id}`);
    if (savedState) {
      return JSON.parse(savedState);
    }
    return null;
  } catch (error) {
    console.error('Failed to load state from localStorage:', error);
    return null;
  }
};

// 保存状态到localStorage
const saveStateToStorage = () => {
  try {
    // 只保存需要持久化的状态
    const stateToSave = {
      status: internshipData.value.status,
      modification_status: internshipData.value.modification_status,
      modification_reason: internshipData.value.modification_reason,
    };

    localStorage.setItem(`internship_${props.s_id}`, JSON.stringify(stateToSave));
  } catch (error) {
    console.error('Failed to save state to localStorage:', error);
  }
};

// 计算属性
const isReadOnly = computed(() => {
  // 已提交但未审核、已通过审核、修改申请处理中时不可编辑
  return internshipData.value.status === 1 ||
      internshipData.value.status === 2 ||
      internshipData.value.modification_status === 1;
});

const isSubmitted = computed(() => {
  // 已提交、已通过审核、修改申请处理中时不可再次提交
  return internshipData.value.status === 1 ||
      internshipData.value.status === 2 ||
      internshipData.value.modification_status === 1;
});

const canApplyForModification = computed(() => {
  // 只有在已审核通过且没有进行中的修改申请时才能申请修改
  return internshipData.value.status === 2 &&
      internshipData.value.modification_status === 0;
});

const statusText = computed(() => {
  let text = '';

  switch(internshipData.value.status) {
    case 0: text = '草稿状态（可修改）'; break;
    case 1: text = '已提交（等待审核）'; break;
    case 2: text = '已审核通过'; break;
    case 3: text = '已驳回（可修改）'; break;
    default: text = '未知状态';
  }

  // 添加修改申请状态
  if (internshipData.value.modification_status === 1) {
    text += ' - 修改申请处理中';
  } else if (internshipData.value.modification_status === 2) {
    text += ' - 修改申请已通过';
  } else if (internshipData.value.modification_status === 3) {
    text += ' - 修改申请已拒绝';
  }

  return text;
});

const statusTagType = computed(() => {
  if (internshipData.value.modification_status === 1) {
    return 'warning';
  } else if (internshipData.value.modification_status === 2) {
    return 'success';
  } else if (internshipData.value.modification_status === 3) {
    return 'danger';
  }

  switch(internshipData.value.status) {
    case 0: return 'info';
    case 1: return 'warning';
    case 2: return 'success';
    case 3: return 'danger';
    default: return '';
  }
});

const loadInternshipData = async () => {
  try {
    // 先从localStorage加载状态
    const savedState = loadStateFromStorage();
    if (savedState) {
      internshipData.value.status = savedState.status;
      internshipData.value.modification_status = savedState.modification_status;
      internshipData.value.modification_reason = savedState.modification_reason;
    }

    const res = await axios.get(`/internship/${props.s_id}`);
    await calculateActualDays();

    if (res.data.data[0] === undefined) {
      // 如果没有数据，初始化一个新的
      internshipData.value.s_id = props.s_id;
      internshipData.value.status = 0; // 默认草稿状态
      internshipData.value.modification_status = 0; // 默认无修改申请
      return;
    }

    // 合并从后端获取的数据和localStorage中的状态
    internshipData.value = {
      ...res.data.data[0],
      status: savedState?.status ?? res.data.data[0].status ?? 0,
      modification_status: savedState?.modification_status ?? res.data.data[0].modification_status ?? 0,
      modification_reason: savedState?.modification_reason ?? res.data.data[0].modification_reason ?? '',
    };
  } catch (error) {
    console.error('加载实习信息失败:', error);
    ElMessage.error('加载实习信息失败');
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
    // 调用中国节假日API
    const response = await fetch(`https://timor.tech/api/holiday/info/${dateStr}`);
    const data = await response.json();

    // 缓存结果
    holidayCache.value[dateStr] = data.type.type;

    // type: 0-工作日 1-周末 2-节假日
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
    // 保存时保持状态不变（如果是草稿或驳回状态）
    if (internshipData.value.status === undefined ||
        internshipData.value.status === 0 ||
        internshipData.value.status === 3) {
      internshipData.value.status = 0; // 草稿状态
    }

    if (internshipData.value.internship_id) {
      // 更新已有记录
      await axios.put(`/internship`, internshipData.value);
    } else {
      // 创建新记录
      const res = await axios.post(`/internship`, internshipData.value);
      internshipData.value.internship_id = res.data.data.internship_id;
    }

    // 保存状态到localStorage
    saveStateToStorage();

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
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
    );

    // 更新状态为已提交
    internshipData.value.status = 1;

    if (internshipData.value.internship_id) {
      await axios.put(`/internship`, internshipData.value);
    } else {
      const res = await axios.post(`/internship`, internshipData.value);
      internshipData.value.internship_id = res.data.data.internship_id;
    }

    // 保存状态到localStorage
    saveStateToStorage();

    ElMessage.success('提交成功，等待教师审核');
    emit('submit', internshipData.value);
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error);
      ElMessage.error('提交失败');
    }
  }
};

const applyForModification = async () => {
  try {
    // 打开输入原因的对话框
    const { value: reason } = await ElMessageBox.prompt(
        '请输入申请修改的原因',
        '申请修改',
        {
          confirmButtonText: '提交申请',
          cancelButtonText: '取消',
          inputPlaceholder: '请输入修改原因',
          inputValidator: (value) => {
            if (!value) return '请输入修改原因';
            return true;
          }
        }
    );

    if (reason) {
      // 更新修改申请信息
      internshipData.value.modification_reason = reason;
      internshipData.value.modification_status = 1; // 申请中

      // 发送申请到后端
      await axios.put(`/internship/${internshipData.value.internship_id}/apply-modification`, {
        modification_reason: reason
      });

      // 保存状态到localStorage
      saveStateToStorage();

      ElMessage.success('修改申请已提交，等待教师审核');
      emit('submit', internshipData.value);
      // 刷新页面数据
      await loadInternshipData();
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交修改申请失败:', error);
      ElMessage.error('提交修改申请失败');
    }
  }
};

// 监听状态变化，自动保存到localStorage
watch(
    () => ({
      status: internshipData.value.status,
      modification_status: internshipData.value.modification_status,
      modification_reason: internshipData.value.modification_reason,
    }),
    (newValue) => {
      saveStateToStorage();
    },
    { deep: true }
);

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
</style>