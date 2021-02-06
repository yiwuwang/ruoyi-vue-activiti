import request from '@/utils/request'
// 查询请假详细
export function historyFromData(instanceId) {
  return request({
    url: '/historyFromData/ByInstanceId/' + instanceId,
    method: 'get'
  })
}
