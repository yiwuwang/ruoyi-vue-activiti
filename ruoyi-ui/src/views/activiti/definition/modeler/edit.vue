<template>
  <div class="containers vue-bpmn-diagram-container">
    <div class="content with-diagram" ref="content">
      <div class="message error">
        <div class="note">
          <p>无法显示bpms2.0</p>
          <div class="details">
            <span>错误详细信息</span>
            <pre></pre>
          </div>
        </div>
      </div>
      <div class="canvas" id="js-canvas" ref="canvas"></div>
      <div class="properties-panel-parent" id="js-properties-panel" v-show="showEditor"></div>
    </div>
    <ul class="buttons" v-show="showEditor">
      <li class="item upload">
        <form name="myForm" onsubmit="return false" method="post" enctype="multipart/form-data" title="上传文件">
          <input type="file" id="uploadFile" accept=".bpmn" style="display: none" v-on:change="importFile">
          <label class="label" for="uploadFile">导入</label>
        </form>
      </li>
      <li class="item download">
        <a class="btn" @click="downloadBpmn()">导出</a>
      </li>
      <li class="item submit">
        <a class="btn" id="js-download-diagram" @click="saveAndDeploy()">
          保存并部署
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
  import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css'
  import propertiesPanelModule from 'bpmn-js-properties-panel'
  // import propertiesPanelModule from './resources/properties-panel'
  import propertiesProviderModule from './resources/properties-panel/provider/activiti/index'
  import activitiModuleDescriptor from './resources/activiti.json'
  // import propertiesProviderModule from 'activiti-bpmn-moddle/lib'
  // import activitiModuleDescriptor from 'activiti-bpmn-moddle/resources/activiti.json'
  import customTranslate from './resources/customTranslate/customTranslate'
  import customControlsModule from './resources/customControls'
  import BpmnModeler from 'bpmn-js/lib/Modeler'
  import {addDeploymentByString, getDefinitionXML, gethighLine} from '@/api/activiti/definition'

  export default {
    name: 'vue-bpmn',
    data: function() {
      return {
        showEditor: true,
        diagramXML: null,
        uploadFile: null,
      }
    },
    mounted: function() {
      const self = this
      this.container = this.$refs.content
      const canvas = this.$refs.canvas
      const customTranslateModule = { translate: ['value', customTranslate] }
      if (self.$route.query.type === 'lookBpmn') {
        // 查看进度
        this.bpmnModeler = new BpmnModeler({
          container: canvas, keyboard: { bindTo: document },
          additionalModules: [
            {
              zoomScroll: ["value", ""],// 禁用滚轮滚动
              bendpoints: ["value", ""], // 禁止拖动线
              paletteProvider: ["value", ""], // 禁用左侧面板
              contextPadProvider: ["value", ""], // 禁止点击节点出现contextPad
              labelEditingProvider: ["value", ""] // 禁止双击节点出现label编辑框
            },
            customTranslateModule
          ]
        })
        const id = self.$route.query.deploymentFileUUID || '6d4af2dc-bab0-11ea-b584-3cf011eaafca'
        const name = self.$route.query.deploymentName || 'String.bpmn'
        const param = { 'deploymentId': id, 'resourceName': decodeURI(name) }
        this.fetchDiagram(param)
        this.showEditor = false;

        const instanceId = self.$route.query.instanceId;
        gethighLine({instanceId})
          .then(result => {
            console.log(result)
            var ColorJson = self.getByColor(result.data)
            setTimeout(function() {
              for (var i in ColorJson) {
                self.setColor(ColorJson[i], self.bpmnModeler)
              }
            }, 200)
          })
          .catch(reason => {
            console.log(reason)
          })
      }else {
        this.bpmnModeler = new BpmnModeler({
          container: canvas,
          keyboard: { bindTo: document },
          //添加控制板
          propertiesPanel: {
            parent: '#js-properties-panel'
          },
          additionalModules: [
            propertiesPanelModule,
            propertiesProviderModule,
            customControlsModule,
            customTranslateModule
          ],
          moddleExtensions: {
            activiti: activitiModuleDescriptor
          }
        })
        if (self.$route.query.type === 'addBpmn'){
          self.diagramXML = '<?xml version="1.0" encoding="UTF-8"?>\n' +
            '<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd" id="sample-diagram" targetNamespace="http://activiti.org/bpmn">\n' +
            '  <bpmn2:process id="Process_1" isExecutable="true">\n' +
            '    <bpmn2:startEvent id="StartEvent_1"/>\n' +
            '  </bpmn2:process>\n' +
            '  <bpmndi:BPMNDiagram id="BPMNDiagram_1">\n' +
            '    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">\n' +
            '      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">\n' +
            '        <dc:Bounds height="36.0" width="36.0" x="412.0" y="240.0"/>\n' +
            '      </bpmndi:BPMNShape>\n' +
            '    </bpmndi:BPMNPlane>\n' +
            '  </bpmndi:BPMNDiagram>\n' +
            '</bpmn2:definitions>'
        }else if (self.$route.query.type === 'editBpmn'){
          const id = self.$route.query.deploymentFileUUID || '6d4af2dc-bab0-11ea-b584-3cf011eaafca'
          const name = self.$route.query.deploymentName || 'String.bpmn'
          const param = { 'deploymentId': id, 'resourceName': decodeURI(name) }
          this.fetchDiagram(param)
        }
      }

      this.bpmnModeler.on('import.done', function(event) {
        var error = event.error
        var warnings = event.warnings
        if (error) {
          self.$emit('error', error)
        } else {
          self.$emit('shown', warnings)
        }
        self.bpmnModeler.get('canvas').zoom('fit-viewport')
      })
    },
    beforeDestroy: function() {
      this.bpmnModeler.destroy()
    },
    watch: {
      diagramXML: function(val) {
        this.bpmnModeler.importXML(val)
      }
    },
    methods: {
      async createDiagram(xml, bpmnModeler, container) {
        try {
          await bpmnModeler.importXML(xml);
          container.removeClass('with-error').addClass('with-diagram');
        } catch (err) {
          container.removeClass('with-diagram').addClass('with-error');
          container.find('.error pre').text(err.message);
          console.error(err);
        }
      },
      setColor(json, bpmnModeler) {
        var modeling = bpmnModeler.get('modeling')
        var elementRegistry = bpmnModeler.get('elementRegistry')
        var elementToColor = elementRegistry.get(json.name)
        if (elementToColor) {
          modeling.setColor([elementToColor], {
            stroke: json.stroke,
            fill: json.fill
          })
        }
      },
      getByColor(data) {
        var ColorJson = []
        for (var k in data['highLine']) {
          var par = {
            'name': data['highLine'][k],
            'stroke': 'green',
            'fill': 'green'
          }
          ColorJson.push(par)
        }
        for (var k in data['highPoint']) {
          var par = {
            'name': data['highPoint'][k],
            'stroke': 'gray',
            'fill': '#eae9e9'

          }
          ColorJson.push(par)
        }
        for (var k in data['iDo']) {
          var par = {
            'name': data['iDo'][k],
            'stroke': 'green',
            'fill': '#a3d68e'
          }
          ColorJson.push(par)
        }
        for (var k in data['waitingToDo']) {
          var par = {
            'name': data['waitingToDo'][k],
            'stroke': 'green',
            'fill': 'yellow'
          }
          ColorJson.push(par)
        }
        return ColorJson
      },
      downloadBpmn: function() {
        const self = this
        this.bpmnModeler.saveXML({ format: true })
          .then(xml => {
            self.download('diagram.bpmn', xml.xml)
          })
          .catch(err => {
            return console.error('could not save BPMN 2.0 diagram', err)
          })
      },
      importFile: function(event) {
        // 从磁盘读取
        event.target.files[0].text().then(readXml => {
          // 进行覆盖
          this.diagramXML = readXml
        })
      },
      fetchDiagram: function(param) {
        var self = this
        getDefinitionXML(param)
          .then(function(response) {
            self.diagramXML = response
          })
          .catch(function(err) {
            self.$emit('error', err)
          })
      },
      download: function(filename, text) {
        console.log(text);
        let pom = document.createElement('a')
        pom.setAttribute('href', 'data:application/bpmn20-xml;charset=UTF-8,' + encodeURIComponent(text))
        pom.setAttribute('download', filename)
        if (document.createEvent) {
          let event = document.createEvent('MouseEvents')
          event.initEvent('click', true, true)
          pom.dispatchEvent(event)
        } else {
          pom.click()
        }
        pom.remove()
      },
      saveAndDeploy(){
        const self = this;
        this.$confirm('确认保存并部署模型, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.bpmnModeler.saveXML({ format: true }, function(err, xml) {
            if (err) {
              self.$message({
                type: 'error',
                message: '保存失败，请重试!'
              });
              return console.error('保存失败，请重试', err)
            }
            // 创建新的
            addDeploymentByString({ 'stringBPMN': xml }).then(result => {
              if (result.code === 200) {
                self.$message({
                  type: 'success', message: '操作成功!', onClose: instance => {
                    //父窗口强行公开的函数
                    parent.setModelVisible(false)
                  }
                })
              } else {
                self.$message({type: 'error', message: result.msg});
              }
            }).catch(err => {
              self.$message({type: 'error', message: err});
            })
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          });
        });
      },
    }
  }
</script>
<style src="./edit.css"></style>
<style src="./vendor/diagram-js.css"></style>
<style src="./vendor/bpmn-font/css/bpmn-embedded.css"></style>
<style scoped>
  .containers {
    height: 100%;
    width: 100%;
  }

  .item {
    display: none;
    cursor: pointer;
  }

  .bjs-powered-by {
    display: none;
  }

  /* line 24, styles/app.less */
  .content {
    position: relative;
    width: 100%;
    height: 100%;
  }

  /* line 29, styles/app.less */
  .content > .message {
    width: 100%;
    height: 100%;
    text-align: center;
    display: table;
    font-size: 16px;
    color: #111;
  }

  /* line 38, styles/app.less */
  .content > .message .note {
    vertical-align: middle;
    text-align: center;
    display: table-cell;
  }

  /* line 45, styles/app.less */
  .content > .message.error .details {
    max-width: 500px;
    font-size: 12px;
    margin: 20px auto;
    text-align: left;
    color: #BD2828;
  }

  /* line 53, styles/app.less */
  .content > .message.error pre {
    border: solid 1px #BD2828;
    background: #fefafa;
    padding: 10px;
    color: #BD2828;
  }

  /* line 61, styles/app.less */
  .content:not(.with-error) .error,
  .content.with-error .intro,
  .content.with-diagram .intro {
    display: none;
  }

  /* line 67, styles/app.less */
  .content .canvas {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }

  /* line 75, styles/app.less */
  .content .canvas,
  .content .properties-panel-parent {
    display: none;
  }

  /* line 81, styles/app.less */
  .content.with-diagram .canvas,
  .content.with-diagram .properties-panel-parent {
    display: block;
  }

  /* line 89, styles/app.less */
  .buttons {
    position: fixed;
    bottom: 20px;
    left: 20px;
    padding: 0;
    margin: 0;
    list-style: none;
  }

  /* line 98, styles/app.less */
  .buttons > li {
    display: inline-block;
    margin-right: 10px;
  }

  /* line 102, styles/app.less */
  .buttons > li > a {
    background: #DDD;
    border: solid 1px #666;
    display: inline-block;
    padding: 5px;
  }

  /* line 110, styles/app.less */
  .buttons a {
    opacity: 0.3;
  }

  /* line 114, styles/app.less */
  .buttons a.active {
    opacity: 1;
  }

  .buttons > li {
    /* display: inline-block; */
    margin-right: 10px;
    height: 26px;
    line-height: 26px;
    float: left;
  }

  .buttons > li > a.btn {
    background: #00BCD4;
    border: none;
    outline: none;
    padding: 0px 10px;
    color: #fff;
    display: inline-block;
    opacity: 1;
    height: 26px;
    font-size: 14px;
    line-height: 26px;
  }

  /* line 119, styles/app.less */
  .properties-panel-parent {
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    width: 260px;
    z-index: 10;
    border-left: 1px solid #ccc;
    overflow: auto;
  }

  /* line 128, styles/app.less */
  .properties-panel-parent:empty {
    display: none;
  }

  /* line 131, styles/app.less */
  .properties-panel-parent > .djs-properties-panel {
    padding-bottom: 70px;
    min-height: 100%;
  }

  .label {
    background: #00BCD4;
    border: none;
    outline: none;
    padding: 0px 10px;
    color: #fff;
    display: inline-block;
    cursor: pointer;
    opacity: 1;
    height: 26px;
    font-size: 14px;
    line-height: 26px;
  }

  .sy-mask {
    width: 100%;
    height: 100%;
    position: fixed;
    background: rgba(0, 0, 0, 0.8);
    left: 0;
    top: 0;
    z-index: 1000;
    display: none;
  }

  .sy-alert {
    position: fixed;
    display: none;
    background: #fff;
    border-radius: 5px;
    overflow: hidden;
    width: 300px;
    max-width: 90%;
    max-height: 80%;
    left: 0;
    right: 0;
    margin: 0 auto;
    z-index: 9999
  }

  .sy-alert.animated {
    -webkit-animation-duration: .3s;
    animation-duration: .3s
  }

  .sy-alert .sy-title {
    height: 45px;
    color: #333;
    line-height: 45px;
    font-size: 15px;
    border-bottom: 1px solid #eee;
    padding: 0 12px
  }

  .sy-alert .sy-content {
    padding: 20px;
    text-align: center;
    font-size: 14px;
    line-height: 24px;
    color: #666;
    overflow-y: auto
  }

  .sy-alert .sy-btn {
    height: 50%;
    border-top: 1px solid #eee;
    overflow: hidden
  }

  .sy-alert .sy-btn button {
    float: left;
    border: 0;
    color: #333;
    cursor: pointer;
    background: #fff;
    width: 50%;
    line-height: 45px;
    font-size: 15px;
    text-align: center
  }

  .sy-alert .sy-btn button:nth-child(1) {
    color: #888;
    border-right: 1px solid #eee
  }

  .sy-alert.sy-alert-alert .sy-btn button {
    float: none;
    width: 100%
  }

  .sy-alert.sy-alert-tips {
    text-align: center;
    width: 150px;
    background: rgba(0, 0, 0, 0.7)
  }

  .sy-alert.sy-alert-tips .sy-content {
    padding: 8px;
    color: #fff;
    font-size: 14px
  }

  .sy-alert.sy-alert-model .sy-content {
    text-align: left
  }

  .sy-alert.sy-alert-model .sy-content .form .input-item {
    margin-bottom: 12px;
    position: relative
  }

  .sy-alert.sy-alert-model .sy-content .form .input-item input {
    display: block;
    position: relative;
    width: 100%;
    border: 1px solid #eee;
    padding: 10px
  }

  .sy-alert.sy-alert-model .sy-content .form .input-item .getcode {
    border: 0;
    top: 0;
    right: 0;
    position: absolute;
    background: 0;
    line-height: 37px;
    color: #f60;
    width: 100px;
    text-align: center
  }
</style>
