

CREATE TABLE posts1 (
    id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    created TIMESTAMP NOT NULL,
    text VARCHAR(64) NOT NULL,
    CONSTRAINT pk_posts1 PRIMARY KEY (id)
);


-- Indexes

--CREATE INDEX idx_posts1_user_created ON posts1(user_id, created DESC);
----	or	-----
--CREATE INDEX idx_posts1_user_id ON posts1(user_id);
--CREATE INDEX idx_posts1_created ON posts1(created);

-- Insert sample data
/*
INSERT INTO posts1 (id, user_id, created, text) VALUES
   (1, 99001, TIMESTAMP '2001-04-10 14:48:56', 'hello'),
   (2, 99002, TIMESTAMP '2001-04-29 07:19:41', 'second'),
   (3, 99001, TIMESTAMP '2001-04-29 21:20:05', 'third');
*/

-- Queries
--SELECT created, text FROM posts1 WHERE user_id=:uid ORDER BY created DESC OFFSET 10000 LIMIT 10;


-- Special limit-offset optimizations
/*

-- Offset function optimized for particular query
CREATE OR REPLACE FUNCTION posts1_user_post_pos (user_id INT, created TIMESTAMP) RETURNS bigint
AS 'SELECT count(created) FROM posts1 WHERE user_id=$1 AND created>$2;'
LANGUAGE SQL IMMUTABLE;

-- Create index for immutable offset function
CREATE INDEX idx_posts1_user_post_pos ON posts1 USING btree(posts1_user_post_pos(user_id, created));

*/

