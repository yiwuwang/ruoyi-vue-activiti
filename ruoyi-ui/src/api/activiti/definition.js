import request from '@/utils/request'

// 查询modeler列表
export function listDefinition(query) {
  return request({
    url: '/definition/list',
    method: 'get',
    params: query
  })
}



// 转换流程定义为模型
export function convert2Model(data) {
  return request({
    url: '/definition/convert2Model',
    method: 'post',
    data:data
  })
}
// 挂起激活转换
export function suspendOrActiveApply(data) {
  return request({
    url: '/definition/suspendOrActiveApply',
    method: 'post',
    data:data
  })
}


// 删除Modeler
export function delDefinition(id) {
  return request({
    url: '/definition/remove/' + id,
    method: 'delete'
  })
}

// 导出Modeler
// export function exportModeler(id) {
//   return request({
//     url: '/modeler/export/'+id,
//     method: 'get'
//   })
// }

