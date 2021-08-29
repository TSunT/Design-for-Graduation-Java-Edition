import request from '@/utils/request'

const adminApi = {
  userList: '/admin/api/getUserList',
  fetchOneUser: '/admin/api/getOneUserById',
  saveUserInfo: '/admin/api/saveUserInfo',
  getUserAvatar: '/admin/api/getUserAvatar'
}

export function userList (parameter) {
  return request({
    url: adminApi.userList,
    method: 'post',
    data: parameter
  })
}

export function fetchOneUser (param) {
  return request({
    url: adminApi.fetchOneUser,
    method: 'post',
    data: param
  })
}

export function saveUserInfo (param) {
  return request({
    url: adminApi.saveUserInfo,
    method: 'post',
    data: param
  })
}

export function getUserAvatar (param) {
  return request({
    url: adminApi.getUserAvatar + '?avatarUrl=' + param,
    method: 'get',
    responseType: 'blob'
  })
}
