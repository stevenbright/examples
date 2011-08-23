
---
--- tables
---

CREATE TABLE $schema.user_account (
  user_id       INTEGER,
  email         VARCHAR2(64) NOT NULL,
  created       SYSDATE NOT NULL,
  
  CONSTRAINT pk_user_account PRIMARY KEY (user_id)
) TABLESPACE $tablespace;

CREATE TABLE $schema.user_profile (
  profile_id    INTEGER,
  display_name  VARCHAR2(64) NOT NULL,
  avatar_url    VARCHAR2(64),
  user_id       INTEGER NOT NULL,
  
  CONSTRAINT pk_user_profile PRIMARY KEY (profile_id),

  CONSTRAINT fk_user_profile_user_id FOREIGN KEY (user_id)
    REFERENCES user_account(user_id) ON DELETE CASCADE
) TABLESPACE $tablespace;

---
--- sequences
---

CREATE SEQUENCE $schema.user_account;

CREATE SEQUENCE $schema.user_profile;


