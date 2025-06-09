<template>
  <div>
    <h3>周记上传状态</h3>
    <el-table :data="weekStatusData" border style="width: 100%">
      <el-table-column prop="week" label="周数" align="center" />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
              v-if="scope.row.status === 0"
              type="primary"
              @click="handleEdit(scope.row)"
              :disabled="!isEditable(scope.row)"
          >
            编辑
          </el-button>
          <el-button
              v-if="scope.row.status === 0"
              type="success"
              @click="handleSubmit(scope.row)"
              :disabled="!isEditable(scope.row)"
          >
            提交
          </el-button>
          <el-button
              v-if="scope.row.status === 1"
              type="info"
              disabled
          >
            审核中
          </el-button>
          <el-button
              v-if="scope.row.status === 2"
              type="success"
              disabled
          >
            已通过
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../../utils/request'
import { ElMessage } from 'element-plus'

// 定义组件属性
const props = defineProps(['userName'])

// 定义表格数据
const weekStatusData = ref([])

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    0: 'info',
    1: 'primary',
    2: 'success'
  }
  return statusMap[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    0: '初始状态',
    1: '提交审核中',
    2: '审核通过'
  }
  return statusMap[status] || '初始状态'
}

// 判断是否可编辑
const isEditable = (row) => {
  // 判断是否可编辑的逻辑
  return true
}

// 加载周记状态数据
const loadWeekStatus = async () => {
  try {
    const res = await axios.get(`/duser/${props.userName}`)
    const diaries = res.data?.data?.diary || []

    // 构造表格数据
    weekStatusData.value = diaries.map((diary) => ({
      week: diary.week,
      status: diary.status || 0
    }))
  } catch (error) {
    console.error('加载周记状态失败:', error)
    ElMessage.error('加载周记状态失败，请稍后重试')
  }
}

// 编辑周记
const handleEdit = (row) => {
  // 编辑周记的逻辑
  console.log('编辑周记:', row.week)
}

// 提交周记
const handleSubmit = (row) => {
  // 提交周记的逻辑
  console.log('提交周记:', row.week)
  // 模拟提交成功，更新状态
  row.status = 1
  ElMessage.success('周记提交成功，等待审核')
}

// 页面加载时获取周记状态数据
onMounted(() => {
  loadWeekStatus()
})
</script>

<style scoped>
.el-button {
  margin: 0 5px;
}
</style>
