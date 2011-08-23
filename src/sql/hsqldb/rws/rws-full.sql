
drop table PROFILE if exists;

create table PROFILE (
   ID integer identity,
   USERNAME varchar(64) not null,
   CREATED timestamp,
   constraint IDX_PROFILE_ID primary key (ID),
   constraint UNQ_PROFILE_USERNAME unique (USERNAME)
);

insert into PROFILE(USERNAME, CREATED) values ('alex',	 '2005-01-12 17:14:10');
insert into PROFILE(USERNAME, CREATED) values ('bob',   '2005-01-12 17:10:10');
insert into PROFILE(USERNAME, CREATED) values ('fred',  '2005-01-12 17:11:10');
insert into PROFILE(USERNAME, CREATED) values ('john',  '2005-01-12 17:19:10');
insert into PROFILE(USERNAME, CREATED) values ('us1',   '2005-01-12 17:15:10');


select * from PROFILE;

select * from PROFILE  where USERNAME like '%o%';