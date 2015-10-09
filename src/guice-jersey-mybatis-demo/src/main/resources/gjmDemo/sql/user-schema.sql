
CREATE TABLE user_profile (
  id          INTEGER PRIMARY KEY,
  username    VARCHAR(256) NOT NULL,
  age         INTEGER NOT NULL,
  note        VARCHAR(1024),
  CONSTRAINT uq_user_profile_username UNIQUE user_profile(username)
);

CREATE SEQUENCE seq_user_profile START WITH 1000;
