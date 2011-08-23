
---
--- tablespaces
---

CREATE TABLESPACE $tablespace DATAFILE '$tablespace.dat' SIZE 2M AUTOEXTEND ON;

---
--- Schema holder user
---

CREATE USER $schema IDENTIFIED BY $schema_password DEFAULT TABLESPACE $tablespace;
  
ALTER USER $schema QUOTA UNLIMITED ON $tablespace;

GRANT CONNECT TO $tablespace;
GRANT CREATE TABLE TO $tablespace;
GRANT CREATE PUBLIC SYNONYM TO $tablespace;
GRANT CREATE VIEW to $tablespace;
GRANT CREATE PROCEDURE TO $tablespace;

---
--- Database user, who operates on the DB by using views/api
---

CREATE USER $dbuser_name IDENTIFIED BY $dbuser_password DEFAULT TABLESPACE $tablespace;
GRANT CONNECT TO $dbuser_name;

