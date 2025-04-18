<template>
  <el-form @submit.prevent="handleSubmit">
    <el-form-item :label="week === 13 ? '实习总结' : `第 ${week} 周周记`">
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
import { ref, watch } from 'vue'

const { week, userId, initContent } = defineProps(['week', 'userId', 'initContent'])
const emit = defineEmits(['submit'])

const content = ref('')
watch(() => initContent, val => content.value = val, { immediate: true })

const handleSubmit = () => {
  emit('submit', content.value)
}
</script>
