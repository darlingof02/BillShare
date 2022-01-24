create database if not exists BillShare;

use BillShare;



create table user (
    uid             Integer,
    lastname        varchar(30) not null,
    firstname       varchar(30) not null,
    nickname        varchar(30),
    password        text not null,
    email           varchar(20) not null,
    avatar          text,
    tel             long,
    primary key (uid)
);

create table bill (
    bid             Integer,
    amount          Integer not null,
    receipt         text,
    status          Integer,
    type            Integer,
    primary key (bid)
);

create table own (
    uid             Integer,
    bid             Integer,
    time            timestamp,
    primary key(bid),
    foreign key (uid) references user(uid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade
);

create table indebt (
    uid             Integer,
    bid             Integer,
    time            Integer not null,
    amount          Integer not null,
    primary key(uid, bid),
    foreign key (uid) references user(uid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade
);

create user 'admin'@'localhost' identified by '123456';

grant all privileges on billshare.* to 'admin'@'localhost';

flush privileges;