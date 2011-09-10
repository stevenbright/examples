drop table FRIENDS if exists;
drop table PROFILE if exists;

create table PROFILE (
	ID integer,
	NAME varchar(64) not null
);

alter table PROFILE add constraint IDX_PROFILE_ID primary key (ID);

create table FRIEND (
	FROM_ID integer not null,
	TO_ID integer not null
);

alter table FRIEND add constraint IDX_FRIENDS_IDS primary key (FROM_ID, TO_ID);
alter table FRIEND add constraint FK_FRIENDS_FROM_ID foreign key (FROM_ID) references PROFILE(ID);
alter table FRIEND add constraint FK_FRIENDS_TO_ID foreign key (TO_ID) references PROFILE(ID);

insert into PROFILE values (1, 'alice', 18);
insert into PROFILE values (2, 'bob', 24);
insert into PROFILE values (3, 'cavin', 19);
insert into PROFILE values (4, 'dave', 25);

insert into FRIENDS values (1, 2);
insert into FRIENDS values (1, 3);
insert into FRIENDS values (1, 4);
insert into FRIENDS values (2, 4);

/* sample queries */
select * from FRIENDS;
select P.NAME from PROFILE as P where P.AGE<20;
select * from PROFILE;

/* insert friends link 1, 2 */
insert into FRIENDS (INITIATOR_ID, INITIATED_ID)
	select
		1 as INITIATOR_ID, F2.INITIATED_ID
	from
		FRIENDS as F2
	where not exists(select * from FRIENDS as F3 where F3.INITIATOR_ID=1 and F3.INITIATED_ID=2);


select * from FRIENDS as F3 where F3.INITIATOR_ID=1 and F3.INITIATED_ID=2;

select 1, 2;		

/* delete alice's friend bob */
delete from FRIENDS as F where F.INITIATOR_ID=1 and F.INITIATED_ID=(select P.ID from PROFILE as P where P.NAME='bob' order by P.ID limit 1);


/* list friends names */
select T1.NAME as INITIATOR, T2.NAME as INITIATED from FRIENDS as F
inner join PROFILE as T1 on T1.ID=F.INITIATOR_ID
inner join PROFILE as T2 on T2.ID=F.INITIATED_ID;

/* calculated field demo */
select P.NAME as NAME, P.AGE as AGE, P.ID>2 as FRIEND from PROFILE as P;

/* get all friends */
select * from PROFILE as P where exists(select * from FRIENDS as F where F.INITIATOR_ID=1 and F.INITIATED_ID=P.ID)

/* get all users with calculated boolean IS-FRIEND column using the USERNAME given */

select
	*,
	exists(
		select
			*
		from
			FRIENDS
		where
			true and
			FRIENDS.INITIATOR_ID = :ID and FRIENDS.INITIATED_ID = ALL_USERS.ID
	) AS IS_FRIEND
from
	PROFILE as ALL_USERS


select *, false as TEST from PROFILE
