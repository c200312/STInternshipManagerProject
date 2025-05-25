<template>
  <div>
    <el-card shadow="never">
      <h2>学生导师分配管理</h2>

      <!-- 选择班级 -->
      <div style="margin-bottom: 16px;">
        <el-select
            v-model="selectedClasses"
            multiple
            placeholder="请选择班级"
            clearable
            style="width: 300px"
        >
          <el-option
              v-for="cls in classList"
              :key="cls"
              :label="cls"
              :value="cls"
          />
        </el-select>
      </div>

      <!-- 学生表格 -->
      <el-table :data="filteredStudents" border style="width: 100%;">
        <el-table-column prop="student_number" label="学号" width="120" />
        <el-table-column prop="student_name" label="姓名" width="120" />
        <el-table-column prop="stu_class" label="班级" width="120" />
        <el-table-column prop="major" label="专业" />

        <el-table-column label="学业导师">
          <template #default="scope">
            <span>
              {{ getTeacherName(scope.row.academic_advisor_id) || '未分配' }}
            </span>
          </template>
        </el-table-column>

        <el-table-column label="行业导师">
          <template #default="scope">
            <span>
              {{ getTeacherName(scope.row.industry_advisor_id) || '未分配' }}
            </span>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分配区域 -->
      <div style="margin-top: 20px; display: flex; gap: 12px; align-items: center;">
        <span>将选中班级学生分配给：</span>

        <el-select
            v-model="selectedAcademicAdvisor"
            placeholder="学业导师"
            style="width: 200px"
        >
          <el-option
              v-for="teacher in academicAdvisors"
              :key="teacher.t_id"
              :label="teacher.teacher_name"
              :value="teacher.t_id"
          />
        </el-select>

        <el-select
            v-model="selectedIndustryAdvisor"
            placeholder="行业导师"
            style="width: 200px"
        >
          <el-option
              v-for="teacher in industryAdvisors"
              :key="teacher.t_id"
              :label="teacher.teacher_name"
              :value="teacher.t_id"
          />
        </el-select>

        <el-button type="primary" @click="assignToSelectedClasses">
          确定分配
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

// 学生和导师数据
const studentData = ref([])
const teacherData = ref([])

// 获取数据方法
async function loadStudentData() {
  const res = await axios.get(`/student`)
  studentData.value = res.data.data || []
}

async function loadTeacherData() {
  const res = await axios.get(`/teacher`)
  teacherData.value = res.data.data || []
}

// 初始化加载
onMounted(() => {
  loadStudentData()
  loadTeacherData()
})

// 导师筛选
const academicAdvisors = computed(() =>
    teacherData.value.filter(t => t.advisor_type === '专业')
)
const industryAdvisors = computed(() =>
    teacherData.value.filter(t => t.advisor_type === '行业')
)

// 班级筛选
const classList = computed(() => {
  const set = new Set()
  studentData.value.forEach(s => set.add(s.stu_class))
  return Array.from(set)
})

// 选中项
const selectedClasses = ref([])
const selectedAcademicAdvisor = ref(null)
const selectedIndustryAdvisor = ref(null)

// 筛选学生
const filteredStudents = computed(() => {
  if (!selectedClasses.value.length) return studentData.value
  return studentData.value.filter(s => selectedClasses.value.includes(s.stu_class))
})

// 获取导师名
const getTeacherName = (id) => {
  const t = teacherData.value.find(t => t.t_id === id)
  return t?.teacher_name || ''
}

// 分配
const assignToSelectedClasses = async () => {
  if (!selectedClasses.value.length) {
    ElMessage.warning('请选择班级')
    return
  }
  if (!selectedAcademicAdvisor.value || !selectedIndustryAdvisor.value) {
    ElMessage.warning('请选择两个导师')
    return
  }

  const targets = studentData.value.filter(s =>
      selectedClasses.value.includes(s.stu_class)
  )

  try {
    const requests = targets.map(s =>
        axios.put(`/student/${s.s_id}/${selectedAcademicAdvisor.value}/${selectedIndustryAdvisor.value}`)
    )
    await Promise.all(requests)
    ElMessage.success('分配成功')
    await loadStudentData() // ✅ 分配完成后刷新
  } catch (err) {
    ElMessage.error('分配失败')
    console.error(err)
  }
}
</script>
