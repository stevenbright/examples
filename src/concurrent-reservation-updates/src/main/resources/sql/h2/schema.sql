
CREATE TABLE user_account (
  id                INTEGER PRIMARY KEY,
  name              VARCHAR(32) NOT NULL
);

CREATE TABLE room (
  id                INTEGER PRIMARY KEY,
  name              VARCHAR(32) NOT NULL
);

CREATE TABLE reservation (
  id                INTEGER PRIMARY KEY,
  user_id           INTEGER NOT NULL,
  room_id           INTEGER NOT NULL,

  ts_from           DATETIME NOT NULL,
  ts_to             DATETIME NOT NULL,

  status            INTEGER NOT NULL,

  CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE,
  CONSTRAINT fk_reservation_room FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

--
-- Sequence
--

CREATE SEQUENCE seq_user          START WITH 100;
CREATE SEQUENCE seq_room          START WITH 1000;
CREATE SEQUENCE seq_reservation   START WITH 1000000;
