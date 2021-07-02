## 平台简介

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、activiti、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。
* 感谢[RuoYi-Vue](https://github.com/yangzongzhuan/RuoYi-Vue)。
* 华为云优惠券：[点我进入](https://www.huaweicloud.com/product/ecs.html?fromacct=2b023ba5-33f5-478c-bbdf-33e27cd7459c&utm_source=V1g3MDY4NTY=&utm_medium=cps&utm_campaign=201905)
* 阿里云优惠券：[点我进入](https://promotion.aliyun.com/ntms/yunparter/invite.html?userCode=ek76b2lk)

#### 部署文档与ruoyi-vue一致:https://doc.ruoyi.vip/ruoyi-vue/
#### 部署文档与ruoyi-vue一致:https://doc.ruoyi.vip/ruoyi-vue/
#### 部署文档与ruoyi-vue一致:https://doc.ruoyi.vip/ruoyi-vue/

#### 重要的事情说三遍
>  参考资料👇
>
>  1. 若依框架: [http://www.ruoyi.vip](http://www.ruoyi.vip/)
>  2. 咖啡兔：[《Activiti 实战》](https://github.com/henryyan/activiti-in-action-codes)

## 最新更新
<p>随手 star ⭐是一种美德。 你们的star就是我的动力</p>

v2.4

由于使用自定义表单之后与页面绑定，在APP端与其他页面无法进行审批，失去了前后端分离的意义
所以2.4版本回退到2.2版本
后续更近会继续使用原先动态表单架构。
春节期间代码改动会很大，谢谢大家的支持，提前祝大家新年快乐。


v2.2
1. 添加任务流程图展示
2. 修改请请假流程默认排序
3. 修改动态表单默认排序
4. 修改动态表展示单审批人nick_name(之前是user_name)
5. act_workflow_formdata表添加create_name字段

近期任务：
1. 优化动态表单页面
2. 添加请假流程添加附件（使用华为云OBS存储）
3. 添加销假流程并使用
4. 添加通知任务监听器
5. 超时提醒-定时事件

v2.1
1. 修改已知BUG
2. 添加审批未通过修改请假流程
3. 修改act_workflow_formdata数据表字段



v2.0

1. activiti升级到7.1.0.M4
2. ruoyi-vue升级到3.2.0
3. 流程设计器更改为bpmn.js
4. 添加请假部门领导审批，公司人事审批流程
5. 使用动态表单构建


v1.1
1. 优化前端代码
2. ruoyi-vue升级到3.1.0  详情请看ruoyi-vue官网

v1.0

1.  新增请假流程：请假会签，支持请假列表、新增暂存、编辑、删除；提交申请 (含选择会签参与人)、表单数据、我的待办 (申请详情、审批、调整申请、销假)、我的已办。
2.  在线绘图部分界面汉化。
3.  新增流程通用接口：审批历史和进度查看。
4.  Activiti Modeler 完全汉化。
5.  流程实例支持挂起和激活、撤销。
6.  新增流程定义功能，支持流程定义部署、列表、删除和导出。
7.  新增在线绘图功能，支持拖拽预览、在线绘图、下载 BPMN 文件和 SVG 文件。



### 测试账号

> 流程管理账号：admin / admin123
>
> 请假流程测试账号
>
> 普通员工1：guanxing / 123456
>
> 普通员工2：zhoucang / 123456
>
> 部门领导1：guanyu / 123456
>
> 部门领导2：zhaoyun / 123456
>
> 人事：zhugeliang / 123456

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 在线体验

- admin/admin123

演示地址：http://demo.ruoyichina.com/login

## 演示图

<table>
    <tr>
        <td><img src="http://www.ltdigit.cn/img/1600223031.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/1cbcf0e6f257c7d3a063c0e3f2ff989e4b3.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8074972883b5ba0622e13246738ebba237a.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-9f88719cdfca9af2e58b352a20e23d43b12.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-39bf2584ec3a529b0d5a3b70d15c9b37646.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-936ec82d1f4872e1bc980927654b6007307.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-b2d62ceb95d2dd9b3fbe157bb70d26001e9.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d67451d308b7a79ad6819723396f7c3d77a.png"/></td>
    </tr>	 
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/5e8c387724954459291aafd5eb52b456f53.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/644e78da53c2e92a95dfda4f76e6d117c4b.jpg"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8370a0d02977eebf6dbf854c8450293c937.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-49003ed83f60f633e7153609a53a2b644f7.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d4fe726319ece268d4746602c39cffc0621.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-c195234bbcd30be6927f037a6755e6ab69c.png"/></td>
    </tr>

</table>


## 若依前后端分离工作流交流群

QQ群1：  [![加入QQ群](https://img.shields.io/badge/815390100-blue.svg)](https://qm.qq.com/cgi-bin/qm/qr?k=nrV3xrlurpfJp-SNd1TrUr__S8AzgrPH&jump_from=webapi") 815390100 点击按钮入群。
QQ群2：  [![加入QQ群](https://img.shields.io/badge/1030614591-blue.svg)](https://qm.qq.com/cgi-bin/qm/qr?k=eseCOPaRhBa07elfzLiiB6yeJuBLmrNF&jump_from=webapi") 1030614591 点击按钮入群。