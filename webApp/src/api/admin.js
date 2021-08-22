import request from '@/utils/request'

const adminApi = {
  userList: '/admin/api/getUserList'
}

export function userList (parameter) {
  return request({
    url: adminApi.userList,
    method: 'post',
    data: parameter
  })
}
