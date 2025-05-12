<template>
    <h3>基本信息</h3>
    <el-form @submit.prevent="handleSubmit">
      <!-- 学号 -->
      <el-form-item label="学号">
        <el-input v-model="studentData.student_number" disabled />
      </el-form-item>
      <!-- 姓名 -->
      <el-form-item label="姓名">
        <el-input v-model="studentData.student_name" />
      </el-form-item>
      <!-- 性别 -->
      <el-form-item label="性别">
        <el-select v-model="studentData.gender" placeholder="请选择性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      <!-- 手机号码 -->
      <el-form-item label="手机号码">
        <el-input v-model="studentData.phone" />
      </el-form-item>
      <!-- 家长号码 -->
      <el-form-item label="家长号码">
        <el-input v-model="studentData.parent_phone" />
      </el-form-item>
      <!-- 入学年份 -->
      <el-form-item label="入学年份">
        <el-input v-model.number="studentData.enrollment_year" />
      </el-form-item>
      <!-- 院系 -->
      <el-form-item label="院系">
        <el-input v-model="studentData.department" />
      </el-form-item>
      <!-- 提交按钮 -->
      <el-button type="primary" native-type="submit">保存</el-button>
    </el-form>

</template>

<script setup>
import {  ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import axios from '../../utils/request';

// 定义组件属性
const props = defineProps(['userId', 'userName'])
// 定义组件事件
const emit = defineEmits(['submit']);

// 学生信息响应式变量
const studentData = ref({});

const loadDiary = async () =>{
  const res=await axios.get(`/student/getbyusernumber/${props.userName}`);
  studentData.value = res.data.data

}



// 提交处理函数
const handleSubmit = async () => {
    await axios.put('/student', studentData.value);
    ElMessage.success('信息更新成功');
    emit('submit', studentData.value);
}

onMounted(() => {
    loadDiary()

  })

</script>

