<template>
  <div class="home-container">
    <div class="card">
      <div class="logo">
        <el-icon :size="40" color="#fff"><Management /></el-icon>
      </div>

      <h1>学生信息管理</h1>
      <div class="sub-title">SCORES • RECORDS • EFFICIENCY</div>

      <div class="institution">
        <span class="icon">🏫</span> 长职 • 教务处
      </div>

      <p class="slogan">成绩管理 • 数据可视化 • 便捷维护</p>

      <div class="button-group">
        <div v-if="loggedIn">
          <el-button type="primary" size="large" @click="goDashboard">进入系统 →</el-button>
        </div>

        <div v-else class="login-buttons">
          <el-button type="primary" @click="goLogin('ADMIN')">管理员登录</el-button>
          <el-button plain @click="goLogin('TEACHER')">教师登录</el-button>
          <el-button plain @click="goLogin('STUDENT')">学生登录</el-button>
        </div>
      </div>

      <footer>© 2026 长职 学生信息管理系统 | 版权所有</footer>
    </div>
  </div>
</template>

<script>
import { checkLogin } from '@/api'
import { Management } from '@element-plus/icons-vue'

export default {
  name: 'HomeView',
  components: {
    Management
  },
  data() {
    return {
      loggedIn: false,
      userRole: '' // 记录当前登录用户的角色
    }
  },
  async created() {
    // 页面加载时自动校验当前的 Session 登录状态
    try {
      const res = await checkLogin()
      if (res.data.loggedIn) {
        this.loggedIn = true
        this.userRole = res.data.role
      } else {
        this.loggedIn = false
      }
    } catch {
      this.loggedIn = false
    }
  },
  methods: {
    // 点击对应的登录按钮，跳转到统一登录页，并通过 query 传参带过去角色类型
    goLogin(roleType) {
      this.$router.push({
        path: '/login',
        query: { role: roleType }
      })
    },
    // 已登录用户点击进入系统时的跳转逻辑
    goDashboard() {
      if (this.userRole === 'STUDENT') {
        this.$router.push('/student-dashboard') // 学生角色跳转到专属查分页
      } else {
        this.$router.push('/scores') // 管理员和教师跳转到成绩管理主列表
      }
    }
  }
}
</script>

<style scoped>
/* 全局背景：带有科技感的淡蓝灰色渐变 */
.home-container {
  background: linear-gradient(135deg, #f4f7fc 0%, #e9f0f5 100%);
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 核心毛玻璃/白色悬浮卡片 */
.card {
  background: white;
  border-radius: 24px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
  padding: 3.5rem 2.5rem;
  max-width: 550px;
  width: 90%;
  text-align: center;
}

/* 蓝底圆形 Logo */
.logo {
  width: 75px;
  height: 75px;
  background-color: #1e3c72;
  border-radius: 50%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1.5rem;
  box-shadow: 0 4px 12px rgba(30, 60, 114, 0.3);
}

/* 主标题：学生信息管理 */
h1 {
  color: #1e3c72;
  font-size: 2rem;
  font-weight: 600;
  margin: 0;
}

/* 橙色英文副标题 */
.sub-title {
  color: #ff8c42;
  font-size: 0.85rem;
  letter-spacing: 2px;
  margin-top: 0.5rem;
  margin-bottom: 1.5rem;
  font-weight: bold;
}

/* 机构教务处标识 */
.institution {
  color: #1e3c72;
  font-weight: 600;
  font-size: 1.05rem;
  margin-bottom: 0.5rem;
}

.institution .icon {
  margin-right: 4px;
}

/* 底部核心标语 */
.slogan {
  color: #7c8ba0;
  font-size: 0.95rem;
  margin-bottom: 2.5rem;
}

/* 登录按钮区域布局调节 */
.button-group {
  margin-bottom: 1rem;
}

.login-buttons {
  display: flex;
  justify-content: center;
  gap: 12px; /* 按钮之间的间距 */
}

.login-buttons .el-button {
  padding: 12px 20px;
  font-size: 0.95rem;
  border-radius: 6px;
}

/* 底部版权声明线 */
footer {
  margin-top: 3rem;
  font-size: 0.8rem;
  color: #a0aec0;
  border-top: 1px solid #edf2f7;
  padding-top: 1.5rem;
}
</style>