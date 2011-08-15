

-- tablespace
create bigfile tablespace TXTEST_TS datafile 'txtest_ts.dat' size 10M autoextend on;


-- user
create user TXTEST identified by TEST
    default tablespace TXTEST_TS
    quota unlimited on TXTEST_TS;

-- priveleges
grant connect,
  create table, create trigger,
  create sequence, create view, create procedure
  to TXTEST
  with admin option;


---
--- end
---
