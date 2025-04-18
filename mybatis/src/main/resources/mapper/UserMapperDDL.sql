create table user
(
    id        int auto_increment
        primary key,
    name      varchar(255) not null comment '名字',
    age       int          not null comment '年龄',
    phone     varchar(255) null,
    cryptName varchar(255) null,
    constraint idx_phone
        unique (phone)
);