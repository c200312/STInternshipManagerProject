<template>
  <div>
    <h2>学生成绩一览</h2>
    <el-table :data="studentData" style="width: 100%" v-if="studentData.length">
      <el-table-column prop="student.student_number" label="学号" />
      <el-table-column prop="student.student_name" label="姓名" />
      <el-table-column prop="student.stu_class" label="班级" />
      <el-table-column prop="academicAdvisor.teacher_name" label="校内导师" />
      <el-table-column prop="industryAdvisor.teacher_name" label="行业导师" />
      
      <el-table-column label="出勤" prop="assessment.attendance_score" width="80" />
      <el-table-column label="任务完成" prop="assessment.task_score" width="100" />
      <el-table-column label="专业素养" prop="assessment.professionalism_score" width="100" />
      <el-table-column label="岗位表现" prop="assessment.performance_score" width="100" />
      <el-table-column label="总结报告" prop="assessment.summary_score" width="100" />
      <el-table-column label="实习成果" prop="assessment.practice_result_score" width="100" />
      <el-table-column label="公司评分" prop="assessment.company_score" width="100" />
      <el-table-column label="学校评分" prop="assessment.school_score" width="100" />
      
      <el-table-column label="总分" prop="assessment.total_score" width="100">
      </el-table-column>
    </el-table>

    <el-empty description="暂无数据" v-else />
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from '@/utils/request';
import {ElMessage} from 'element-plus';

const studentData = ref([]);

async function loadStudentInfo() {
  try {
    const res = await axios.get(`/admin/stinfo`);
    studentData.value = res.data?.data || [];
  } catch (err) {
    ElMessage.error('加载学生数据失败');
    console.error(err);
  }
}






onMounted(() => {
  loadStudentInfo();
});
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>