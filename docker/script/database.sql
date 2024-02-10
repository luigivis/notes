create database if not exists notes_db;
use notes_db;

create table notes(
    id bigint primary key auto_increment,
    title varchar(50) not null,
    description varchar(100),
    content longtext not null,
    user_uuid varchar(36) not null,
    share BOOLEAN default false,
    created_at timestamp not null default NOW(),
    updated_at timestamp on update NOW()
)engine InnoDB;