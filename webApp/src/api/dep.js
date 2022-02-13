import request from '@/utils/request'

const depApi = {
  getDepPage: '/dep/getDepPageList'
}

export function getDepPage (parameter) {
  return request({
    url: depApi.getDepPage,
    method: 'post',
    data: parameter
  })
}
