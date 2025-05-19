<template>
  <div>
    <el-card shadow="never">
      <h2>为学生选择导师</h2>

      <el-table :data="students" border stripe style="width: 100%; margin-top: 20px;">
        <el-table-column prop="student_number" label="学号" width="100" />
        <el-table-column prop="student_name" label="姓名" width="100" />
        <el-table-column prop="major" label="专业" />
        <el-table-column prop="stu_class" label="班级" />
        <el-table-column label="学业导师">
          <template #default="scope">
            <el-select
                :model-value="advisorSelections[scope.row.s_id]?.academic"
                @update:model-value="value => handleAdvisorChange(scope.row.s_id, 'academic', value)"
                placeholder="选择学业导师"
                style="width: 150px"
            >
              <el-option
                  v-for="teacher in academicAdvisors"
                  :key="teacher.t_id"
                  :label="teacher.teacher_name"
                  :value="teacher.t_id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="行业导师">
          <template #default="scope">
            <el-select
                :model-value="advisorSelections[scope.row.s_id]?.industry"
                @update:model-value="value => handleAdvisorChange(scope.row.s_id, 'industry', value)"
                placeholder="选择行业导师"
                style="width: 150px"
            >
              <el-option
                  v-for="teacher in industryAdvisors"
                  :key="teacher.t_id"
                  :label="teacher.teacher_name"
                  :value="teacher.t_id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="submitAssignment(scope.row.s_id)">
              保存
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

// Props
const props = defineProps({
  studentData: Array,
  teacherData: Array
})

// 响应式计算属性
const students = computed(() => props.studentData || [])
const teachers = computed(() => props.teacherData || [])
const academicAdvisors = computed(() =>
    teachers.value.filter(t => t.advisor_type === '专业')
)
const industryAdvisors = computed(() =>
    teachers.value.filter(t => t.advisor_type === '行业')
)

// 学生对应的导师选择
const advisorSelections = reactive({})

// 初始化选中值
watch(
    () => props.studentData,
    (newVal) => {
      if (newVal && newVal.length) {
        newVal.forEach(student => {
          advisorSelections[student.s_id] = {
            academic: student.academic_advisor_id || null,
            industry: student.industry_advisor_id || null
          }
        })
      }
    },
    {immediate: true}
)
console.log(students)

// 处理变更
const handleAdvisorChange = (studentId, type, value) => {
  if (!advisorSelections[studentId]) {
    advisorSelections[studentId] = {academic: null, industry: null}
  }
  advisorSelections[studentId][type] = value
}

// 提交绑定数据
const submitAssignment = async (studentId) => {
  const selection = advisorSelections[studentId]
  try {
    await axios.put(`/student/${studentId}/${selection.academic}/${selection.industry}`)
    ElMessage.success('导师分配成功')
  } catch (err) {
    ElMessage.error('导师分配失败')
    console.error(err)
  }
}
</script>
