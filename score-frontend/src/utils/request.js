import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 5000,
  withCredentials: true // 关键：允许携带 Cookie/Session ID
})

// 响应拦截器
request.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default request