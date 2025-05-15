<template>
  <h3>企业信息</h3>
  <el-form @submit.prevent="handleSubmit">

    <el-form-item label="开始日期">
      <el-date-picker v-model="internshipData.start_date" type="date" placeholder="选择日期" />
    </el-form-item>
    <el-form-item label="结束日期">
      <el-date-picker v-model="internshipData.end_date" type="date" placeholder="选择日期"/>
    </el-form-item>
    <el-form-item label="实习天数">
      <el-input v-model="internshipData.actual_days"/>
    </el-form-item>
    <el-form-item label="实习公司">
      <el-input v-model="companyData.company_name"/>
    </el-form-item>
    <el-form-item label="实习地点">
      <el-input v-model="internshipData.address"/>
    </el-form-item>
    <el-form-item label="实习岗位">
      <el-input v-model="internshipData.position"/>
    </el-form-item>
    <el-button type="primary" native-type="submit">保存</el-button>
  </el-form>

</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '../../utils/request';

// 定义组件属性
const props = defineProps(['userId', 'userName'])
// 定义组件事件
const emit = defineEmits(['submit']);

// 公司信息响应式变量
const internshipData = ref({});
const companyData = ref({});

const loadInternshipData = async () => {
  const res = await axios.get(`/internship/${props.userId}`);
  internshipData.value = res.data.data[0];
  await loadCompanyData();
};

const loadCompanyData = async () => {
  if (!internshipData.value || !internshipData.value.c_id) {
    return;
  }

  const res = await axios.get(`/company/${internshipData.value.c_id}`);
  companyData.value = res.data.data;
};

const handleSubmit = async () => {
  await axios.put(`/internship`, internshipData.value);
  await axios.put(`/company`, companyData.value);
    ElMessage.success('保存成功');
    emit('submit', internshipData.value, companyData.value);

}
onMounted(() => {
  loadInternshipData()
  })



</script>
