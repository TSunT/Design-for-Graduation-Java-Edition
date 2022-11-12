import request from '@/utils/request'

const workflowTaskApi = {
  myWaitApproveTasks: '/workflow/task/myWaitApproveTasks',
  allApprovedTasks: '/workflow/task/allApprovedTasks',
  myApprovedTasks: '/workflow/task/myApprovedTasks',
  myCandidateApproveTasks: '/workflow/task/myCandidateApproveTasks'
}

export function myWaitApproveTasks (param) {
  return request({
    url: workflowTaskApi.myWaitApproveTasks,
    method: 'post',
    data: param
  })
}

export function allApprovedTasks (param) {
  return request({
    url: workflowTaskApi.allApprovedTasks,
    method: 'post',
    data: param
  })
}

export function myApprovedTasks (param) {
  return request({
    url: workflowTaskApi.myApprovedTasks,
    method: 'post',
    data: param
  })
}

export function myCandidateApproveTasks (param) {
  return request({
    url: workflowTaskApi.myCandidateApproveTasks,
    method: 'post',
    data: param
  })
}
