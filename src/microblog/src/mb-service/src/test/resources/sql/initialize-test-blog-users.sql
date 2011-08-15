-- add test blog users for blog service test

insert into USER_ACCOUNT (LOGIN, PASSWORD, EMAIL, CREATED) values ('alex', 'test', 'alex@mail.ru', now());
insert into USER_ACCOUNT (LOGIN, PASSWORD, EMAIL, CREATED) values ('bob', 'test', 'bob@mail.ru', now());
insert into USER_ACCOUNT (LOGIN, PASSWORD, EMAIL, CREATED) values ('eva', 'test', 'eva@mail.ru', now());
insert into USER_ACCOUNT (LOGIN, PASSWORD, EMAIL, CREATED) values ('dave', 'test', 'dave@mail.ru', now());
insert into USER_ACCOUNT (LOGIN, PASSWORD, EMAIL, CREATED) values ('cavin', 'test', 'cavin@mail.ru', now());

-- these values already initialized in `initialize-roles.sql'
--insert into ROLE (ROLE_NAME) values ('ROLE_USER');
--insert into ROLE (ROLE_NAME) values ('ROLE_ADMIN');

insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='alex',
  select ID from ROLE where ROLE_NAME='ROLE_USER');
insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='alex',
  select ID from ROLE where ROLE_NAME='ROLE_ADMIN');

insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='bob',
  select ID from ROLE where ROLE_NAME='ROLE_USER');

insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='cavin',
  select ID from ROLE where ROLE_NAME='ROLE_USER');

insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='dave',
  select ID from ROLE where ROLE_NAME='ROLE_USER');

insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='eva',
  select ID from ROLE where ROLE_NAME='ROLE_ADMIN');
insert into USER_ROLES (USER_ID, ROLE_ID) values (select ID from USER_ACCOUNT where LOGIN='eva',
  select ID from ROLE where ROLE_NAME='ROLE_USER');
