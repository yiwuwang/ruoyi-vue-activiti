import request from '@/utils/request'

// 查询请假流程列表
export function listLeave(query) {
  return request({
    url: '/workflow/leave/list',
    method: 'get',
    params: query
  })
}

// 查询请假流程待办列表
export function taskList(query) {
  return request({
    url: '/workflow/leave/taskList',
    method: 'get',
    params: query
  })
}// 查询请假流程已办列表
export function taskDoneList(query) {
  return request({
    url: '/workflow/leave/taskDoneList',
    method: 'get',
    params: query
  })
}

// 查询请假流程详细
export function getLeave(id) {
  return request({
    url: '/workflow/leave/' + id,
    method: 'get'
  })
}

// 提交请假流程
export function submitApply(id) {
  return request({
    url: '/workflow/leave/submitApply/' + id,
    method: 'post'
  })
}

// 新增请假流程
export function addLeave(data) {
  return request({
    url: '/workflow/leave',
    method: 'post',
    data: data
  })
}

// 修改请假流程
export function updateLeave(data) {
  return request({
    url: '/workflow/leave',
    method: 'put',
    data: data
  })
}

// 删除请假流程
export function delLeave(id) {
  return request({
    url: '/workflow/leave/' + id,
    method: 'delete'
  })
}

// 导出请假流程
export function exportLeave(query) {
  return request({
    url: '/workflow/leave/export',
    method: 'get',
    params: query
  })
}
