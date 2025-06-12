<template>
  <div class="word-report-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Word报告管理</span>
        </div>
      </template>
      
      <div class="report-content">
        <!-- 文档状态检查 -->
        <el-row :gutter="20" class="status-section">
          <el-col :span="24">
            <el-alert
              v-if="documentStatus === 'exists'"
              title="文档状态：存在Word报告文档 可下载或重新生成"
              type="success"
              :closable="false"
              show-icon>
            </el-alert>
            <el-alert
              v-else-if="documentStatus === 'not-found'"
              title="文档状态：不存在Word报告文档 可生成报告"
              type="warning"
              :closable="false"
              show-icon>
            </el-alert>
            <el-alert
              v-else-if="documentStatus === 'error'"
              :title="`文档状态：检查失败 - ${errorMessage}`"
              type="error"
              :closable="false"
              show-icon>
            </el-alert>
          </el-col>
        </el-row>

        <!-- 操作按钮区域 -->
        <el-row :gutter="20" class="action-section">
          <el-col :span="12">
            <el-button 
              type="warning" 
              @click="generateReport" 
              :loading="generating"
              icon="Document">
              生成报告
            </el-button>
          </el-col>
          <el-col :span="12">
            <el-button 
              type="success" 
              @click="downloadDocument" 
              :disabled="documentStatus !== 'exists'"
              :loading="downloading"
              icon="Download">
              下载Word报告
            </el-button>
          </el-col>
        </el-row>


      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

// Props
const props = defineProps({
  studentNumber: {
    type: String,
    required: true
  },
  s_id: {
    type: String,
    required: false
  }
})

// 响应式数据
const documentStatus = ref('') // 'exists', 'not-found', 'error', ''
const errorMessage = ref('')
const checking = ref(false)
const downloading = ref(false)
const generating = ref(false)
const lastCheckTime = ref('')

// 检查文档是否存在
const checkDocument = async () => {
  checking.value = true
  try {
    const response = await axios.get(`/student/checkdocument/${props.studentNumber}`)
    if (response.data.code === "200") {
      documentStatus.value = 'exists'
      ElMessage.success(response.data.message || '文档检查成功')
    } else {
      documentStatus.value = 'not-found'
      errorMessage.value = response.data.message || '未找到文档'
      ElMessage.warning(response.data.message || '未找到Word文档')
    }
    lastCheckTime.value = new Date().toLocaleString()
  } catch (error) {
    documentStatus.value = 'error'
    errorMessage.value = error.response?.data?.message || error.message || '检查失败'
    ElMessage.error('检查文档状态失败：' + errorMessage.value)
  } finally {
    checking.value = false
  }
}

// 下载文档
const downloadDocument = async () => {
  if (documentStatus.value !== 'exists') {
    ElMessage.warning('请先检查文档状态')
    return
  }
  
  downloading.value = true
  try {
    const response = await axios.get(`/student/download/${props.studentNumber}`, {
      responseType: 'blob',
      timeout: 30000 // 增加超时时间
    })
    
    // 创建下载链接
    const blob = new Blob([response.data])
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    
    // 从响应头获取文件名，如果没有则使用默认名称
    const contentDisposition = response.headers['content-disposition']
    let filename = `${props.studentNumber}_实习报告.docx`
    if (contentDisposition) {
      const filenameMatch = contentDisposition.match(/filename="(.+)"/)
      if (filenameMatch) {
        filename = filenameMatch[1]
      }
    }
    
    link.download = filename
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
    
    ElMessage.success('文档下载成功')
  } catch (error) {
    console.error('下载错误详情:', error)
    if (error.response?.status === 404) {
      ElMessage.error('文档不存在或已被删除')
      documentStatus.value = 'not-found'
    } else if (error.response?.status === 500) {
      ElMessage.error('服务器内部错误，请检查后端日志')
      console.error('500错误详情:', error.response)
    } else {
      ElMessage.error('下载失败：' + (error.response?.data?.message || error.message))
    }
  } finally {
    downloading.value = false
  }
}

// 生成报告
const generateReport = async () => {
  generating.value = true
  try {
    const response = await axios.get(`/admin/wordoutput/${props.s_id}`)
    if (response.data.code === "200") {
      ElMessage.success(response.data.message || 'Word报告生成成功')
      // 生成成功后自动检查文档状态
      setTimeout(() => {
        checkDocument()
      }, 1000)
    } else {
      ElMessage.error(response.data.message || '报告生成失败')
    }
  } catch (error) {
    console.error('生成报告错误详情:', error)
    ElMessage.error('生成报告失败：' + (error.response?.data?.message || error.message))
  } finally {
    generating.value = false
  }
}


// 组件挂载时自动检查文档状态
onMounted(() => {
  checkDocument()
})
</script>

<style scoped>
.word-report-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.report-content {
  padding: 20px 0;
}

.status-section {
  margin-bottom: 30px;
}

.action-section {
  margin-bottom: 30px;
}

.action-section .el-button {
  width: 100%;
}

.info-section {
  margin-top: 20px;
}

.el-descriptions {
  margin-top: 20px;
}

.el-alert {
  margin-bottom: 20px;
}
</style>