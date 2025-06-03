<template>
  <el-aside width="200px" class="student-aside">
    <el-menu>
      <el-menu-item
          v-for="studentView in students"
          :key="studentView.student.s_id"
          @click="$emit('select', studentView)"
      >
        {{ studentView.student.student_name }}
      </el-menu-item>
    </el-menu>
    <!-- 添加评分页面跳转链接 -->
    <div class="assessment-link">
      <el-button
          type="text"
          @click="router.push('/assessment')"
          style="width: 100%; text-align: left;"
      >
        <i class="el-icon-star-on"></i>
        <span>评分管理</span>
      </el-button>
    </div>
  </el-aside>
</template>
<script setup>
import {useRouter} from 'vue-router'
const router = useRouter()

defineProps({
  students: Array  // 类型为 StudentView[]
})
defineEmits(['select'])
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

.assessment-link {
  padding: 10px;
  border-top: 1px solid #eee;
  margin-top: auto;
}
</style>