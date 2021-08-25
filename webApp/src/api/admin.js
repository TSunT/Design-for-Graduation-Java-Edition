import request from '@/utils/request'

const adminApi = {
  userList: '/admin/api/getUserList',
  fetchOneUser: '/admin/api/getOneUserById'
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
