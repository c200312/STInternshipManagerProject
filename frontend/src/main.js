import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // 确保正确引入 router
import store from './store'
import axios from "axios";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

// 将axios挂载到全局
app.config.globalProperties.$axios = axios

app.use(store)
    .use(router) // 确保正确使用 router
    .use(ElementPlus)
    .mount('#app')