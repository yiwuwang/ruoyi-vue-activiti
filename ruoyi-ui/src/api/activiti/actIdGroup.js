import request from '@/utils/request'

// 查询流程用户组列表
export function listActIdGroup(query) {
  return request({
    url: '/activiti/actIdGroup/list',
    method: 'get',
    params: query
  })
}
