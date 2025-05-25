<template>
  <div class="internship-info-form">
    <h3>实习信息登记</h3>
    <el-form
        @submit.prevent="handleSubmit"
        :model="internshipForm"
        label-width="180px"
        class="custom-form"
    >
      <!-- 基础信息 -->
      <el-form-item label="学生学号" required>
        <el-input v-model="internshipForm.s_id" disabled placeholder="学生学号"/>
      </el-form-item>

      <el-form-item label="实习单位名称" required>
        <el-input v-model="internshipForm.company_name" placeholder="请输入实习单位全称"/>
      </el-form-item>

      <el-form-item label="是否校外实践基地">
        <el-switch
            v-model="internshipForm.is_practice_base"
            active-text="是"
            inactive-text="否"
            active-color="#409eff"
            inactive-color="#c0c4cc"
        />
      </el-form-item>

      <el-form-item label="所属实践基地名称">
        <el-input v-model="internshipForm.practice_base_name" placeholder="请输入实践基地名称"/>
      </el-form-item>

      <!-- 企业资质 -->
      <el-form-item label="统一社会信用代码">
        <el-input v-model="internshipForm.credit_code" placeholder="请输入18位统一社会信用代码"/>
      </el-form-item>

      <el-form-item label="实习地区">
        <el-input v-model="internshipForm.practice_region" placeholder="例：北京市-朝阳区"/>
      </el-form-item>

      <!-- 时间信息 -->
      <el-form-item label="实习时间范围" required>
        <el-date-picker
            v-model="internshipForm.start_date"
            type="date"
            placeholder="开始日期"
            style="width: 48%"
            @change="calculateActualDays"
        />
        <span class="date-separator">-</span>
        <el-date-picker
            v-model="internshipForm.end_date"
            type="date"
            placeholder="结束日期"
            style="width: 48%"
            @change="calculateActualDays"
        />
      </el-form-item>

      <el-form-item label="实际实习天数">
        <el-input
            v-model.number="internshipForm.actual_days"
            type="number"
            placeholder="自动计算"
            min="0"
            disabled
        />
      </el-form-item>

      <!-- 岗位信息 -->
      <el-form-item label="实习岗位">
        <el-input v-model="internshipForm.position" placeholder="例：软件测试实习生"/>
      </el-form-item>

      <el-form-item label="实习方式">
        <el-select v-model="internshipForm.internship_mode" placeholder="请选择实习方式">
          <el-option label="集中实习" value="集中实习"/>
          <el-option label="分散实习" value="分散实习"/>
          <el-option label="跟岗实习" value="跟岗实习"/>
        </el-select>
      </el-form-item>

      <el-form-item label="实习类型">
        <el-select v-model="internshipForm.internship_type" placeholder="请选择实习类型">
          <el-option label="专业实习" value="专业实习"/>
          <el-option label="毕业实习" value="毕业实习"/>
          <el-option label="认知实习" value="认知实习"/>
        </el-select>
      </el-form-item>

      <!-- 企业指导 -->
      <el-form-item label="企业指导人员姓名">
        <el-input v-model="internshipForm.company_advisor_name" placeholder="请输入指导人员姓名"/>
      </el-form-item>

      <el-form-item label="企业指导人员职务">
        <el-input v-model="internshipForm.company_advisor_position" placeholder="例：技术主管"/>
      </el-form-item>

      <!-- 联系信息 -->
      <el-form-item label="实习单位地址">
        <el-input
            v-model="internshipForm.address"
            type="textarea"
            rows="2"
            placeholder="请输入详细地址"
        />
      </el-form-item>

      <el-form-item label="单位联系人">
        <el-input v-model="internshipForm.contact_person" placeholder="请输入联系人姓名"/>
      </el-form-item>

      <el-form-item label="联系电话">
        <el-input
            v-model="internshipForm.company_phone"
            placeholder="请输入联系电话"
            maxlength="13"
            show-word-limit
        />
      </el-form-item>

      <!-- 提交按钮 -->
      <el-form-item>
        <el-button type="primary" native-type="submit">
          {{ internshipForm.internship_id ? '更新实习信息' : '提交实习申请' }}
        </el-button>
        <el-button @click="resetForm" style="margin-left: 15px">重置表单</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '@/utils/request';

// 缓存节假日数据，避免重复请求
const holidayCache = ref({});

// 接收父组件传递的参数（可编辑的实习记录ID）
const props = defineProps({
  editId: { type: Number, default: null },
  userName: { type: String, required: true } // 新增接收用户名的属性
});

const emit = defineEmits(['saveSuccess']);

// 初始化表单数据
const internshipForm = ref({
  s_id: '',
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
  company_phone: ''
});

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
  if (!internshipForm.value.start_date || !internshipForm.value.end_date) {
    return;
  }

  const start = new Date(internshipForm.value.start_date);
  const end = new Date(internshipForm.value.end_date);

  // 显示加载状态
  internshipForm.value.actual_days = '计算中...';

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

  internshipForm.value.actual_days = days;
};

// 加载编辑数据
const loadEditData = async () => {
  if (props.editId) {
    try {
      const { data } = await axios.get(`/internship/${props.editId}`);
      internshipForm.value = { ...internshipForm.value, ...data };
      calculateActualDays();
    } catch (error) {
      ElMessage.error('加载实习信息失败');
    }
  }
};

// 获取学生学号
const getStudentNumber = async () => {
  try {
    const res = await axios.get(`/student/getbyusernumber/${props.userName}`);
    internshipForm.value.s_id = res.data.data.student_number;
  } catch (error) {
    ElMessage.error('加载学生信息失败');
  }
};

// 提交处理
const handleSubmit = async () => {
  try {
    let res;
    if (internshipForm.value.internship_id) {
      res = await axios.put('/internship', internshipForm.value);
    } else {
      res = await axios.post('/internship', internshipForm.value);
    }
    ElMessage.success(internshipForm.value.internship_id ? '更新成功' : '提交成功');
    emit('saveSuccess', res.data);
  } catch (error) {
    ElMessage.error('操作失败，请检查输入');
  }
};

// 重置表单
const resetForm = () => {
  internshipForm.value = { ...internshipForm.value, ...{
      company_name: '',
      practice_base_name: '',
      is_practice_base: false,
      credit_code: '',
      practice_region: '',
      start_date: null,
      end_date: null,
      actual_days: null,
      position: '',
      salary: null,
      internship_mode: '',
      internship_type: '',
      company_advisor_name: '',
      company_advisor_position: '',
      address: '',
      contact_person: '',
      company_phone: ''
    }};
};

// 初始化加载
onMounted(() => {
  loadEditData();
  getStudentNumber();
});
</script>

<style scoped>
.el-form{
text-align: left;
}
</style>
