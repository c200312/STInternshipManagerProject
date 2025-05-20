<template>
  <el-aside width="200px" class="student-aside">
    <el-menu>
      <el-menu-item
          v-for="studentView in students"
          :key="studentView.student.s_id"
          @click="$emit('select', studentView)"
      >
        {{ studentView.student.student_name}}
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script setup>
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
}
</style>
