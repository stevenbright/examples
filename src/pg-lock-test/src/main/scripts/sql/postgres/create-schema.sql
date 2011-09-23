
--
-- Sequences
--

CREATE SEQUENCE seq_user_account;
CREATE SEQUENCE seq_bank_ops;

--
-- Tables
--

-- user account
CREATE TABLE user_account (
  user_id     INTEGER,
  user_name   VARCHAR(32) NOT NULL,
  balance     DECIMAL NOT NULL,
  created     TIMESTAMP NOT NULL,
  CONSTRAINT pk_user_account        PRIMARY KEY (user_id),
  CONSTRAINT unq_user_account_name  UNIQUE (user_name)
);

-- operation type
CREATE TABLE bank_op_type (
  op_type_id  INTEGER,
  code        VARCHAR(32) NOT NULL,
  CONSTRAINT pk_bank_op_type        PRIMARY KEY (op_type_id),
  CONSTRAINT unq_bank_op_type_code  UNIQUE (code)
);

-- bank operations
CREATE TABLE bank_ops (
  op_id       INTEGER,
  amount      DECIMAL NOT NULL,
  op_type_id  INTEGER NOT NULL,
  user_id     INTEGER NOT NULL,
  created     TIMESTAMP NOT NULL,
  CONSTRAINT pk_bank_ops            PRIMARY KEY (op_id),
  CONSTRAINT fk_bank_ops_type_id    FOREIGN KEY (op_type_id) REFERENCES bank_op_type(op_type_id) ON DELETE CASCADE,
  CONSTRAINT fk_bank_ops_user_id    FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE
);

--
-- Initialization: constants
--

INSERT INTO bank_op_type (op_type_id, code) VALUES (1, 'WITHDRAW');
INSERT INTO bank_op_type (op_type_id, code) VALUES (2, 'REPLENISH');


--
-- API
--

CREATE OR REPLACE FUNCTION f_add_user (
  pr_user_name    user_account.user_name%TYPE,
  pr_balance      user_account.balance%TYPE
) RETURNS INTEGER AS '
  DECLARE l_user_id   INTEGER;
  BEGIN
    SELECT nextval(''seq_user_account'') INTO l_user_id;

    INSERT INTO user_account
      (user_id, user_name, balance, created)
    VALUES
      (l_user_id, pr_user_name, pr_balance, now());

    RETURN l_user_id;
END;
'
LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION f_add_bank_op (
  pr_amount       bank_ops.amount%TYPE,
  pr_op_type      bank_op_type.code%TYPE,
  pr_user_name    user_account.user_name%TYPE
) RETURNS INTEGER AS '
  DECLARE l_op_id     INTEGER;
  DECLARE l_user_id   INTEGER;
  DECLARE l_type_id   INTEGER;
  BEGIN
    -- get user id
    SELECT user_id INTO l_user_id FROM user_account WHERE user_name = pr_user_name;
    SELECT op_type_id INTO l_type_id FROM bank_op_type WHERE code = pr_op_type;

    -- next operation ID
    SELECT nextval(''seq_bank_ops'') INTO l_op_id;

    INSERT INTO bank_ops
      (op_id, amount, op_type_id, user_id, created)
    VALUES
      (l_op_id, pr_amount, l_type_id, l_user_id, now());

    RETURN l_op_id;
END;
'
LANGUAGE plpgsql;

--
-- Sample usage
--

--SELECT f_add_user('cavin', 12);
--SELECT f_add_user('bob', 500);
--SELECT f_add_user('alex', 4000);

--SELECT f_add_bank_op(10, 'WITHDRAW', 'cavin');
--SELECT f_add_bank_op(110, 'REPLENISH', 'alex');

