
SELECT 'dummy' FROM dual;

SELECT username FROM dba_users;

SELECT * FROM dba_users WHERE username LIKE 'QWA%' AND ROWNUM<10;

SELECT * FROM qwa200.user_account;
INSERT INTO qwa200.user_account (user_id, email, created) VALUES (1, 'fred', (SELECT sysdate FROM dual));
INSERT INTO qwa200.user_account (user_id, email, created) VALUES (2, 'boris', (SELECT sysdate FROM dual));
INSERT INTO qwa200.user_account (user_id, email, created) VALUES (3, 'jane', (SELECT sysdate FROM dual));

DROP USER qwa200 CASCADE;
DROP USER qwa_user200 CASCADE;
DROP TABLESPACE qwa_tspace200 INCLUDING CONTENTS AND DATAFILES;

