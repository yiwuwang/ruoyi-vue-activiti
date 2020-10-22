import request from '@/utils/request'

// 查询流程用户列表
export function listActIdUser(query) {
  return request({
    url: '/activiti/actIdUser/list',
    method: 'get',
    params: query
  })
}
