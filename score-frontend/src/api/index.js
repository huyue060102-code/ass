import request from '@/utils/request'

export const login = (username, password) => {
  return request.post('/auth/login', null, { params: { username, password } })
}

export const logout = () => {
  return request.post('/auth/logout')
}

export const checkLogin = () => {
  return request.get('/auth/check')
}

export const getScores = () => {
  return request.get('/scores')
}

export const getScore = (id) => {
  return request.get(`/scores/${id}`)
}

export const saveScore = (data) => {
  return request.post('/scores', data)
}

export const deleteScore = (id) => {
  return request.delete(`/scores/${id}`)
}