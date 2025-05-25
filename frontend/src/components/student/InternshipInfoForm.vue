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
      <el-input v-model="internshipData.company_name"/>
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
const internshipData = ref({
  start_date: '',
  end_date: '',
  actual_days: '',
  company_name: '',
  address: '',
  position: '',
  s_id: props.userId,

});

const loadInternshipData = async () => {
  const res = await axios.get(`/internship/${props.userId}`);
  console.log(res.data.data[0]);
  if (res.data.data[0]===undefined) return;
  internshipData.value = res.data.data[0];
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
