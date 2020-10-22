<template>
  <div>
    <el-table :data="gridData">
      <el-table-column property="activityName" label="任务名称"></el-table-column>
      <el-table-column property="assigneeName" label="处理人"></el-table-column>
      <el-table-column property="comment" label="审批意见"></el-table-column>
      <el-table-column property="startTime" label="开始时间"></el-table-column>
      <el-table-column property="endTime" label="结束时间"></el-table-column>
      <el-table-column property="durationInMillis" label="耗时"></el-table-column>
    </el-table>
  </div>
</template>

<script>
  import { listHistory } from '@/api/activiti/process'

  export default {
    props: ['instanceId'],
    name: 'index',
    data() {
      return {
        //审批历史
        gridData: null
      }
    },
    methods: {
      /** 历史列表 */
      historyList(instance) {
        listHistory(instance).then(response => {
          this.gridData = response.rows
        })
      }
    },
    watch: {
      instanceId: {
        immediate: true,
        handler(newVal) {
          if (newVal) {
            this.historyList(newVal)
          }else {
            this.gridData=null;
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>
