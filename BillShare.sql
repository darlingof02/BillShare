create user (
    uid             Integer,
    username        varchar(30) not null,
    password        varchar(20) not null,
    email           varchar(20) not null,
    avatar          text,
    primary key (uid)
);

create bill (
    bid             Integer,
    amount          Integer not null,
    receipt         text,
    status          Integer,
    type            Integer,
    primary key (bid)
);

create own (
    uid             Integer,
    bid             Integer,
    time            timestamp,
    primary key(bid),
    foreign key (uid) references user(uid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade
);

create indebt (
    uid             Integer,
    bid             Integer,
    time            Integer not null,
    amount          Integer not null,
    primary key(uid, bid),
    foreign key (uid) references user(uid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade
);