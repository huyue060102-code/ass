import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import Login from '@/views/Login.vue'
import ScoreList from '@/views/ScoreList.vue'
import ScoreForm from '@/views/ScoreForm.vue'
import { checkLogin } from '@/api'

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/scores', component: ScoreList, meta: { requiresAuth: true } },
  { path: '/scores/add', component: ScoreForm, meta: { requiresAuth: true } },
  { path: '/scores/edit/:id', component: ScoreForm, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局路由守卫：校验登录状态
router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth) {
    try {
      const res = await checkLogin()
      if (res.data && res.data.loggedIn) {
        next()
      } else {
        next('/login')
      }
    } catch {
      next('/login')
    }
  } else {
    next()
  }
})

export default router