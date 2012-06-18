
CREATE TABLE person (
    id      INTEGER,
    name    VARCHAR(255) NOT NULL,
    age     INTEGER NOT NULL,
    CONSTRAINT pk_bar PRIMARY KEY (id)
);


--
-- init script
--

INSERT INTO person (id, name, age) VALUES (1, 'alice', 18);
INSERT INTO person (id, name, age) VALUES (2, 'bob', 45);
INSERT INTO person (id, name, age) VALUES (3, 'cindy', 25);
INSERT INTO person (id, name, age) VALUES (4, 'dave', 28);
