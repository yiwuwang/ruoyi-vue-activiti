import request from '@/utils/request'

// 查询modeler列表
export function listModeler(query) {
  return request({
    url: '/modeler/list',
    method: 'get',
    params: query
  })
}



// 新增Modeler
export function addModeler(data) {
  return request({
    url: '/modeler/create',
    method: 'post',
    data: data
  })
}


// 删除Modeler
export function delModeler(id) {
  console.log('/modeler/remove/' + id)
  return request({
    url: '/modeler/remove/' + id,
    method: 'delete'
  })
}

// 导出Modeler
export function exportModeler(id) {
  return request({
    url: '/modeler/export/'+id,
    method: 'get'
  })
}

// 配置Modeler
export function deployModeler(modelId) {
  return request({
    url: '/modeler/deploy/'+modelId,
    method: 'get'
  })
}
