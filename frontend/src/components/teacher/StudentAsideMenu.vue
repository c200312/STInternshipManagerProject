<template>
  <el-aside width="200px" class="student-aside">
    <el-menu :default-active="selectedStudentId">
      <el-menu-item
          v-for="studentView in students"
          :key="studentView.student.s_id"
          :index="studentView.student.s_id.toString()"
          @click="$emit('select', studentView)"
      >
        {{ studentView.student.student_name }}
      </el-menu-item>
    </el-menu>
    
    <!-- 切换按钮 -->
    <div class="toggle-link">
      <el-button
          type="text"
          @click="toggleView"
          style="width: 100%; text-align: left;"
      >
        <i :class="currentView === 'diary' ? 'el-icon-star-on' : 'el-icon-document'"></i>
        <span>{{ currentView === 'diary' ? '评分管理' : '周记管理' }}</span>
      </el-button>
    </div>
  </el-aside>
</template>
<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  students: Array,  // 类型为 StudentView[]
  selectedStudent: Object  // 当前选中的学生
})

// 计算选中学生的ID
const selectedStudentId = computed(() => {
  return props.selectedStudent?.student?.s_id?.toString() || ''
})

const emit = defineEmits(['select', 'view-change'])

const currentView = ref('diary') // 'diary' 或 'assessment'

const toggleView = () => {
  currentView.value = currentView.value === 'diary' ? 'assessment' : 'diary'
  emit('view-change', currentView.value)
}
</script>

<style scoped>
.student-aside {
  height: 100%;
  overflow-y: auto;
  border-right: 1px solid #eee;
  background-color: #fafafa;
  display: flex;
  flex-direction: column;
}

.toggle-link {
  padding: 10px;
  border-top: 1px solid #eee;
  margin-top: auto;
}
</style>