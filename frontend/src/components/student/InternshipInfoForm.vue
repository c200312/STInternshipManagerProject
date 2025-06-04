<template>
  <h3>企业信息</h3>
  <el-form @submit.prevent="handleSubmit">

    <!-- 基础信息 -->
    <el-form-item label="学生学号" required>
      <el-input v-model="internshipData.s_id" disabled placeholder="学生学号"/>
    </el-form-item>

    <el-form-item label="实习单位名称" required>
      <el-input v-model="internshipData.company_name" placeholder="请输入实习单位全称"/>
    </el-form-item>

    <el-form-item label="是否校外实践基地">
      <el-switch
          v-model="internshipData.is_practice_base"
          active-text="是"
          inactive-text="否"
          active-color="#409eff"
          inactive-color="#c0c4cc"
      />
    </el-form-item>

    <el-form-item label="所属实践基地名称">
      <el-input v-model="internshipData.practice_base_name" placeholder="请输入实践基地名称"/>
    </el-form-item>

    <!-- 企业资质 -->
    <el-form-item label="统一社会信用代码">
      <el-input v-model="internshipData.credit_code" placeholder="请输入18位统一社会信用代码"/>
    </el-form-item>

    <el-form-item label="实习地区">
      <el-input v-model="internshipData.practice_region" placeholder="例：北京市-朝阳区"/>
    </el-form-item>

    <!-- 时间信息 -->
    <el-form-item label="实习时间范围" required>
      <el-date-picker
          v-model="internshipData.start_date"
          type="date"
          placeholder="开始日期"
          style="width: 48%"
          @change="calculateActualDays"
      />
      <span class="date-separator">-</span>
      <el-date-picker
          v-model="internshipData.end_date"
          type="date"
          placeholder="结束日期"
          style="width: 48%"
          @change="calculateActualDays"
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
      <el-input v-model="internshipData.position" placeholder="例：软件测试实习生"/>
    </el-form-item>

    <el-form-item label="实习方式">
      <el-select v-model="internshipData.internship_mode" placeholder="请选择实习方式">
        <el-option label="集中实习" value="集中实习"/>
        <el-option label="分散实习" value="分散实习"/>
        <el-option label="跟岗实习" value="跟岗实习"/>
      </el-select>
    </el-form-item>

    <el-form-item label="实习类型">
      <el-select v-model="internshipData.internship_type" placeholder="请选择实习类型">
        <el-option label="专业实习" value="专业实习"/>
        <el-option label="毕业实习" value="毕业实习"/>
        <el-option label="认知实习" value="认知实习"/>
      </el-select>
    </el-form-item>

    <!-- 企业指导 -->
    <el-form-item label="企业指导人员姓名">
      <el-input v-model="internshipData.company_advisor_name" placeholder="请输入指导人员姓名"/>
    </el-form-item>

    <el-form-item label="企业指导人员职务">
      <el-input v-model="internshipData.company_advisor_position" placeholder="例：技术主管"/>
    </el-form-item>

    <!-- 联系信息 -->
    <el-form-item label="实习单位地址">
      <el-input
          v-model="internshipData.address"
          type="textarea"
          rows=2
          placeholder="请输入详细地址"
      />
    </el-form-item>

    <el-form-item label="单位联系人">
      <el-input v-model="internshipData.contact_person" placeholder="请输入联系人姓名"/>
    </el-form-item>

    <el-form-item label="联系电话">
      <el-input
          v-model="internshipData.company_phone"
          placeholder="请输入联系电话"
          maxlength="13"
          show-word-limit
      />
    </el-form-item>

    <el-button type="primary" native-type="submit">保存</el-button>
  </el-form>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '@/utils/request';

// 定义组件属性
const props = defineProps(['userId'])
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
  s_id: props.userId

});

const loadInternshipData = async () => {
  const res = await axios.get(`/internship/${props.userId}`);
  await calculateActualDays();

  console.log(res.data.data[0]);
  if (res.data.data[0]===undefined) return;
  internshipData.value = res.data.data[0];
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


const handleSubmit = async () => {
  await axios.put(`/internship`, internshipData.value);
  ElMessage.success('保存成功');
  emit('submit', internshipData.value);

}
onMounted(() => {
  loadInternshipData()
})



</script>