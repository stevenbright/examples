
INSERT INTO user_profile (id, username, age, note) VALUES (
  (SELECT seq_user_profile.nextval), 'alice', 18, 'SysAdmin'
);

INSERT INTO user_profile (id, username, age, note) VALUES (
  (SELECT seq_user_profile.nextval), 'bob', 35, 'VP'
);

INSERT INTO user_profile (id, username, age, note) VALUES (
  (SELECT seq_user_profile.nextval), 'cavin', 27, 'Manager'
);

INSERT INTO user_profile (id, username, age, note) VALUES (
  (SELECT seq_user_profile.nextval), 'diana', 32, 'HR'
);
