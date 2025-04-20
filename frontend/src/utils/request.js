import axios from 'axios'

const instance = axios.create({
    baseURL: '/api', // 对应 vue.config.js 中的代理设置
    timeout: 100000
})

export default instance
