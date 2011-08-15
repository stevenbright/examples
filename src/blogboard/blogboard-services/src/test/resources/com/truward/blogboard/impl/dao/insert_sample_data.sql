
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
