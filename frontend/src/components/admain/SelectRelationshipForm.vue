<template>
  <div>
    <el-card shadow="never">
      <h2>为学生选择导师</h2>

      <div style="margin-bottom: 16px; display: flex; gap: 12px;">
        <el-button type="primary" @click="assignAdvisorsEvenly">平均分配未绑定学生</el-button>
        <el-button type="success" @click="submitAllAssignments">一键保存所有绑定</el-button>
      </div>

      <el-table :data="students" border stripe style="width: 100%;">
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

const props = defineProps({
  studentData: Array,
  teacherData: Array
})

const students = computed(() => props.studentData || [])
const teachers = computed(() => props.teacherData || [])

const academicAdvisors = computed(() =>
    teachers.value.filter(t => t.advisor_type === '专业')
)
const industryAdvisors = computed(() =>
    teachers.value.filter(t => t.advisor_type === '行业')
)

const advisorSelections = reactive({})

// 初始化学生绑定信息
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
    { immediate: true }
)

// 平均分配导师，仅限未绑定学生
const assignAdvisorsEvenly = () => {
  const academicList = academicAdvisors.value
  const industryList = industryAdvisors.value

  if (!academicList.length || !industryList.length) {
    ElMessage.warning('导师人数不足，无法分配')
    return
  }

  let academicIndex = 0
  let industryIndex = 0

  students.value.forEach(student => {
    const current = advisorSelections[student.s_id]
    if (!current || (!current.academic && !current.industry)) {
      advisorSelections[student.s_id] = {
        academic: academicList[academicIndex % academicList.length].t_id,
        industry: industryList[industryIndex % industryList.length].t_id
      }
      academicIndex++
      industryIndex++
    }
  })

  ElMessage.success('未绑定学生已平均分配导师')
}

// 手动更改绑定
const handleAdvisorChange = (studentId, type, value) => {
  if (!advisorSelections[studentId]) {
    advisorSelections[studentId] = { academic: null, industry: null }
  }
  advisorSelections[studentId][type] = value
}

// 提交单个学生绑定
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

// 批量提交所有学生绑定
const submitAllAssignments = async () => {
  try {
    const promises = students.value.map(student => {
      const selection = advisorSelections[student.s_id]
      if (!selection || !selection.academic || !selection.industry) return null
      return axios.put(`/student/${student.s_id}/${selection.academic}/${selection.industry}`)
    }).filter(Boolean)

    await Promise.all(promises)
    ElMessage.success('所有导师绑定已保存')
  } catch (err) {
    ElMessage.error('批量保存失败')
    console.error(err)
  }
}
</script>
