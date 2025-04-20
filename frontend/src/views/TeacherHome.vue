<template>
  <el-container>
    <!-- 顶部选择周期 -->
    <el-header>
      <el-select
          v-if="selectedStudent"
          v-model="selectedLabel"
          placeholder="请选择评语周期"
      >
        <el-option
            v-for="option in weekPeriodOptions"
            :key="option.label"
            :label="option.label"
            :value="option.label"
        />
      </el-select>
    </el-header>

    <el-container>
      <!-- 左侧学生菜单 -->
      <el-aside width="200px">
        <el-menu>
          <el-menu-item
              v-for="student in studentList"
              :key="student.id"
              @click="selectStudent(student)"
          >
            {{ student.name }}
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 右侧内容展示 -->
      <el-main>
        <el-card v-if="selectedStudent">
          <h2>{{ selectedStudent.name }} 的信息</h2>
          <p>学号: {{ selectedStudent.id }}</p>
          <p>班级: {{ selectedStudent.class || '无' }}</p>
          <p>实习单位: {{ selectedStudent.company?.[0]?.name || '无' }}</p>

          <!-- 周记内容 -->
          <el-card v-if="displayedDiaries.length" style="margin: 10px 0;">
            <h4>参考周记：</h4>
            <div v-for="item in displayedDiaries" :key="item.week" style="margin-bottom: 10px;">
              <strong>第 {{ item.week }} 周：</strong>
              <p style="white-space: pre-line;">{{ item.content }}</p>
            </div>
          </el-card>

          <!-- 按钮 -->
          <el-button
              type="primary"
              @click="generateComment"
              :disabled="!selectedPeriod"
          >
            生成评语
          </el-button>

          <!-- 生成结果 -->
          <el-input
              v-if="comment"
              type="textarea"
              v-model="comment"
              placeholder="生成的评语将显示在这里"
              readonly
              rows="10"
              style="margin-top: 10px;"
          />
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from '../utils/request'
import { ElMessage } from 'element-plus'

const studentList = ref([])
const selectedStudent = ref(null)
const selectedLabel = ref(null)
const selectedPeriod = ref(null)
const displayedDiaries = ref([])
const comment = ref('')


const weekPeriodOptions = ref([
  { label: '第1-2周', weeks: [1, 2] },
  { label: '第3-4周', weeks: [3, 4] },
  { label: '第5-6周', weeks: [5, 6] },
  { label: '第7-8周', weeks: [7, 8] },
  { label: '第9-10周', weeks: [9, 10] },
  { label: '第11-12周', weeks: [11, 12] },
  { label: '实习总结（第13周）', weeks: [13] }
])

const fetchStudents = async () => {
  try {
    const res = await axios.get('/duser/all')
    studentList.value = res.data.data
  } catch {
    ElMessage.error('获取学生列表失败')
  }
}

const selectStudent = (student) => {
  selectedStudent.value = student
  selectedLabel.value = null
  selectedPeriod.value = null
  displayedDiaries.value = []
  comment.value = ''
}

watch(selectedLabel, () => {
  if (!selectedLabel.value || !selectedStudent.value) return

  const period = weekPeriodOptions.value.find(o => o.label === selectedLabel.value)
  if (!period) return

  selectedPeriod.value = period

  displayedDiaries.value = (selectedStudent.value.diary || [])
      .filter(d => period.weeks.includes(d.week))
      .sort((a, b) => a.week - b.week)
})

const generateComment = async () => {
  if (!selectedStudent.value || !selectedPeriod.value) {
    ElMessage.warning('请选择学生和周期')
    return
  }

  try {
    const res = await axios.post('/teacher/generate-comment', {
      studentId: selectedStudent.value.id,
      weeks: selectedPeriod.value.weeks
    })
    comment.value = res.data.data
  } catch {
    ElMessage.error('生成评语失败')
  }
}

onMounted(() => fetchStudents())
</script>

<style scoped>
</style>
