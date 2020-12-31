create table sys_custom_form
(
    id bigint auto_increment,
    form_name varchar(50) null,
    desc_text text null,
    form_key varchar(30) not null,
    form_json longtext null,
    constraint sys_custom_form_pk
        primary key (id)
)
    comment '自定义表单';

create unique index sys_custom_form_form_key_uindex
    on sys_custom_form (form_key);

alter table act_workflow_formdata drop column control_id;

alter table act_workflow_formdata drop column control_name;

alter table act_workflow_formdata change control_value form_data longtext null comment '表单值';

alter table act_workflow_formdata add form_desc longtext null comment '表单属性定义' after form_key;

