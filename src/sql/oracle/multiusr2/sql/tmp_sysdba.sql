
SELECT 'dummy' FROM dual;

SELECT username FROM dba_users;

SELECT * FROM dba_users WHERE username LIKE 'QWA%' AND ROWNUM<10;

SELECT * FROM qwa200.user_account;

DROP USER qwa200 CASCADE;
DROP USER qwa_user200 CASCADE;
DROP TABLESPACE qwa_tspace200 INCLUDING CONTENTS AND DATAFILES;


--
DROP TABLE qwa200.user_account;
CREATE TABLE qwa200.user_account (
  user_id       INTEGER,
  email         VARCHAR2(64) NOT NULL,
  created       TIMESTAMP NOT NULL,
  
  CONSTRAINT pk_user_account PRIMARY KEY (user_id)
) TABLESPACE qwa_tspace200;

--
CREATE TABLE qwa200.user_profile (
  profile_id    INTEGER,
  display_name  VARCHAR2(64) NOT NULL,
  avatar_url    VARCHAR2(64),
  user_id       INTEGER NOT NULL
  
--  CONSTRAINT pk_user_profile PRIMARY KEY (profile_id),

--  CONSTRAINT fk_user_profile_user_id FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE
) TABLESPACE qwa_tspace200;

