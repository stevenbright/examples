drop table STR_PROP if exists;
drop table DBL_PROP if exists;
drop table PROFILE if exists;


--
-- Profiles
--
create table PROFILE (
ID integer identity,

constraint IDX_PROFILE_ID primary key(ID)
);


--
-- String properties
--
create table STR_PROP (
ID integer identity,
PROFILE_ID integer not null,
PROP_NAME varchar(64) not null,
PROP_VALUE varchar(256) not null,

constraint IDX_STR_PROP_ID primary key(ID),
constraint UNQ_STR_PROP_PROFILE_ID_AND_PROP_NAME unique (PROFILE_ID, PROP_NAME),
constraint FK_STR_PROP_PROFILE_ID foreign key (PROFILE_ID) references PROFILE(ID) on delete cascade
);

--
-- Double properties
--
create table DBL_PROP (
ID integer identity,
PROFILE_ID integer not null,
PROP_NAME varchar(64) not null,
PROP_VALUE double not null,

constraint IDX_DBL_PROP_ID primary key(ID),
constraint UNQ_DBL_PROP_PROFILE_ID_AND_PROP_NAME unique (PROFILE_ID, PROP_NAME),
constraint FK_DBL_PROP_PROFILE_ID foreign key (PROFILE_ID) references PROFILE(ID) on delete cascade
);


--
-- insert fake data
--

insert into PROFILE (ID) values (100);
insert into PROFILE (ID) values (101);
insert into PROFILE (ID) values (102);

insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (0, 100, 'DISPLAY_NAME', 'Alex J');
insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (1, 101, 'DISPLAY_NAME', 'Bob Rikes');
insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (2, 102, 'DISPLAY_NAME', 'Charles Dunn');

insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (3, 100, 'EMAIL', 'alex@mail.com');
insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (4, 101, 'EMAIL', 'bob@yahoo.com');
insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (5, 102, 'EMAIL', 'charley@gnu.org');
-- the following SHALL NOT work
--insert into STR_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (6, 102, 'EMAIL', 'charley1@gnu.org');

insert into DBL_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (0, 100, 'LAT', 60.2);
insert into DBL_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (1, 101, 'LAT', 34.9);
insert into DBL_PROP (ID, PROFILE_ID, PROP_NAME, PROP_VALUE) values (2, 102, 'LAT', 78.1);

-- works in HSQLDB

select
P.ID as PID,
(select PROP_VALUE from STR_PROP where PROFILE_ID=P.ID and PROP_NAME='DISPLAY_NAME' order by ID limit 1) as DISPLAY_NAME,
(select PROP_VALUE from STR_PROP where PROFILE_ID=P.ID and PROP_NAME='EMAIL' order by ID limit 1) as EMAIL,
(select PROP_VALUE from DBL_PROP where PROFILE_ID=P.ID and PROP_NAME='LAT' order by ID limit 1) as LAT
from PROFILE as P where P.ID=:PROFILE_ID order by ID limit 1 offset 0;

select
P.ID as PID,
(select PROP_VALUE from STR_PROP where PROFILE_ID=P.ID and PROP_NAME='DISPLAY_NAME' order by ID limit 1) as DISPLAY_NAME,
(select PROP_VALUE from STR_PROP where PROFILE_ID=P.ID and PROP_NAME='EMAIL' order by ID limit 1) as EMAIL,
(select PROP_VALUE from DBL_PROP where PROFILE_ID=P.ID and PROP_NAME='LAT' order by ID limit 1) as LAT
from PROFILE as P order by ID;

--select (select ) as ID from STR_PROP order by ID limit 1 offset 0;

