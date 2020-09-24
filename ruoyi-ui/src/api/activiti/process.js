import request from '@/utils/request'

// 撤销请假流程
export function cancelApply(instanceId) {
  return request({
    url: '/process/cancelApply/' + instanceId,
    method: 'post'
  })
}
// 激活挂起
export function suspendOrActiveApply(data) {
  return request({
    url: '/process/suspendOrActiveApply',
    method: 'post',
    data: data
  })
}
// 历史列表
export function listHistory(instanceId) {

  return request({
    url: '/process/listHistory/'+instanceId,
    method: 'get'
  })
}
// 查询请假流程详细
export function showVerifyDialog(id) {
  return request({
    url: '/process/showVerifyDialog/' + id,
    method: 'get'
  })
}

// 完成待办
export function complete(data) {
  return request({
    url: '/process/complete',
    method: 'post',
    data: data
  })
}
