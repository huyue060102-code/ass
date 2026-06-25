const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: 8039, // 前端开发服务器端口
    proxy: {
      '/api': {
        target: 'http://localhost:8038', // 后端 Boot 端口
        changeOrigin: true
      }
    }
  }
})