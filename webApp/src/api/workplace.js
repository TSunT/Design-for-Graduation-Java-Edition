import request from '@/utils/request'

const workplaceApi = {
  getDepNewsByUser: '/workplace/getDepNewsByUser',
  getOneDepNewsById: '/workplace/getOneDepNewsById'
}

export function getDepNewsByUser (param) {
  return request({
    url: workplaceApi.getDepNewsByUser,
    method: 'post',
    data: param
  })
}

export function getOneDepNewsById (param) {
  return request({
    url: workplaceApi.getOneDepNewsById,
    method: 'post',
    data: param
  })
}
