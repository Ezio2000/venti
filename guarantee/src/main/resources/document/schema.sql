create table guarantee
(
    id                 bigint auto_increment comment '主键ID'
        primary key,
    guarantee_number   varchar(50)                        not null comment '保函编号，唯一标识',
    beneficiary        varchar(100)                       not null comment '受益人',
    guaranteed_party   varchar(100)                       not null comment '被担保人',
    project_name       varchar(200)                       not null comment '项目名称',
    guarantee_amount   decimal(15, 2)                     not null comment '担保金额',
    guarantee_deadline datetime                           not null comment '担保截止日期',
    guarantor          varchar(100)                       not null comment '担保人',
    create_time        datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_guarantee_number
        unique (guarantee_number) comment '保函编号唯一索引'
)
    comment '保函信息表';

create table guarantee_verification
(
    id               bigint auto_increment comment '主键ID'
        primary key,
    guarantee_number varchar(50)                          not null comment '保函编号',
    security_code    varchar(70)                          not null comment '防伪码',
    status           tinyint(1) default 1                 not null comment '状态：1-有效，0-无效',
    create_time      datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time      datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_guarantee_number
        unique (guarantee_number) comment '保函编号索引',
    constraint uk_security_code
        unique (security_code) comment '防伪码唯一索引'
)
    comment '保函查询验证表';