<template>
  <div>
    <h2>学生实习信息一览</h2>
    <el-table :data="studentData" style="width: 100%" v-if="studentData.length">
      <el-table-column prop="student.student_number" label="学号" />
      <el-table-column prop="student.student_name" label="姓名" />
      <el-table-column prop="student.stu_class" label="班级" />
      <el-table-column prop="academicAdvisor.teacher_name" label="校内导师" />
      <el-table-column prop="industryAdvisor.teacher_name" label="行业导师" />

      <el-table-column label="公司名称" prop="internship.company_name" width="150" />
      <el-table-column label="实践基地名称" prop="internship.practice_base_name" width="150" />
      <el-table-column label="是否实践基地" width="120">
        <template #default="{ row }">
          {{ row.internship ? (row.internship.is_practice_base ? '是' : '否') : '未填写' }}
        </template>
      </el-table-column>
      <el-table-column label="社会信用代码" prop="internship.credit_code" width="180" />
      <el-table-column label="实践区域" prop="internship.practice_region" width="150" />
      <el-table-column label="审批状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.internship" :type="getApprovalStatusType(row.internship.approval_status)">
            {{ getApprovalStatusText(row.internship.approval_status) }}
          </el-tag>
          <span v-else>未填写</span>
        </template>
      </el-table-column>
      <el-table-column label="开始日期" width="120">
        <template #default="{ row }">
          {{ row.internship ? formatDate(row.internship.start_date) : '未填写' }}
        </template>
      </el-table-column>
      <el-table-column label="结束日期" width="120">
        <template #default="{ row }">
          {{ row.internship ? formatDate(row.internship.end_date) : '未填写' }}
        </template>
      </el-table-column>
      <el-table-column label="实习天数" prop="internship.actual_days" width="100" />
      <el-table-column label="实习岗位" prop="internship.position" width="120" />
      <el-table-column label="薪资" width="100">
        <template #default="{ row }">
          {{ row.internship && row.internship.salary ? `¥${row.internship.salary}` : '未填写' }}
        </template>
      </el-table-column>
      <el-table-column label="实习模式" prop="internship.internship_mode" width="100" />
      <el-table-column label="实习类型" prop="internship.internship_type" width="100" />
      <el-table-column label="企业导师姓名" prop="internship.company_advisor_name" width="120" />
      <el-table-column label="企业导师职位" prop="internship.company_advisor_position" width="120" />
      <el-table-column label="实习地址" prop="internship.address" width="200" />
      <el-table-column label="联系人" prop="internship.contact_person" width="100" />
      <el-table-column label="企业电话" prop="internship.company_phone" width="150" />

    </el-table>

    <el-empty description="暂无数据" v-else />
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from '@/utils/request';
import {ElMessage} from 'element-plus';
import dayjs from "dayjs";
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
// 格式化日期显示
const formatDate = (date) => {
  return date ? dayjs(date).format('YYYY-MM-DD') : '';
};

// 获取审批状态文本
const getApprovalStatusText = (status) => {
  const statusMap = {
    0: '待审批',
    1: '已通过',
    2: '已拒绝'
  };
  return statusMap[status] || '未知状态';
};

// 获取审批状态标签类型
const getApprovalStatusType = (status) => {
  const typeMap = {
    0: 'warning',
    1: 'success',
    2: 'danger'
  };
  return typeMap[status] || 'info';
};






onMounted(() => {
  loadStudentInfo();
});
</script>

<style scoped>

</style>
