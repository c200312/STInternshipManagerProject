<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">用户登录</h2>
      <el-form
          :model="loginForm"
          :rules="rules"
          ref="loginFormRef"
          @keyup.enter="submitForm"
      >
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              show-userpassword
          />
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              :loading="loading"
              @click="submitForm"
          >
            {{ loading ? '登录中...' : '立即登录' }}
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { useRouter } from 'vue-router'

// 配置 axios 实例
const api = axios.create({
  baseURL: '/api',
  timeout: 5000
})

const router = useRouter()
const loading = ref(false)

// 表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const rules = reactive({
  username: [
    { required: true, message: '用户名不能为空', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
})

// 表单提交
const submitForm = async () => {
  loading.value = true;
  try {
    const { data } = await api.post('/user/login', loginForm);
    if (data.code === "200") {
      ElMessage.success('登录成功');
      localStorage.setItem('userInfo', JSON.stringify(data.data));

      // 根据用户类型跳转不同页面
      const routePath = data.data.role === 'student'
          ? '/studenthome'
          : '/teacherhome';

      await router.push(routePath);
    } else {
      ElMessage.error(data.msg);
    }
  } catch (error) {
    ElMessage.error('登录失败');
    console.error('Login failed:', error);
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f0f2f5;
}

.login-card {
  width: 400px;
  padding: 20px;
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #409EFF;
}

.login-btn {
  width: 100%;
  margin-top: 10px;
}
</style>