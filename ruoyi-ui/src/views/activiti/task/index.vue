<template>
  <div class="app-container">


    <el-table v-loading="loading" :data="tastList">
      <!--      <el-table-column label="流程ID" align="center" prop="id"/>-->
      <el-table-column label="流程名称" align="center" prop="instanceName"/>
      <el-table-column label="任务节点名称" align="center" prop="name"/>
      <el-table-column label="任务状态" align="center" prop="status"/>
      <el-table-column label="办理人" align="center" prop="assignee"/>
      <el-table-column label="创建时间" align="center" prop="createdDate"/>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="examineAndApprove (scope.row)"
            v-hasPermi="['workflow:leave:edit']"
          >审批
          </el-button>
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

    <!-- 审批对话框 -->
    <el-dialog :title="title" :visible.sync="open" v-if="open" width="500px" append-to-body>
      <leaveHistoryForm :businessKey="businessKey" v-if="'leave'==definitionKey"/>

      <h2>您的审批：</h2>
      <el-form :model="form" ref="form" label-width="100px" class="demo-dynamic">
        <div
          v-for="(domain, index) in form.formData"
          :label="domain.controlLable"
          :key="index"
        >
          <parser ref="formCustom" v-if="'custom'==domain.type" :form-conf="domain.json" />
          <div v-if="'str'==domain.type">
            <el-form-item>
              <el-radio-group v-model="domain.controlValue" v-if="'radio'==domain.controlType">
                <el-radio v-for="(defaults,indexd) in domain.controlDefault.split('--__--')"
                          :label=indexd
                          :key="indexd"
                >{{defaults}}

                </el-radio>

              </el-radio-group>
              <el-input type="textarea" v-model="domain.controlValue" v-if="'textarea'==domain.controlType"
              ></el-input>
            </el-form-item>
          </div>

        </div>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Parser from 'form-gen-parser';
  import {listTask, formDataShow, formDataSave} from "@/api/activiti/task";
  import leaveHistoryForm from "@/views/workflow/leave/leaveHistoryForm";

  export default {
    name: "Leave",
    components: {leaveHistoryForm, Parser},
    data() {
      return {
        id:'',
        definitionKey: '',
        businessKey: '',
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
        // 请假表格数据
        tastList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
        },
        // 表单参数
        form: {
          formData:[]
        },
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询请假列表 */
      getList() {
        this.loading = true;
        listTask(this.queryParams).then(response => {
          this.tastList = response.rows;
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
        this.definitionKey = '',
          this.businessKey = '',
          this.form = {
            formData:[],
          };
        this.resetForm("form");
      },

      /** 审批按钮操作 */
      examineAndApprove(row) {
        this.reset()
        this.definitionKey = row.definitionKey
        this.businessKey = row.businessKey
        this.id = row.id
        formDataShow(row.id).then(response => {
          let formData = []
          for (let oncekey in response.data) {
            let datas = response.data[oncekey]
            if (datas) {
              for (let i = 0; i < datas.length; i++) {
                let once = datas[i]
                if (/^{/.test(once)) {
                  formData.push({
                    type: 'custom',
                    json: JSON.parse(once)
                  })
                } else {
                  // 自定义表单
                  // FormProperty_3qipis2--__!!radio--__!!审批意见--__!!i--__!!同意--__--不同意
                  // FormProperty_0lffpcm--__!!textarea--__!!批注--__!!f--__!!null
                  let strings = once.split('--__!!')
                  let controlValue = null
                  let controlDefault = null
                  switch (strings[1]) {
                    case 'radio':
                      controlValue = 0
                      controlDefault = strings[4]
                      break
                    // default:
                  }
                  formData.push({
                    type: 'str',
                    controlId: strings[0],
                    controlType: strings[1],
                    controlLable: strings[2],
                    controlIsParam: strings[3],
                    controlValue: controlValue,
                    controlDefault: controlDefault
                  })
                }
              }
            }
          }

          this.form.formData = formData
          this.open = true
          this.title = '审批'
        })
      },
      /** 提交按钮 */
      submitForm() {
        let custFormRef = this.$refs.formCustom[0];
        let custForm = custFormRef.$refs[custFormRef.formConf.formRef];
        let custData = custFormRef[custFormRef.formConf.formModel];
        let custDesc = {};
        for (let onceForm of this.form.formData){
          for (let onceField of onceForm.json.fields){
            custDesc[onceField.__vModel__] = onceField.__config__.label;
          }
        }
        let params = { data: custData, desc: custDesc }
        // 自定义表单的核验
        custForm.validate()
          .then(e => {
            if (e){
              formDataSave(this.id, params).then(response => {
                this.msgSuccess('审批成功')
                this.open = false
                this.getList()
              })
            }
          })

      },
    }
  };
</script>
