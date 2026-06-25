<template>
  <div class="login-container">
    <div class="login-card">
      <div class="back-home" @click="goHome" title="返回首页">
        <el-icon><ArrowLeft /></el-icon> 返回首页
      </div>

      <h2>{{ roleName }}登录</h2>

      <el-form :model="form" @submit.prevent="handleLogin">
        <el-form-item>
          <el-input
            v-model="form.username"
            :placeholder="placeholderText"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="form.password"
            type="password"
            placeholder="密码"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" native-type="submit" class="submit-btn">
            安全登录
          </el-button>
        </el-form-item>

        <div v-if="error" class="error-msg">{{ error }}</div>
      </el-form>

      <footer>长职教务管理系统</footer>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import { ArrowLeft } from '@element-plus/icons-vue' // 引入返回箭头图标

export default {
  name: 'LoginView',
  components: {
    ArrowLeft
  },
  data() {
    return {
      form: { username: '', password: '' },
      role: 'ADMIN',
      error: ''
    }
  },
  computed: {
    roleName() {
      if (this.role === 'TEACHER') return '教师'
      if (this.role === 'STUDENT') return '学生'
      return '管理员'
    },
    placeholderText() {
      if (this.role === 'TEACHER') return '请输入工号'
      if (this.role === 'STUDENT') return '请输入学号'
      return '请输入管理员账号'
    }
  },
  watch: {
    // 监听路由参数变化，防止在当前页切换角色时标题不更新
    '$route.query.role': {
      immediate: true,
      handler(newRole) {
        if (newRole) {
          this.role = newRole
        }
      }
    }
  },
  methods: {
    goHome() {
      this.$router.push('/') // 编程式导航退回大首页
    },
    async handleLogin() {
      try {
        const res = await request.post('/auth/login', null, {
          params: {
            username: this.form.username,
            password: this.form.password,
            role: this.role
          }
        })

        const responseData = res.data && res.data.data ? res.data.data : res.data;

        if (responseData && (responseData.success === true || responseData.success === 'true')) {
          localStorage.setItem('user', JSON.stringify(responseData))

          if (this.role === 'STUDENT') {
            this.$router.push('/student-dashboard')
          } else {
            this.$router.push('/scores')
          }
        } else {
          this.error = responseData.message || '用户名、密码或登录角色错误'
        }
      } catch (err) {
        this.error = '登录请求失败，请检查网络'
        console.error(err)
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  background: linear-gradient(135deg, #f4f7fc 0%, #e9f0f5 100%);
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0;
  padding: 20px;
  box-sizing: border-box;
}

.login-card {
  position: relative; /* 为返回按钮提供定位基准 */
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
  padding: 3.5rem 2.5rem 2.5rem 2.5rem; /* 增加顶部内边距给按钮留出空间 */
  width: 100%;
  max-width: 420px;
  text-align: center;
  box-sizing: border-box;
}

/* 新增：左上角返回首页按钮样式 */
.back-home {
  position: absolute;
  top: 20px;
  left: 20px;
  font-size: 0.85rem;
  color: #7c8ba0;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 4px;
  transition: color 0.2s ease;
  user-select: none;
}

.back-home:hover {
  color: #1e3c72; /* 悬浮时变深蓝色 */
}

h2 {
  color: #1e3c72;
  margin-top: 0;
  margin-bottom: 2rem;
  font-size: 1.6rem;
  font-weight: 600;
}

.el-form-item {
  margin-bottom: 1.5rem;
}

:deep(.el-input__wrapper) {
  padding: 4px 12px;
}

.submit-btn {
  width: 100%;
  padding: 12px 0;
  font-size: 1rem;
  border-radius: 6px;
}

.error-msg {
  color: #f56c6c;
  font-size: 0.85rem;
  margin-top: 0.5rem;
  text-align: center;
}

footer {
  margin-top: 2rem;
  font-size: 0.8rem;
  color: #a0aec0;
  border-top: 1px solid #edf2f7;
  padding-top: 1rem;
}
</style>