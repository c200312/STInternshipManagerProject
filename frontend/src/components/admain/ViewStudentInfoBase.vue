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
      <el-table-column label="审核状态" width="120">
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
      
      <!-- 操作列 -->
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <div v-if="row.internship">
            <el-button 
              v-if="row.internship.approval_status === 1" 
              type="success" 
              size="small" 
              @click="openReviewDialog(row, 'approve')"
            >
              通过
            </el-button>
            <el-button 
              v-if="row.internship.approval_status === 1" 
              type="danger" 
              size="small" 
              @click="openReviewDialog(row, 'reject')"
            >
              拒绝
            </el-button>
            <el-button
              type="info" 
              size="small" 
              @click="viewDetails(row)"
            >
              查看详情
            </el-button>
          </div>
          <span v-else>无实习信息</span>
        </template>
      </el-table-column>

    </el-table>

    <el-empty description="暂无数据" v-else />
    
    <!-- 审核对话框 -->
    <el-dialog 
      v-model="reviewDialogVisible" 
      :title="reviewDialogTitle" 
      width="500px"
      @close="resetReviewDialog"
    >
      <el-form :model="reviewForm" label-width="80px">
        <el-form-item label="学生姓名">
          <span>{{ currentStudent?.student?.student_name }}</span>
        </el-form-item>
        <el-form-item label="学号">
          <span>{{ currentStudent?.student?.student_number }}</span>
        </el-form-item>
        <el-form-item label="公司名称">
          <span>{{ currentStudent?.internship?.company_name }}</span>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-tag :type="getApprovalStatusType(reviewForm.approval_status)">
            {{ getApprovalStatusText(reviewForm.approval_status) }}
          </el-tag>
        </el-form-item>
        <el-form-item label="审核意见" required>
          <el-input 
            v-model="reviewForm.review_comments" 
            type="textarea" 
            :rows="4" 
            placeholder="请输入审核意见"
          />
        </el-form-item>
        <el-form-item label="审核人">
          <el-input v-model="reviewForm.reviewer_name" placeholder="请输入审核人姓名" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="reviewDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitReview">确认审核</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 详情对话框 -->
    <el-dialog 
      v-model="detailDialogVisible" 
      title="实习信息详情" 
      width="800px"
    >
      <div v-if="currentStudent?.internship">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="学生姓名">{{ currentStudent.student?.student_name }}</el-descriptions-item>
          <el-descriptions-item label="学号">{{ currentStudent.student?.student_number }}</el-descriptions-item>
          <el-descriptions-item label="班级">{{ currentStudent.student?.stu_class }}</el-descriptions-item>
          <el-descriptions-item label="校内导师">{{ currentStudent.academicAdvisor?.teacher_name }}</el-descriptions-item>
          <el-descriptions-item label="行业导师">{{ currentStudent.industryAdvisor?.teacher_name }}</el-descriptions-item>
          <el-descriptions-item label="公司名称">{{ currentStudent.internship.company_name }}</el-descriptions-item>
          <el-descriptions-item label="实践基地名称">{{ currentStudent.internship.practice_base_name || '无' }}</el-descriptions-item>
          <el-descriptions-item label="是否实践基地">{{ currentStudent.internship.is_practice_base ? '是' : '否' }}</el-descriptions-item>
          <el-descriptions-item label="社会信用代码">{{ currentStudent.internship.credit_code || '无' }}</el-descriptions-item>
          <el-descriptions-item label="实践区域">{{ currentStudent.internship.practice_region }}</el-descriptions-item>
          <el-descriptions-item label="审核状态">
            <el-tag :type="getApprovalStatusType(currentStudent.internship.approval_status)">
              {{ getApprovalStatusText(currentStudent.internship.approval_status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="开始日期">{{ formatDate(currentStudent.internship.start_date) }}</el-descriptions-item>
          <el-descriptions-item label="结束日期">{{ formatDate(currentStudent.internship.end_date) }}</el-descriptions-item>
          <el-descriptions-item label="实习天数">{{ currentStudent.internship.actual_days }}天</el-descriptions-item>
          <el-descriptions-item label="实习岗位">{{ currentStudent.internship.position }}</el-descriptions-item>
          <el-descriptions-item label="薪资">{{ currentStudent.internship.salary ? `¥${currentStudent.internship.salary}` : '未填写' }}</el-descriptions-item>
          <el-descriptions-item label="实习模式">{{ currentStudent.internship.internship_mode }}</el-descriptions-item>
          <el-descriptions-item label="实习类型">{{ currentStudent.internship.internship_type }}</el-descriptions-item>
          <el-descriptions-item label="企业导师姓名">{{ currentStudent.internship.company_advisor_name }}</el-descriptions-item>
          <el-descriptions-item label="企业导师职位">{{ currentStudent.internship.company_advisor_position }}</el-descriptions-item>
          <el-descriptions-item label="联系人">{{ currentStudent.internship.contact_person }}</el-descriptions-item>
          <el-descriptions-item label="企业电话">{{ currentStudent.internship.company_phone }}</el-descriptions-item>
          <el-descriptions-item label="实习地址" :span="2">{{ currentStudent.internship.address }}</el-descriptions-item>
        </el-descriptions>
        
        <!-- 审核历史 -->
        <div v-if="currentStudent.internship.review_comments" style="margin-top: 20px;">
          <el-divider content-position="left">审核信息</el-divider>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="审核人">{{ currentStudent.internship.reviewer_name || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="审核时间">{{ currentStudent.internship.review_time ? formatDate(currentStudent.internship.review_time) : '未知' }}</el-descriptions-item>
            <el-descriptions-item label="审核意见">{{ currentStudent.internship.review_comments }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from '@/utils/request';
import {ElMessage, ElMessageBox} from 'element-plus';
import dayjs from "dayjs";

const studentData = ref([]);
const reviewDialogVisible = ref(false);
const detailDialogVisible = ref(false);
const currentStudent = ref(null);
const reviewDialogTitle = ref('');
const reviewForm = ref({
  approval_status: 0,
  review_comments: '',
  reviewer_name: ''
});

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
function getApprovalStatusText(status) {
  const statusMap = {
    0: '草稿',
    1: '待审核',
    2: '审核通过',
    3: '审核拒绝',
    5: '已撤回'
  };
  return statusMap[status] || '未知状态';
}

// 获取审批状态标签类型
function getApprovalStatusType(status) {
  const typeMap = {
    0: 'info',
    1: 'warning',
    2: 'success',
    3: 'danger',
    5: 'info'
  };
  return typeMap[status] || 'info';
}

// 打开审核对话框
function openReviewDialog(student, action) {
  currentStudent.value = student;
  reviewForm.value = {
    approval_status: action === 'approve' ? 2 : 3,
    review_comments: '',
    reviewer_name: ''
  };
  
  const actionMap = {
    'approve': '审核通过',
    'reject': '审核拒绝',
  };
  
  reviewDialogTitle.value = `${actionMap[action]} - ${student.student?.student_name}`;
  reviewDialogVisible.value = true;
}

// 重置审核对话框
function resetReviewDialog() {
  currentStudent.value = null;
  reviewForm.value = {
    approval_status: 0,
    review_comments: '',
    reviewer_name: ''
  };
  reviewDialogTitle.value = '';
}

// 提交审核
async function submitReview() {
  if (!reviewForm.value.review_comments.trim()) {
    ElMessage.warning('请填写审核意见');
    return;
  }
  
  if (!reviewForm.value.reviewer_name.trim()) {
    ElMessage.warning('请填写审核人姓名');
    return;
  }
  
  try {
    const response = await axios.post(`/internship/${currentStudent.value.internship.s_id}/review`, {
      approval_status: reviewForm.value.approval_status,
      review_comments: reviewForm.value.review_comments,
      reviewer_name: reviewForm.value.reviewer_name
    });
    
    if (response.data.code === '200') {
      ElMessage.success('审核操作成功');
      reviewDialogVisible.value = false;
      await loadStudentInfo(); // 重新加载数据
    } else {
      ElMessage.error(response.data.msg || '审核操作失败');
    }
  } catch (error) {
    console.error('审核操作失败:', error);
    ElMessage.error('审核操作失败，请稍后重试');
  }
}

// 查看详情
function viewDetails(student) {
  currentStudent.value = student;
  detailDialogVisible.value = true;
}

// 检查是否可以审核
function canReview(status) {
  return status === 1; // 只有待审核状态才能进行审核操作
};






onMounted(() => {
  loadStudentInfo();
});
</script>

<style scoped>

</style>
