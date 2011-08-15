
/*
 * Create table schema
 */

create table PROFILE (
  ID integer identity primary key,
  USERNAME varchar(64) not null,
  EMAIL varchar(128) not null,
  PASSWORD varchar(64) not null,
  CREATED timestamp not null,

  constraint IDX_PROFILE_ID primary key (ID),
  constraint UNQ_PROFILE_EMAIL unique (EMAIL),
  constraint UNQ_PROFILE_USERNAME unique (USERNAME)
);

create table FRIEND (
  FROM_ID integer not null,
  TO_ID integer not null,

  constraint IDX_FRIEND_ID primary key (FROM_ID, TO_ID),
  constraint FK_FRIEND_FROM_ID foreign key (FROM_ID) references PROFILE(ID) on delete cascade,
  constraint FK_FRIEND_TO_ID foreign key (TO_ID) references PROFILE(ID) on delete cascade
);

create table POST (
  ID integer identity primary key,
  CONTENT varchar(4096) not null,
  CREATED timestamp not null,
  AUTHOR_ID integer not null,

  constraint IDX_POST_ID primary key (ID),
  constraint FK_POST_AUTHOR_ID foreign key (AUTHOR_ID) references PROFILE(ID) on delete cascade
);

