create table if not exists tb_user
(
    user_id   bigint       not null primary key auto_increment,
    user_name varchar(100) not null,
    password  varchar(32)  not null
);



create table if not exists tb_role
(
    id          int                                not null primary key auto_increment,
    role        varchar(32)                        not null,
    `desc`      varchar(100)                       null,
    create_time datetime default current_timestamp not null
);

create table if not exists tb_resource
(
    id          bigint                             not null primary key auto_increment,
    path        varchar(200)                       not null,
    create_time datetime default current_timestamp not null
);


create table if not exists tb_user_role
(
    id          bigint                             not null primary key auto_increment,
    user_id     bigint                             not null,
    role_id     bigint                             not null,
    create_time datetime default current_timestamp not null
);


create table if not exists tb_role_resource
(
    id          bigint                             not null primary key auto_increment,
    role_id     bigint                             not null,
    resource_id bigint                             not null,
    create_time datetime default current_timestamp not null
);

