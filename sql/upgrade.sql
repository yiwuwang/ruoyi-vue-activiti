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

