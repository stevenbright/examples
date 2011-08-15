
---
--- constraints
---

-- USER_ACCOUNT
alter table USER_ACCOUNT add constraint
  UNQ_USER_ACCOUNT_USER_NAME unique (USER_NAME);

-- BANK_OPERATIONS
alter table BANK_OPERATION add constraint
  FK_BANK_OPERATION_USER_ID foreign key (USER_ID) references USER_ACCOUNT(ID) on delete cascade;
