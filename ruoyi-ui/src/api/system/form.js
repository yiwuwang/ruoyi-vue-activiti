import request from '@/utils/request'

// 查询自定义表单列表
export function listForm(query) {
  return request({
    url: '/system/form/list',
    method: 'get',
    params: query
  })
}

// 查询自定义表单详细
export function getForm(id) {
  return request({
    url: '/system/form/' + id,
    method: 'get'
  })
}

// 新增自定义表单
export function addForm(data) {
  return request({
    url: '/system/form',
    method: 'post',
    data: data
  })
}

// 修改自定义表单
export function updateForm(data) {
  return request({
    url: '/system/form',
    method: 'put',
    data: data
  })
}

// 删除自定义表单
export function delForm(id) {
  return request({
    url: '/system/form/' + id,
    method: 'delete'
  })
}

// 导出自定义表单
export function exportForm(query) {
  return request({
    url: '/system/form/export',
    method: 'get',
    params: query
  })
}