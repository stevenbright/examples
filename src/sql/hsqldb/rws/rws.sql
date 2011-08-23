create table PROFILE (
   ID integer identity primary key,
   USERNAME varchar(64) not null,
   AVATAR_URL varchar(256) default 'http://www.moikrug.ru/images/blank_photo/blank_photo_228px.png' not null,
   ONLINE bit default 0 not null,
   LATITUDE double,
   LONGTITUDE double,
   ALTITUDE double,
   ACCURACY double,
   EMAIL varchar(64) not null,
   SUBSCRIBED_TO_POSTS bit not null,
   PLMN varchar(64) default '' not null,
   PSTN varchar(64),
   SIP varchar(64) default '' not null,
   DEFAULT_NUMBER_ID integer default 1 not null,
   PASSWORD varchar(64) not null,
   CREATED timestamp,
   constraint IDX_PROFILE_ID primary key (ID),
   constraint UNQ_PROFILE_USERNAME unique (USERNAME)
);

insert into PROFILE (USERNAME, EMAIL, PLMN, PASSWORD, SUBSCRIBED_TO_POSTS) values ('alice', 'a@h', '123', 'test', 1);

insert into PROFILE (USERNAME, EMAIL, PLMN, PASSWORD, SUBSCRIBED_TO_POSTS) values ('bob', 'a@h', '123', 'test', 1);
insert into PROFILE (USERNAME, EMAIL, PLMN, PASSWORD, SUBSCRIBED_TO_POSTS) values ('dave', 'a@h', '123', 'test', 1);

select * from PROFILE order by USERNAME desc limit 1 offset 1;



declare @one varchar(18);
set test = 1;
select * from PROFILE where USERNAME='alice';
