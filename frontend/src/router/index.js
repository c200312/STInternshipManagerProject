import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/studenthome',
    name: 'StudentHome',
    component: () => import('../views/student/StudentHome.vue'),
    meta: { requiresAuth: true, requiredRole: 'student' }
  },
  {
    path: '/teacherhome',
    name: 'TeacherHome',
    component: () => import('../views/teacher/TeacherHome.vue'),
    meta: { requiresAuth: true, requiredRole: 'teacher' }
  },
  {
    path: '/adminhome',
    name: 'AdminHome',
    component: () => import('../views/admin/AdminHome.vue'),
    meta: { requiresAuth: true, requiredRole: 'admin' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 添加全局前置守卫，确保登录后才能访问需要认证的页面
router.beforeEach((to, from, next) => {
  const userInfo = localStorage.getItem('userInfo');
  
  // 检查是否需要认证
  if (to.meta.requiresAuth) {
    if (!userInfo) {
      // 未登录，跳转到登录页
      next({ name: 'Login' });
      return;
    }
    
    // 检查角色权限
    if (to.meta.requiredRole) {
      try {
        const user = JSON.parse(userInfo);
        if (user.role !== to.meta.requiredRole) {
          // 角色不匹配，跳转到对应角色的首页
          const correctPath = `/${user.role}home`;
          next({ path: correctPath });
          return;
        }
      } catch (error) {
        // userInfo 解析失败，清除并跳转到登录页
        localStorage.removeItem('userInfo');
        next({ name: 'Login' });
        return;
      }
    }
  }
  
  next();
})

export default router