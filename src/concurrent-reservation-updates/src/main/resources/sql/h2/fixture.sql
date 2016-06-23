
INSERT INTO user_account (id, name) VALUES ((SELECT seq_user.nextval), 'Jane');
INSERT INTO user_account (id, name) VALUES ((SELECT seq_user.nextval), 'Alice');
INSERT INTO user_account (id, name) VALUES ((SELECT seq_user.nextval), 'Bob');
INSERT INTO user_account (id, name) VALUES ((SELECT seq_user.nextval), 'David');
INSERT INTO user_account (id, name) VALUES ((SELECT seq_user.nextval), 'Cole');

INSERT INTO room (id, name) VALUES ((SELECT seq_room.nextval), 'Everett');
INSERT INTO room (id, name) VALUES ((SELECT seq_room.nextval), 'Bellevue');
INSERT INTO room (id, name) VALUES ((SELECT seq_room.nextval), 'Redmond');
