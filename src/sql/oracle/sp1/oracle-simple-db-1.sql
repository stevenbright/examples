
--------------------------------------------------------------------------------
----
---- Simple Oracle Script
----
--------------------------------------------------------------------------------

-- tablespace (where all the tables will be located)
CREATE BIGFILE TABLESPACE test1_ts DATAFILE 'test1_ts.dat' SIZE 10M AUTOEXTEND ON;

-- test1 - root test1 user (it's name is a namespace for tables)
CREATE USER test1 IDENTIFIED BY test
    DEFAULT TABLESPACE test1_ts 
    QUOTA UNLIMITED ON test1_ts;
-- grant user with priveleges
GRANT CONNECT,
    CREATE TABLE, CREATE PUBLIC SYNONYM, CREATE VIEW,
    CREATE PROCEDURE, CREATE TRIGGER, CREATE SEQUENCE
    TO test1
    WITH ADMIN OPTION;
    
--- run the following as test1 user:
-- create simple tables in the user 'test1' namespace
CREATE TABLE test1.checkme (
    id            INTEGER NOT NULL,
    login         VARCHAR2(50) NOT NULL,
    user_name     VARCHAR2(50) NOT NULL
) TABLESPACE test1_ts;

ALTER TABLE test1.checkme
    ADD CONSTRAINT pk_checkme_id PRIMARY KEY (id)
    ADD CONSTRAINT unq_checkme_login UNIQUE (login);
    
CREATE TABLE test1.checkme (
    id            INTEGER NOT NULL,
    login         VARCHAR2(50) NOT NULL,
    user_name     VARCHAR2(50) NOT NULL
) TABLESPACE test1_ts;

ALTER TABLE test1.checkme
    ADD CONSTRAINT pk_checkme_id PRIMARY KEY (id)
    ADD CONSTRAINT unq_checkme_login UNIQUE (login);


INSERT INTO test1.checkme (id, login, user_name) VALUES (1, 'alex', 'Alexander J');
INSERT INTO test1.checkme (id, login, user_name) VALUES (2, 'dave', 'Dave Hikes');
INSERT INTO test1.checkme (id, login, user_name) VALUES (3, 'boris', 'Борис Борисович');

SELECT * FROM test1.checkme;



--------------------------------------------------------------------------------
----
---- END
----
--------------------------------------------------------------------------------


