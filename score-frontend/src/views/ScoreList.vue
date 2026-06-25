<template>
  <div class="container">
    <div class="header">
      <h2>学生成绩列表</h2>
      <div>
        <el-button type="primary" @click="$router.push('/scores/add')">添加成绩</el-button>
        <el-button type="danger" plain @click="handleLogout">退出登录</el-button>
      </div>
    </div>

    <el-table :data="scores" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="studentName" label="学生姓名" />
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="score" label="分数" width="120" align="center" />
      <el-table-column label="操作" width="180" align="center">
        <template #default="{ row }">
          <el-button size="small" type="warning" @click="$router.push(`/scores/edit/${row.id}`)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div v-if="scores.length === 0" class="empty-text">暂无成绩记录</div>
  </div>
</template>

<script>
import { getScores, deleteScore, logout } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  data() {
    return { scores: [] }
  },
  created() {
    this.fetchScores()
  },
  methods: {
    async fetchScores() {
      try {
        const res = await getScores()
        this.scores = res.data
      } catch {
        ElMessage.error('获取成绩列表失败')
      }
    },
    async handleDelete(id) {
      try {
        await ElMessageBox.confirm('确定删除该成绩吗？', '提示', { type: 'warning' })
        await deleteScore(id)
        ElMessage.success('删除成功')
        this.fetchScores()
      } catch {
        // 取消删除
      }
    },
   async handleLogout() {
     try {
       await logout() // 调用后端注销接口
     } catch (e) {
       console.error(e)
     }
     localStorage.removeItem('user') // 清理前端缓存
     this.$router.push('/') // 🛠️ 关键修改：退出后直接回大首页，不再回单独的登录页！
   }
  }
}
</script>

<style scoped>
.container { padding: 2rem; max-width: 1000px; margin: 0 auto; }
.header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; }
.empty-text { text-align: center; padding: 20px; color: #909399; }
</style>