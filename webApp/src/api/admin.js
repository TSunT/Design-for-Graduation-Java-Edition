import request from '@/utils/request'

const adminApi = {
  userList: '/admin/api/getUserList',
  fetchOneUser: '/admin/api/getOneUserById',
  saveUserInfo: '/admin/api/saveUserInfo',
  getUserAvatar: '/admin/api/getUserAvatar',
  getAllRoles: '/admin/api/getAllRoles',
  getRolesList: '/admin/api/getRoleList',
  getAllMenus: '/admin/api/getAllMenus',
  getOneRoleSelectedMenu: '/admin/api/getOneRoleSelectedMenu',
  saveRoleMenus: '/admin/api/saveRoleMenus'
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

export function getAllRoles () {
  return request({
    url: adminApi.getAllRoles,
    method: 'post'
  })
}

export function getRolesList (params) {
  return request({
    url: adminApi.getRolesList,
    method: 'post',
    data: params
  })
}

export function getAllMenus () {
  return request({
    url: adminApi.getAllMenus,
    method: 'post'
  })
}

export function getOneRoleSelectedMenu (params) {
  return request({
    url: adminApi.getOneRoleSelectedMenu,
    method: 'post',
    data: params
  })
}

export function saveRoleMenus (params) {
  return request({
    url: adminApi.saveRoleMenus,
    method: 'post',
    data: params
  })
}
