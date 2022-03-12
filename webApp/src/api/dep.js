import request from '@/utils/request'

const depApi = {
  getDepPage: '/dep/getDepPageList',
  getONeDepProfile: '/dep/getOneDepById',
  getDepForSearchParentNode: '/dep/getDepForSearchParentNode',
  getDepNewsPage: '/dep/getDepNewsPage'
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
