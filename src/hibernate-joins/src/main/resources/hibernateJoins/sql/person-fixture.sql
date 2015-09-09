
--
-- Persons
--

INSERT INTO person (id, name, age) VALUES (10, 'Alice', 19);
INSERT INTO person (id, name, age) VALUES (11, 'Bob', 37);
INSERT INTO person (id, name, age) VALUES (12, 'Cavin', 37);
INSERT INTO person (id, name, age) VALUES (13, 'David', 37);
INSERT INTO person (id, name, age) VALUES (14, 'Emma', 37);
INSERT INTO person (id, name, age) VALUES (15, 'Fred', 37);
INSERT INTO person (id, name, age) VALUES (16, 'Gregory', 37);
INSERT INTO person (id, name, age) VALUES (17, 'Harry', 37);

--
-- Companies
--

INSERT INTO company (id, name, stock_sym) VALUES (30, 'Starbucks', 'SBUX');
INSERT INTO company (id, name, stock_sym) VALUES (31, 'Costco', 'COST');
INSERT INTO company (id, name, stock_sym) VALUES (32, 'Fred Meyers', NULL);
INSERT INTO company (id, name, stock_sym) VALUES (33, 'Bartell Drugs', NULL);
INSERT INTO company (id, name, stock_sym) VALUES (34, 'McDonalds', 'MCD');

--
-- Positions
--

INSERT INTO company_position (id, name) VALUES (50, 'Designer');
INSERT INTO company_position (id, name) VALUES (51, 'Engineer');
INSERT INTO company_position (id, name) VALUES (52, 'Art Director');
INSERT INTO company_position (id, name) VALUES (53, 'Consumer Electronics Specialist');
INSERT INTO company_position (id, name) VALUES (54, 'Manager');

--
-- Employment Entry
--

INSERT INTO employment_entry (id, start_date, end_date, person_id, company_id, pos_id)
                      VALUES (100, '1995-05-15', '1995-09-27', 11, 30, 50);


COMMIT;
