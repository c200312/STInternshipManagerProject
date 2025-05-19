<template>
  <div>
    <!-- 顶部：头像 + 姓名模块 -->
    <UserHeader
        :username="userName"
    />

    <!-- 主体区域：左侧学生列表 + 右侧内容 -->
    <el-container class="main-container">
      <!-- 左侧学生列表 -->
      <el-aside width="200px" class="student-aside">
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

      <!-- 右侧内容区 -->
      <el-main class="right-main">
        <!-- 周期选择器 -->
        <div class="week-selector">
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
        </div>

        <!-- 学生信息内容展示 -->
        <el-card v-if="selectedStudent">
          <h2>{{ selectedStudent.name }} 的信息</h2>
          <p>学号: {{ selectedStudent.id }}</p>
          <p>班级: {{ selectedStudent.class || '无' }}</p>
          <p>实习单位: {{ selectedStudent.company?.[0]?.name || '无' }}</p>

          <el-card v-if="displayedDiaries.length" style="margin: 10px 0;">
            <h4>参考周记：</h4>
            <div
                v-for="item in displayedDiaries"
                :key="item.week"
                style="margin-bottom: 10px;"
            >
              <strong>第 {{ item.week }} 周：</strong>
              <p style="white-space: pre-line;">{{ item.content }}</p>
            </div>
          </el-card>

          <el-button
              type="primary"
              @click="generateComment"
              :disabled="!selectedPeriod"
          >
            生成评语
          </el-button>

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
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../../utils/request'
import UserHeader from '../../components/common/UserHeader.vue'
import { ElMessage } from 'element-plus'

// 教师信息
const teacherInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = teacherInfo.username
const router = useRouter()

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
    const res = await axios.get('/duser')
    studentList.value = res.data.data

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
/* 主体容器整体布局 */
.main-container {
  margin-top: 50px; /* 留出顶部用户头像栏的空间 */
  height: calc(100vh - 50px);
  overflow: hidden;
}

/* 学生列表 */
.student-aside {
  height: 100%;
  overflow-y: auto;
  border-right: 1px solid #eee;
  background-color: #fafafa;
}

/* 右侧内容区 */
.right-main {
  top: auto;
  padding: 20px;
  overflow-y: auto;
  position: relative;
  height: 100%;
  background-color: #fff; /* 防止下层内容透出 */
}


/* 周期选择器样式 */
.week-selector {
  position: fixed;
  top: 50px; /* 与头像栏高度保持一致 */
  left: 210px; /* 与侧边栏宽度对齐 */
  width: calc(100% - 210px);
  z-index: 1001;
  background-color: #fff;
  padding: 10px 20px;
  border-bottom: 1px solid #eee;
}


</style>
