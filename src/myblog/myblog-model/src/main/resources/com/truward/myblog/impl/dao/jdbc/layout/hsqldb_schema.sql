drop table POST_TO_COMMENT if exists;
drop table COMMENT if exists;
drop table POST if exists;
drop table PROFILE_TO_ROLE if exists;
drop table ROLE if exists;
drop table PROFILE if exists;

create table PROFILE (
   ID integer identity primary key,
   LOGIN varchar(64) not null,
   EMAIL varchar(128) not null,
   PASSWORD varchar(64) not null,
   CREATED timestamp not null,
   constraint IDX_PROFILE_ID primary key (ID),
   constraint UNQ_PROFILE_EMAIL unique (EMAIL),
   constraint UNQ_PROFILE_LOGIN unique (LOGIN)
);

create table ROLE (
   ID integer identity primary key,
   NAME varchar(64) not null,
   constraint IDX_ROLE_ID primary key (ID),
   constraint UNQ_ROLE_NAME unique (NAME)
);

create table PROFILE_TO_ROLE (
   PROFILE_ID integer not null,
   ROLE_ID integer not null,
   constraint IDX_PROFILE_TO_ROLE_IDS primary key (PROFILE_ID, ROLE_ID),
   constraint FK_PROFILE_TO_ROLE_PROFILE_ID foreign key (PROFILE_ID) references PROFILE(ID) on delete cascade,
   constraint FK_PROFILE_TO_ROLE_ROLE_ID foreign key (ROLE_ID) references ROLE(ID) on delete cascade
);

create table POST (
   ID integer identity primary key,
   TITLE varchar(64) not null,
   CONTENT varchar(4096) not null,
   CREATED timestamp not null,
   UPDATED timestamp not null,
   AUTHOR_ID integer not null,
   constraint IDX_POST_ID primary key (ID),
   constraint FK_POST_AUTHOR_ID foreign key (AUTHOR_ID) references PROFILE(ID) on delete cascade
);

create table COMMENT (
   ID integer identity primary key,
   CONTENT varchar(256) not null,
   CREATED timestamp not null,
   AUTHOR_ID integer not null,
   constraint IDX_COMMENT_ID primary key (ID),
   constraint FK_COMMENT_AUTHOR_ID foreign key (AUTHOR_ID) references PROFILE(ID) on delete cascade
);

create table POST_TO_COMMENT (
   POST_ID integer not null,
   COMMENT_ID integer not null,
   constraint IDX_POST_TO_COMMENT_IDS primary key (POST_ID, COMMENT_ID),
   constraint FK_POST_TO_COMMENT_POST_ID foreign key (POST_ID) references POST(ID) on delete cascade,
   constraint FK_POST_TO_COMMENT_COMMENT_ID foreign key (COMMENT_ID) references COMMENT(ID) on delete cascade
);


/* -- uncomment for tests
-- sample data

insert into PROFILE(ID, LOGIN, EMAIL, PASSWORD, CREATED) values (0, 'Alex', 'alex@mail.com', 'test', now());
insert into PROFILE(ID, LOGIN, EMAIL, PASSWORD, CREATED) values (1, 'Alice', 'alice@mail.com', 'test', now());
insert into PROFILE(ID, LOGIN, EMAIL, PASSWORD, CREATED) values (2, 'Bob', 'bob@mail.com', 'test', now());
insert into PROFILE(ID, LOGIN, EMAIL, PASSWORD, CREATED) values (3, 'Diana', 'diana@mail.com', 'test', now());
insert into PROFILE(ID, LOGIN, EMAIL, PASSWORD, CREATED) values (4, 'Eva', 'eva@mail.com', 'test', now());

insert into ROLE(ID, NAME) values (0, 'admin');
insert into ROLE(ID, NAME) values (1, 'moderator');
insert into ROLE(ID, NAME) values (2, 'investor');
insert into ROLE(ID, NAME) values (3, 'editor');
insert into ROLE(ID, NAME) values (4, 'author'); 
insert into ROLE(ID, NAME) values (5, 'user');

insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (0, 0);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (0, 1);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (0, 2);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (1, 5);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (2, 5);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (2, 4);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (3, 2);
insert into PROFILE_TO_ROLE(PROFILE_ID, ROLE_ID) values (4, 1);

-- sample queries

select * from PROFILE order by CREATED;

select * from ROLE order by ID;

select R.NAME from PROFILE_TO_ROLE as PR inner join ROLE as R on R.ID=PR.ROLE_ID and PR.PROFILE_ID=2;

-- */
