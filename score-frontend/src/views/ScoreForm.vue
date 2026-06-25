<template>
  <div class="form-container">
    <h2>{{ isEdit ? '编辑成绩' : '添加成绩' }}</h2>
    <el-form :model="form" label-width="100px" @submit.prevent="handleSubmit">
      <el-form-item label="学生姓名" required>
        <el-input v-model="form.studentName" />
      </el-form-item>
      <el-form-item label="课程名称" required>
        <el-input v-model="form.courseName" />
      </el-form-item>
      <el-form-item label="分数" required>
        <el-input-number v-model="form.score" :min="0" :max="150" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">保存</el-button>
        <el-button @click="$router.push('/scores')">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getScore, saveScore } from '@/api'
import { ElMessage } from 'element-plus'

export default {
  data() {
    return {
      form: { id: null, studentName: '', courseName: '', score: 0 },
      isEdit: false
    }
  },
  async created() {
    const id = this.$route.params.id
    if (id) {
      this.isEdit = true
      try {
        const res = await getScore(id)
        this.form = res.data
      } catch {
        ElMessage.error('获取成绩详情失败')
      }
    }
  },
  methods: {
    async handleSubmit() {
      if (!this.form.studentName || !this.form.courseName) {
        ElMessage.warning('请填写完整的学生姓名和课程名称')
        return
      }

      // 🔥 核心修复二：在前端进行动态业务边界校验
      const course = this.form.courseName.trim()
      const score = Number(this.form.score)
      const isMainSubject = course === '语文' || course === '数学' || course === '英语' ||
                            course.toLowerCase() === 'chinese' || course.toLowerCase() === 'math' || course.toLowerCase() === 'english'

      if (isMainSubject) {
        // 语数外分支逻辑：如果大于150分，直接在网页拦截
        if (score > 150) {
          ElMessage.error(`${course}成绩最高不能超过 150 分！`)
          return
        }
      } else {
        // 普通科目分支逻辑：如果大于100分，直接在网页拦截
        if (score > 100) {
          ElMessage.error(`${course}成绩最高不能超过 100 分！`)
          return
        }
      }

      try {
        await saveScore(this.form)
        ElMessage.success('保存成功')
        this.$router.push('/scores')
      } catch {
        ElMessage.error('保存失败')
      }
    }
  }
}
</script>

<style scoped>
.form-container { max-width: 500px; margin: 3rem auto; padding: 2rem; background: white; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
h2 { margin-bottom: 2rem; color: #1e3c72; text-align: center; }
</style>