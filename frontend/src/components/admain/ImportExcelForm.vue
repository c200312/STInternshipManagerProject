<template>
  <el-card shadow="never">
    <h2>导入学生/教师信息</h2>

    <!-- 下载模板按钮 -->
    <div style="margin-bottom: 20px;">
      <el-button type="primary" @click="downloadTemplate('student')">下载学生模板</el-button>
      <el-button type="primary" @click="downloadTemplate('teacher')" style="margin-left: 10px">下载教师模板</el-button>
    </div>

    <!-- 拖拽上传 -->
    <el-upload
        drag
        :file-list="fileList"
        :before-upload="beforeUpload"
        :http-request="customUpload"
        :on-remove="handleRemove"
        accept=".xlsx,.xls"
        :limit="1"
    >
      <el-icon><Upload /></el-icon>
      <div class="el-upload__text">将文件拖到此处，或 <em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只支持 Excel 文件（.xls / .xlsx）</div>
    </el-upload>

    <!-- 导入按钮 -->
    <div style="margin-top: 20px;">
      <el-button type="success" :disabled="!uploadedFile" @click="importData">
        导入数据
      </el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'
import { Upload } from '@element-plus/icons-vue'

// 上传文件状态
const uploadedFile = ref(null)
const fileList = ref([])

// 下载模板
const downloadTemplate = async (type) => {
  try {
    const res = await axios.get(`/admin/download/${type}`, {
      responseType: 'blob'
    })

    const blob = new Blob([res.data])
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = type === 'student' ? '学生导入模板.xlsx' : '教师导入模板.xlsx'
    link.click()

    ElMessage.success('模板下载成功')
  } catch (err) {
    ElMessage.error('模板下载失败')
    console.error(err)
  }
}

// 上传前校验
const beforeUpload = (file) => {
  const isExcel = file.type.includes('excel') || file.name.endsWith('.xlsx') || file.name.endsWith('.xls')
  if (!isExcel) {
    ElMessage.error('只能上传 Excel 文件！')
  }
  return isExcel
}

// 自定义上传逻辑（不自动提交）
const customUpload = ({ file }) => {
  uploadedFile.value = file
  fileList.value = [file]
}

// 删除文件
const handleRemove = () => {
  uploadedFile.value = null
  fileList.value = []
}

// 导入按钮事件
const importData = async () => {
  const formData = new FormData()
  formData.append('file', uploadedFile.value)

  try {
    const res = await axios.post('/admin/import', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
    ElMessage.success(res.data.message || '导入成功')
    uploadedFile.value = null
    fileList.value = []
  } catch (err) {
    ElMessage.error('导入失败，请检查文件格式')
    console.error(err)
  }
}
</script>
