<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="请假类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择请假类型" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="leaveStartTime">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.leaveStartTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="leaveEndTime">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.leaveEndTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择结束时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="流程实例ID" prop="instanceId">
        <el-input
          v-model="queryParams.instanceId"
          placeholder="请输入流程实例ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['workflow:leave']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="leaveList">
      <el-table-column label="请假类型" align="center" prop="type"/>
      <el-table-column label="标题" align="center" prop="title"/>
      <el-table-column label="原因" align="center" prop="reason"/>
      <el-table-column label="开始时间" align="center" prop="leaveStartTime">
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="leaveEndTime">
      </el-table-column>
      <el-table-column label="请假时长" align="center" prop="totalTime"/>
      <el-table-column label="流程实例ID" align="center" prop="instanceId"/>
      <el-table-column label="创建人" align="center" prop="createUserName"/>
      <el-table-column label="申请人" align="center" prop="applyUserName"/>
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前任务名称" align="center" prop="taskName"/>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <div v-if="scope.row.instanceId">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="detail(scope.row)"
              v-hasPermi="['workflow:leave']"
            >表单数据
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="historyList(scope.row)"
              v-hasPermi="['workflow:leave']"
            >审批历史
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="showProcessImgDialog(scope.row)"
              v-hasPermi="['workflow:leave']"
            >进度查看
            </el-button>
            <div v-if="scope.row.taskName.indexOf('已结束') === -1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="cancelApply(scope.row)"
                v-hasPermi="['workflow:leave']"
              >撤销
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="suspendOrActiveApply(scope.row)"
                v-hasPermi="['workflow:leave']"
              >{{scope.row.suspendState=== '2' ? '激活' : '挂起'}}
              </el-button>
            </div>
          </div>
          <div v-else>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="submitApply(scope.row)"
              v-hasPermi="['workflow:leave']"
            >提交申请
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['workflow:leave']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['workflow:leave']"
            >删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改请假流程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" :disabled=readonly></el-input>
        </el-form-item>
        <el-form-item label="原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" placeholder="请输入内容" :disabled=readonly></el-input>
        </el-form-item>

        <el-form-item label="请假时间" prop="startAndEndTime">
          <el-date-picker
            style="width: 380px;height: 36px"
            v-model="startAndEndTime"
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            start-placeholder="开始日期"
            end-placeholder="结束日期" :disabled=readonly>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假时长" prop="totalTime">
          <el-input v-model="formatDateSub" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-show=!readonly>确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="审批历史" :visible.sync="dialogTableVisible">
      <el-table :data="gridData">
        <el-table-column property="activityName" label="任务名称"></el-table-column>
        <el-table-column property="title" label="visible"></el-table-column>
        <el-table-column property="field" label="处理人"></el-table-column>
        <el-table-column property="comment" label="审批意见"></el-table-column>
        <el-table-column property="startTime" label="开始时间"></el-table-column>
        <el-table-column property="endTime" label="结束时间"></el-table-column>
        <el-table-column property="durationInMillis" label="耗时"></el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog title="查看进度" :visible.sync="processImg">
      <div style="
			display: inline-block;
			height: 100%;
			width: 0;
			vertical-align: middle;
		">
        <img alt="图片加载中...." :src="src" style="vertical-align: middle;">
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listLeave, getLeave, delLeave, addLeave, updateLeave, exportLeave, submitApply} from "@/api/workflow/leave";
  import {cancelApply, suspendOrActiveApply, listHistory} from "@/api/activiti/process"
  import {calcTotalSecond, formatTotalDateSub} from "@/utils/dateUtil";

  export default {
    name: "Leave",
    data() {
      return {
        processImg: false,
        dialogTableVisible: false,
        gridData: null,
        src:"",


        startAndEndTime: ["", ""],
        readonly: false,
        start: '',
        end: '',
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 请假流程表格数据
        leaveList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          type: null,
          title: null,
          reason: null,
          leaveStartTime: null,
          leaveEndTime: null,
          totalTime: null,
          instanceId: null,
          applyUser: null,
          applyTime: null,
          realityStartTime: null,
          realityEndTime: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          title: [
            {required: true, message: "用户名称不能为空", trigger: "blur"},
          ], totalTime: [
            {required: true, message: "请假时间不能为空", trigger: "blur"},
          ], reason: [
            {required: true, message: "原因不能为空", trigger: "blur"},
          ],
        },

        formatDateSub: '',
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 历史列表 */
      historyList(row) {
        this.dialogTableVisible = true;
        listHistory(row.instanceId, this.queryParams).then(response => {
          this.gridData = response.rows;
        });
      },
      /** 查询请假流程列表 */
      getList() {
        this.loading = true;
        listLeave(this.queryParams).then(response => {
          this.leaveList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          type: null,
          title: null,
          reason: null,
          leaveStartTime: null,
          leaveEndTime: null,
          totalTime: null,
          instanceId: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          applyUser: null,
          applyTime: null,
          realityStartTime: null,
          realityEndTime: null
        };

        this.startAndEndTime = ["", ""];
        this.formatDateSub = null;
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.readonly = false;
        this.title = "添加请假流程";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getLeave(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.readonly = false;
          this.title = "修改请假流程";
        });
      },
      submitApply(row) {
        this.$confirm('确认要提交申请吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return submitApply(row.id);
        }).then(() => {
          this.getList();
          this.msgSuccess("提交成功");
        }).catch(function () {
        });
      },
      cancelApply(row) {
        this.$confirm('确认要撤销申请吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return cancelApply(row.instanceId);
        }).then(() => {
          this.getList();
          this.msgSuccess("撤销成功");
        }).catch(function () {
        });
      },
      suspendOrActiveApply(row) {
        var suspendOrActive = row.suspendState === '2' ? '激活' : '挂起';
        this.$confirm('确认要' + suspendOrActive + '申请吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          const data = {"instanceId": row.instanceId, "suspendState": row.suspendState};
          return suspendOrActiveApply(data);
        }).then(() => {
          this.getList();
          this.msgSuccess("撤销成功");
        }).catch(function () {
        });
      },
      /** 表单数据展示 */
      detail(row) {
        this.reset();
        const id = row.id || this.ids
        getLeave(id).then(response => {
          this.form = response.data;
          this.startAndEndTime = [this.form.leaveStartTime, this.form.leaveEndTime]
          this.open = true;
          this.readonly = true;
          this.title = "修改请假流程";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateLeave(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addLeave(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除请假流程编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delLeave(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有请假流程数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportLeave(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      showProcessImgDialog(row){
        this.src=null;
        this.processImg=true;
        this.src=process.env.VUE_APP_BASE_API+'/process/read-resource?pProcessInstanceId=' + row.instanceId;
      }
    },
    watch: {
      startAndEndTime: {
        handler(newValue) {
          this.form.leaveStartTime = newValue[0];
          this.form.leaveEndTime = newValue[1];
          if (newValue[0] && newValue[1]) {
            this.form.totalTime = calcTotalSecond(newValue[0], newValue[1]);
            this.formatDateSub = formatTotalDateSub(this.form.totalTime);
          }

        },
        deep: true
      }
    }
  }
  ;
</script>
