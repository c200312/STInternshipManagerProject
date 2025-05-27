<template>
  <div>
    <h2>学生实习信息一览</h2>
    <el-table :data="studentData" style="width: 100%" v-if="studentData.length">
      <el-table-column prop="student.student_number" label="学号" />
      <el-table-column prop="student.student_name" label="姓名" />
      <el-table-column prop="student.gender" label="性别" />
      <el-table-column prop="student.department" label="学院" />
      <el-table-column prop="student.major" label="专业" />
      <el-table-column prop="student.stu_class" label="班级" />
      <el-table-column prop="academicAdvisorName" label="校内导师" />
      <el-table-column prop="industryAdvisorName" label="行业导师" />

      <el-table-column label="实习信息">
        <template #default="{ row }">
          <el-tooltip
              class="item"
              effect="dark"
              placement="top-start"
              :content="renderInternshipTooltip(row)"
              raw-content
          >
            <el-button type="text" size="small">查看</el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="成绩">
        <template #default="{ row }">
          <el-tooltip
              class="item"
              effect="dark"
              placement="top-start"
              :content="renderAssessmentTooltip(row)"
              raw-content
          >
            <el-button type="text" size="small">查看</el-button>
          </el-tooltip>
        </template>
      </el-table-column>

<!--      <el-table-column label="周记">-->
<!--        <template #default="{ row }">-->
<!--          <el-tooltip-->
<!--              class="item"-->
<!--              effect="dark"-->
<!--              placement="top-start"-->
<!--              :content="renderDiaryTooltip(row)"-->
<!--              raw-content-->
<!--          >-->
<!--            <el-button type="text" size="small">查看</el-button>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->

<!--      <el-table-column label="评语">-->
<!--        <template #default="{ row }">-->
<!--          <el-tooltip-->
<!--              class="item"-->
<!--              effect="dark"-->
<!--              placement="top-start"-->
<!--              :content="renderCommentTooltip(row)"-->
<!--              raw-content-->
<!--          >-->
<!--            <el-button type="text" size="small">查看</el-button>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->

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
const renderInternshipTooltip = (row) => {
  const i = row.internship;
  if (!i) return '无实习记录';
  return `
    <div>
      <p><b>公司：</b>${i.company_name}</p>
      <p><b>岗位：</b>${i.position}</p>
      <p><b>地址：</b>${i.address}</p>
      <p><b>公司社会信用代码：</b>${i.credit_code}</p>
      <p><b>开始：</b>${dayjs(i.start_date).format('YYYY-MM-DD')}</p>
      <p><b>结束：</b>${dayjs(i.end_date).format('YYYY-MM-DD')}</p>
      <p><b>天数：</b>${i.actual_days}</p>
    </div>
  `;
};
const renderAssessmentTooltip = (row) => {
  const a = row.assessment;
  if (!a) return '无成绩记录';
  return `
    <div>
      <p><b>出勤：</b>${a.attendance_score}</p>
      <p><b>任务完成：</b>${a.task_score}</p>
      <p><b>专业素养：</b>${a.professionalism_score}</p>
      <p><b>岗位表现：</b>${a.performance_score}</p>
      <p><b>总结报告：</b>${a.summary_score}</p>
      <p><b>实习成果：</b>${a.practice_result_score}</p>
      <p><b>公司评分：</b>${a.company_score}</p>
      <p><b>学校评分：</b>${a.school_score}</p>
      <p><b>总分：</b><b>${a.total_score}</b></p>
    </div>
  `;
};

// const renderDiaryTooltip = (row) => {
//   const diaries = row.duser?.diary || [];
//   if (!diaries.length) return '无周记记录';
//   return diaries
//       .sort((a, b) => a.week - b.week)
//       .map(d => `<p><b>第${d.week}周：</b>${d.content}</p>`)
//       .join('');
// };
//
// const renderCommentTooltip = (row) => {
//   const comments = row.duser?.comment || [];
//   if (!comments.length) return '无评语记录';
//   return comments
//       .sort((a, b) => a.week - b.week)
//       .map(c => `<p><b>第${c.week}周 - ${c.teachername}：</b>${c.content}</p>`)
//       .join('<hr style="margin: 4px 0;" />');
// };



onMounted(() => {
  loadStudentInfo();
});
</script>

<style scoped>

</style>
