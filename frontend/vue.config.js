const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    webSocketServer: false,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
        onProxyRes(proxyRes, req, res) {
          // 👇 关闭 Nginx/代理缓存
          proxyRes.headers['Cache-Control'] = 'no-cache'
          proxyRes.headers['Content-Type'] = 'text/event-stream'
          proxyRes.headers['Connection'] = 'keep-alive'
          proxyRes.headers['X-Accel-Buffering'] = 'no'
          proxyRes.headers['Transfer-Encoding'] = 'chunked'
        }
      }
    }
  },
  chainWebpack: config => {
    config.plugin('define').tap(args => {
      args[0]['__VUE_PROD_HYDRATION_MISMATCH_DETAILS__'] = JSON.stringify(true)
      return args
    })
  }
})
