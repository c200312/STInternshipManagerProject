<template>
  <h3>基本信息</h3>
  <el-form @submit.prevent="handleSubmit">
    <!-- 学号 -->
    <el-form-item label="学号">
      <el-input
          v-model="studentData.student_number"
          disabled
          placeholder="学生学号"
      />
    </el-form-item>
    <!-- 姓名 -->
    <el-form-item label="姓名">
      <el-input
          v-model="studentData.student_name"
          placeholder="请输入真实姓名"
      />
    </el-form-item>
    <!-- 性别 -->
    <el-form-item label="性别">
      <el-select
          v-model="studentData.gender"
          placeholder="请选择性别"
      >
        <el-option label="男" value="男" />
        <el-option label="女" value="女" />
      </el-select>
    </el-form-item>
    <!-- 手机号码 -->
    <el-form-item label="手机号码">
      <el-input
          v-model="studentData.phone"
          placeholder="请输入有效手机号码"
          pattern="^1[3-9]\d{9}$"
      /> <!-- 手机号格式验证 -->
    </el-form-item>
    <!-- 家长号码 -->
    <el-form-item label="家长号码">
      <el-input
          v-model="studentData.parent_phone"
          placeholder="请输入家长手机号码"
          pattern="^1[3-9]\d{9}$"
      />
    </el-form-item>
    <!-- 入学年份 -->
    <el-form-item label="入学年份">
      <el-input
          v-model.number="studentData.enrollment_year"
          placeholder="请输入入学年份"
          type="number"
      />  <!-- 限制为数字输入 -->
    </el-form-item>
    <!-- 院系 -->
    <el-form-item label="院系">
      <el-input
          v-model="studentData.department"
          placeholder="请输入院系全称"
      />
    </el-form-item>
    <!-- 提交按钮 -->
    <el-button type="primary" native-type="submit">保存</el-button>
  </el-form>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '../../utils/request';

const props = defineProps(['s_id'])
const emit = defineEmits(['submit']);

const studentData = ref({
  student_number: '',
  student_name: '',
  gender: '',
  phone: '',
  parent_phone: '',
  enrollment_year: null,
  department: ''
});

// 手机号验证提示
const phoneError = ref('');
const parentPhoneError = ref('');

// 手机号格式验证
watch(() => studentData.value.phone, (val) => {
  if (val && !/^1[3-9]\d{9}$/.test(val)) {
    phoneError.value = '请输入正确的手机号码格式';
  } else {
    phoneError.value = '';
  }
});

watch(() => studentData.value.parent_phone, (val) => {
  if (val && !/^1[3-9]\d{9}$/.test(val)) {
    parentPhoneError.value = '请输入正确的手机号码格式';
  } else {
    parentPhoneError.value = '';
  }
});

const loadDiary = async () => {
  try {
    const res = await axios.get(`/student/${props.s_id}`);
    studentData.value = res.data.data;
  } catch (error) {
    ElMessage.error('加载学生信息失败');
  }
};

const handleSubmit = async () => {
  // 简单验证（可根据需求扩展）
  if (!studentData.value.student_name) {
    ElMessage.error('请填写姓名');
    return;
  }
  if (!studentData.value.phone && studentData.value.parent_phone) {
    ElMessage.error('请填写手机号码或家长号码');
    return;
  }

  try {
    await axios.put('/student', studentData.value);
    ElMessage.success('信息更新成功');
    emit('submit', studentData.value);
  } catch (error) {
    ElMessage.error('保存失败，请检查输入');
  }
};

onMounted(() => {
  loadDiary();
});
</script>
