<template>
  <div class="fixed-header">
    <el-dropdown @command="handleUserCommand">
      <span class="el-dropdown-link user-info">
        <el-avatar
            :size="32"
            src="https://cdn-icons-png.flaticon.com/512/847/847969.png"
        />
        <span>{{ username }}</span>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const props = defineProps({
  username: {
    type: String,
    required: true
  }
})

const emit = defineEmits(['logout'])

const router = useRouter()

const handleUserCommand = (command) => {
  if (command === 'logout') {
    // 登出逻辑直接在组件中处理
    localStorage.removeItem('userInfo')
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
/* 顶部头像+姓名模块 */
.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 50px;
  background: #fff;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 20px;
  z-index: 1000;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: bold;
  cursor: pointer;
}
</style>