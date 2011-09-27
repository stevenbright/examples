
--
-- Tables
--


-- user account
CREATE TABLE user_account (
  user_id     INTEGER,
  user_name   VARCHAR2(32) NOT NULL,
  balance     DECIMAL NOT NULL,
  created     TIMESTAMP NOT NULL,
  CONSTRAINT pk_user_account        PRIMARY KEY (user_id),
  CONSTRAINT unq_user_account_name  UNIQUE (user_name)
);

-- operation type
CREATE TABLE bank_op_type (
  op_type_id  INTEGER,
  code        VARCHAR2(32) NOT NULL,
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
-- Sequences
--

CREATE SEQUENCE seq_user_account;
CREATE SEQUENCE seq_bank_ops;

--
-- API
--

CREATE OR REPLACE PACKAGE usr_api AS

  PROCEDURE p_add_user (
    pr_user_name              IN        user_account.user_name%TYPE,
    pr_balance                IN        user_account.balance%TYPE,
    pro_user_id               OUT       user_account.user_id%TYPE
  );

  PROCEDURE p_add_bank_op (
    pr_amount                 IN        bank_ops.amount%TYPE,
    pr_op_type                IN        bank_op_type.code%TYPE,
    pr_user_name              IN        user_account.user_name%TYPE,
    pro_op_id                 OUT       bank_ops.op_id%TYPE
  );

END usr_api;
/
CREATE OR REPLACE PACKAGE BODY usr_api AS

  /**
   * {@inheritDoc}
   */
  PROCEDURE p_add_user (
    pr_user_name              IN        user_account.user_name%TYPE,
    pr_balance                IN        user_account.balance%TYPE,
    pro_user_id               OUT       user_account.user_id%TYPE
  ) IS
    l_user_id                 user_account.user_id%TYPE;
  BEGIN
    -- Validate input parameters.
    IF pr_user_name IS NULL OR pr_balance IS NULL
    THEN
      raise_application_error(-20101, 'Incorrect parameters');
    END IF;

    -- Get PK id.
    SELECT seq_user_account.nextval INTO l_user_id FROM dual;

    -- Insert new record.
    INSERT INTO user_account
        (user_id, user_name, balance, created)
      VALUES
        (l_user_id, pr_user_name, pr_balance, sysdate);

    -- Set results
    pro_user_id := l_user_id;
  END p_add_user;

  /**
   * {@inheritDoc}
   */
  PROCEDURE p_add_bank_op (
    pr_amount                 IN        bank_ops.amount%TYPE,
    pr_op_type                IN        bank_op_type.code%TYPE,
    pr_user_name              IN        user_account.user_name%TYPE,
    pro_op_id                 OUT       bank_ops.op_id%TYPE
  ) IS
    l_user_id                 user_account.user_id%TYPE;
    l_op_id                   bank_ops.op_id%TYPE;
    l_type_id                 bank_op_type.op_type_id%TYPE;
  BEGIN
    -- Validate input parameters.
    IF pr_amount IS NULL OR pr_op_type IS NULL OR pr_user_name IS NULL
    THEN
      raise_application_error(-20101, 'Incorrect parameters');
    END IF;

    -- Get PK id.
    SELECT seq_bank_ops.nextval INTO l_op_id FROM dual;

    -- Get associated user ID
    SELECT user_id INTO l_user_id FROM user_account WHERE user_name = pr_user_name;

    -- Get operation type ID.
    SELECT op_type_id INTO l_type_id FROM bank_op_type WHERE code = pr_op_type;

    -- Insert new record.
    INSERT INTO bank_ops
      (op_id, amount, op_type_id, user_id, created)
    VALUES
      (l_op_id, pr_amount, l_type_id, l_user_id, sysdate);

    -- Set results
    pro_op_id := l_op_id;
  END p_add_bank_op;

END usr_api;
/

--
-- Sample usage
--

/*
SHOW ERRORS;

SET serveroutput ON;
DECLARE
  l_id        INTEGER;
BEGIN
  dbms_output.enable(1000000);

  usr_api.p_add_user('cavin', 12, l_id);
  dbms_output.put_line('(1) cavin id = ' || to_char(l_id));

  usr_api.p_add_user('bob', 500, l_id);
  dbms_output.put_line('(2) bob id = ' || to_char(l_id));

  usr_api.p_add_user('alex', 4000, l_id);
  dbms_output.put_line('(3) cavin id = ' || to_char(l_id));

  usr_api.p_add_bank_op(10, 'WITHDRAW', 'cavin', l_id);
  dbms_output.put_line('(1-1) cavin op id = ' || to_char(l_id));

  usr_api.p_add_bank_op(110, 'REPLENISH', 'alex', l_id);
  dbms_output.put_line('(3-1) alex op id = ' || to_char(l_id));

  ROLLBACK;
END;
/

*/