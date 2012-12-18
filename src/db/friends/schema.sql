
-- Tables
CREATE TABLE users (
	id INTEGER NOT NULL,
	nickname VARCHAR(32) NOT NULL,
	CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_nickname UNIQUE (nickname)
);

CREATE TABLE friends (
	from_id INTEGER NOT NULL,
	to_id INTEGER NOT NULL,
	CONSTRAINT pk_friends PRIMARY KEY (from_id, to_id),
	CONSTRAINT fk_friends_from FOREIGN KEY (from_id) REFERENCES users(id),
	CONSTRAINT fk_friends_to FOREIGN KEY (to_id) REFERENCES users(id)
);

-- Sample data
INSERT INTO users (id, nickname) VALUES
    (1, 'bob'),
    (2, 'steve'),
    (3, 'terry'),
    (4, 'alice'),
    (5, 'alex'),
    (6, 'ted'),
    (7, 'cavin'),
    (8, 'dave'),
    (9, 'eva');

INSERT INTO friends (from_id, to_id) VALUES
    (1, 2),
    (1, 4),
    (1, 9),
    (2, 1),
    (2, 5),
    (2, 6),
    (5, 7),
    (9, 2),
    (9, 1);

-- Queries
SELECT u1.nickname AS from_name, u2.nickname AS to_name FROM friends f
    JOIN users u1 ON f.from_id = u1.id
    JOIN users u2 ON f.to_id = u2.id;

SELECT DISTINCT uff.nickname FROM users uf
    JOIN friends f1 ON f1.from_id = uf.id
    JOIN friends f2 ON f2.from_id = f1.to_id
    JOIN users uff ON f2.to_id = uff.id
    WHERE uf.nickname = 'bob';



