<template>
  <el-main class="right-main">
    <div class="week-selector" v-if="studentView">
      <el-select v-model="selectedLabel" placeholder="请选择评语周期">
        <el-option
            v-for="option in weekPeriodOptions"
            :key="option.label"
            :label="option.label"
            :value="option.label"
        />
      </el-select>
    </div>

    <el-card v-if="studentView">
      <h2>{{ studentView.student.student_name}} 的信息</h2>
      <p>学号: {{ studentView.student.student_number }}</p>
      <p>班级: {{ studentView.student.stu_class}}</p>
      <p>实习单位: {{ studentView.duser?.company?.[0]?.name || '无' }}</p>

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

      <el-button type="primary" @click="generateComment" :disabled="!selectedPeriod || displayedDiaries.length === 0"
      >
        生成评语
      </el-button>

      <el-input
          v-if="comment"
          type="textarea"
          v-model="comment"
          rows="10"
          style="margin-top: 10px;"
          placeholder="生成的评语将显示在这里"
      />
      <el-button
          v-if="comment"
          type="success"
          style="margin-top: 10px;"
          @click="saveComment"
      >
        保存评语
      </el-button>

    </el-card>
  </el-main>
</template>

<script setup>
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

const props = defineProps({
  studentView: Object
})

const selectedLabel = ref(null)
const selectedPeriod = ref(null)
const displayedDiaries = ref([])
const comment = ref('')

const weekPeriodOptions = [
  { label: '第1-2周', weeks: [1, 2] },
  { label: '第3-4周', weeks: [3, 4] },
  { label: '第5-6周', weeks: [5, 6] },
  { label: '第7-8周', weeks: [7, 8] },
  { label: '第9-10周', weeks: [9, 10] },
  { label: '第11-12周', weeks: [11, 12] },
  { label: '实习总结（第13周）', weeks: [13] }
]

watch(selectedLabel, () => {
  if (
      !selectedLabel.value ||
      !props.studentView ||
      !props.studentView.duser ||
      !Array.isArray(props.studentView.duser.diary)
  ) {
    displayedDiaries.value = []
    selectedPeriod.value = null
    comment.value = ''
    return
  }

  const period = weekPeriodOptions.find(o => o.label === selectedLabel.value)
  if (!period) return

  selectedPeriod.value = period

  displayedDiaries.value = props.studentView.duser.diary
      .filter(d => period.weeks.includes(Number(d.week)))
      .sort((a, b) => Number(a.week) - Number(b.week))

  const week = period.weeks[0]
  const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username
  const allComments = props.studentView.duser.comment || []
  const match = allComments.find(
      c => c.week === week && c.teachername === teacherName
  )
  comment.value = match ? match.content : ''
})
watch(
    () => props.studentView,
    (newVal) => {
      if (!newVal) return

      // 重置状态
      selectedLabel.value = null
      selectedPeriod.value = null
      displayedDiaries.value = []
      comment.value = ''
    },
    { immediate: true }
)


const generateComment = async () => {
  if (!props.studentView?.student || !selectedPeriod.value) {
    ElMessage.warning('请选择学生和周期')
    return
  }

  try {
    const res = await axios.post('/dteacher/generate-comment', {
      studentId: props.studentView.student.student_number,
      weeks: selectedPeriod.value.weeks
    })
    comment.value = res.data.data
  } catch {
    ElMessage.error('生成评语失败')
  }
}
const saveComment = async () => {
  if (
      !comment.value ||
      !props.studentView?.student?.student_number ||
      !selectedPeriod.value?.weeks?.[0]
  ) {
    ElMessage.warning('信息不完整，无法保存')
    return
  }

  const week = selectedPeriod.value.weeks[0] // 保存为当前周期第一周
  const teacherName = JSON.parse(localStorage.getItem('userInfo') || '{}').username

  try {
    await axios.patch(`/duser/${props.studentView.student.student_number}`, {
      comment: [
        {
          week,
          teachername: teacherName,
          content: comment.value
        }
      ]
    })
    ElMessage.success('保存成功')
  } catch (err) {
    ElMessage.error('保存失败')
    console.error(err)
  }
}

</script>

<style scoped>
.right-main {
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}
.week-selector {
  position: fixed;
  top: 50px;
  left: 210px;
  width: calc(100% - 210px);
  z-index: 1001;
  background-color: #fff;
  padding: 10px 20px;
  border-bottom: 1px solid #eee;
}
</style>
