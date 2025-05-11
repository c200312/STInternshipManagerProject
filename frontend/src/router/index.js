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
    meta: { requiresAuth: true }
  },
    {
      path: '/teacherhome',
      name: 'TeacherHome',
      component: () => import('../views/teacher/TeacherHome.vue'),
      meta: { requiresAuth: true }
    },
  {
    path: '/adminhome',
    name: 'AdminHome',
    component: () => import('../views/admin/AdminHome.vue'),
    meta: { requiresAuth: true }
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 添加全局前置守卫，确保登录后才能访问需要认证的页面
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !localStorage.getItem('userInfo')) {
    next({ name: 'Login' });
  } else {
    next();
  }
})

export default router