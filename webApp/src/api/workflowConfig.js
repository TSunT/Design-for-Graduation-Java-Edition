import request from '@/utils/request'

const workflowConfigApi = {
  getWorkFlowUserList: '/workflowConfig/getWorkFlowUserList',
  deleteOneWfUser: '/workflowConfig/deleteOneWfUser',
  addWfUser: '/workflowConfig/addWfUser',
  getWfGroupPage: '/workflowConfig/getWfGroupPage',
  deleteOneWfGroup: '/workflowConfig/deleteOneWfGroup',
  addWfGroup: '/workflowConfig/addWfGroup',
  getAllUser: '/workflowConfig/getAllUser',
  getOneGroupInfo: '/workflowConfig/getOneGroupInfo'
}

export function getWorkFlowUserList (param) {
  return request({
    url: workflowConfigApi.getWorkFlowUserList,
    method: 'post',
    data: param
  })
}

export function deleteOneWfUser (param) {
  return request({
    url: workflowConfigApi.deleteOneWfUser,
    method: 'post',
    data: param
  })
}

export function addWfUser (param) {
  return request({
    url: workflowConfigApi.addWfUser,
    method: 'post',
    data: param
  })
}

export function getWfGroupPage (param) {
  return request({
    url: workflowConfigApi.getWfGroupPage,
    method: 'post',
    data: param
  })
}

export function deleteOneWfGroup (param) {
  return request({
    url: workflowConfigApi.deleteOneWfGroup,
    method: 'post',
    data: param
  })
}

export function addWfGroup (param) {
  return request({
    url: workflowConfigApi.addWfGroup,
    method: 'post',
    data: param
  })
}

export function getAllUser () {
  return request({
    url: workflowConfigApi.getAllUser,
    method: 'post'
  })
}

export function getOneGroupInfo (param) {
  return request({
    url: workflowConfigApi.getOneGroupInfo,
    method: 'post',
    data: param
  })
}
