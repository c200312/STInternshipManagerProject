<template>
  <el-form @submit.prevent="handleSubmit">
    <el-form-item label="选择周数">
      <el-select
          v-model="selectedWeek"
          placeholder="选择周数"
          @change="loadDiary"
      >
        <el-option
            v-for="week in 13"
            :key="week"
            :label="week === 13 ? '总结' : '第 ' + week + ' 周'"
            :value="week"
        />
      </el-select>
    </el-form-item>

    <el-form-item :label="selectedWeek === 13 ? '实习总结' : `第 ${selectedWeek} 周周记`">
      <el-input
          type="textarea"
          rows="10"
          v-model="content"
          placeholder="请输入周记内容"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../../utils/request'
import { ElMessage } from 'element-plus'

// 定义组件属性
const props = defineProps(['userName'])
// 定义组件事件
const emit = defineEmits(['submit'])

// 周数选择
const selectedWeek = ref(1)

// 周记内容响应式变量
const content = ref('')



// 加载周记数据
const loadDiary = async () =>
{
  const res=await axios.get(`/duser/${props.userName}`);
  const diaries = res.data?.data?.diary || []
  const found = diaries.find(d => d.week === selectedWeek.value)
  content.value = found ? found.content : ''

}


// 保存周记
const saveDiary = async () => {

    await axios.patch(`/duser/${props.userName}`, {
      diary: [{ week: selectedWeek.value, content: content.value }]
    })
    ElMessage.success('周记保存成功')
    emit('submit', content.value)
  }

// 提交处理函数
const handleSubmit = () => {
  saveDiary()
}

// 页面加载时获取周记数据
onMounted(() => {
    loadDiary()

})
</script>