
---
--- tables
---


create table USER_ACCOUNT (
  ID integer not null,
  USER_NAME varchar2(64) not null,
  BALANCE decimal not null,
  CREATED timestamp not null
);

create table BANK_OPERATION (
  ID integer not null,
  USER_ID integer not null,
  AMOUNT decimal not null,
  STATUS integer not null,
  CREATED timestamp not null
);

---
--- constraints
---

-- USER_ACCOUNT
alter table USER_ACCOUNT add constraint
  PK_USER_ACCOUNT_ID primary key (ID);

alter table USER_ACCOUNT add constraint
  UNQ_USER_ACCOUNT_USER_NAME unique (USER_NAME);

-- BANK_OPERATIONS
alter table BANK_OPERATION add constraint
  PK_BANK_OPERATION_ID primary key (ID);

alter table BANK_OPERATION add constraint
  FK_BANK_OPERATION_USER_ID foreign key (USER_ID) references USER_ACCOUNT(ID) on delete cascade;


---
--- sequences
---

create sequence SEQ_USER_ACCOUNT_ID start with 1 increment by 1 nomaxvalue;

create sequence SEQ_BANK_OPERATION_ID start with 1 increment by 1 nomaxvalue;


---
--- triggers
---

create trigger TRG_USER_ACCOUNT_ID_SEQ
	before insert on USER_ACCOUNT
	for each row
begin
	select SEQ_USER_ACCOUNT_ID.nextval into :new.ID from dual;
end;
/

create trigger TRG_BANK_OPERATION_ID_SEQ
	before insert on BANK_OPERATION
	for each row
begin
	select SEQ_BANK_OPERATION_ID.nextval into :new.ID from dual;
end;
/

;

---
--- end
---