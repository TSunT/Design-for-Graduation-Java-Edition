import request from '@/utils/request'

const depApi = {
  getDepPage: '/dep/getDepPageList',
  getONeDepProfile: '/dep/getOneDepById',
  getDepForSearchParentNode: '/dep/getDepForSearchParentNode',
  getDepNewsPage: '/dep/getDepNewsPage',
  getOneNewsById: '/dep/getOneNewsById',
  saveOneDepNews: '/dep/saveOneDepNews',
  getListByParentId: '/dep/getListByParentId',
  saveOneDep: '/dep/saveOneDep'
}

export function getDepPage (parameter) {
  return request({
    url: depApi.getDepPage,
    method: 'post',
    data: parameter
  })
}

export function getOneDepProfile (parameter) {
  return request({
    url: depApi.getONeDepProfile,
    method: 'post',
    data: parameter
  })
}

export function getDepForSearchParentNode (name) {
  return request({
    url: depApi.getDepForSearchParentNode,
    method: 'post',
    data: { name: name }
  })
}

export function getDepNewsPage (parameter) {
  return request({
    url: depApi.getDepNewsPage,
    method: 'post',
    data: parameter
  })
}

export function saveOneDepNews (paramter) {
  return request({
    url: depApi.saveOneDepNews,
    method: 'post',
    data: paramter
  })
}

export function getOneNewsById (param) {
  return request({
    url: depApi.getOneNewsById,
    method: 'post',
    data: param
  })
}

export function getListByParentId (param) {
  return request({
    url: depApi.getListByParentId,
    method: 'post',
    data: param
  })
}

export function saveOneDep (param) {
  return request({
    url: depApi.saveOneDep,
    method: 'post',
    data: param
  })
}
