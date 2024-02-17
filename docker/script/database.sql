create database if not exists notes_db;
use notes_db;

create table users
(
    uuid       varchar(36) default (uuid())          not null
        primary key,
    username   varchar(100)                          not null,
    password   text                                  not null,
    status     tinyint(1)  default 1                 not null,
    created_at timestamp   default CURRENT_TIMESTAMP null,
    updated_at timestamp                             null on update CURRENT_TIMESTAMP,
    constraint username
        unique (username)
);

create table notes
(
    uuid       varchar(36) default (uuid())          not null
        primary key,
    title       varchar(50)                          not null,
    description varchar(100)                         null,
    content     longtext                             not null,
    user_uuid   varchar(36)                          not null,
    share       tinyint(1) default 0                 null,
    created_at  timestamp  default CURRENT_TIMESTAMP not null,
    updated_at  timestamp                            null on update CURRENT_TIMESTAMP,
    constraint notes_users_uuid_fk
        foreign key (user_uuid) references users (uuid)
            on delete cascade
);
