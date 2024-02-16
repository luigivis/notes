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
    id          bigint auto_increment
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

#CREATE TABLE SPRING_SESSION (
#                                PRIMARY_ID CHAR(36) NOT NULL,
#                                SESSION_ID CHAR(36) NOT NULL,
#                                CREATION_TIME BIGINT NOT NULL,
#                                LAST_ACCESS_TIME BIGINT NOT NULL,
#                                MAX_INACTIVE_INTERVAL INT NOT NULL,
#                                EXPIRY_TIME BIGINT NOT NULL,
#                                PRINCIPAL_NAME VARCHAR(100),
#                                CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
#) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
#
#CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
#CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
#CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);
#
#CREATE TABLE SPRING_SESSION_ATTRIBUTES (
#                                           SESSION_PRIMARY_ID CHAR(36) NOT NULL,
#                                           ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
#                                           ATTRIBUTE_BYTES BLOB NOT NULL,
#                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
#                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
#) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;