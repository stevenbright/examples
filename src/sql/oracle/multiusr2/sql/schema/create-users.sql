
---
--- Tablespaces
---

CREATE TABLESPACE $tablespace DATAFILE '$tablespace.dat' SIZE $sizeof_tablespace AUTOEXTEND ON;

---
--- Schema holder user
---

CREATE USER $schema IDENTIFIED BY $schema_password DEFAULT TABLESPACE $tablespace;
  
ALTER USER $schema QUOTA UNLIMITED ON $tablespace;

-- Grant schema holder with the necessary priveleges
GRANT CONNECT,
    CREATE TABLE, CREATE PUBLIC SYNONYM, CREATE VIEW,
    CREATE PROCEDURE, CREATE TRIGGER, CREATE SEQUENCE
    TO $schema;

---
--- Database user, who operates on the DB by using views/api
---

CREATE USER $dbuser_name IDENTIFIED BY $dbuser_password DEFAULT TABLESPACE $tablespace;

-- Grant database user with the necessary priveleges
GRANT CONNECT
    TO $dbuser_name;

