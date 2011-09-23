
--
-- Drop all the DB entities
--

DROP FUNCTION IF EXISTS f_add_user(VARCHAR, DECIMAL);
DROP FUNCTION IF EXISTS f_add_bank_op(DECIMAL, VARCHAR, VARCHAR);

DROP TABLE IF EXISTS bank_ops;
DROP TABLE IF EXISTS bank_op_type;
DROP TABLE IF EXISTS user_account;

DROP SEQUENCE IF EXISTS seq_user_account;
DROP SEQUENCE IF EXISTS seq_bank_ops;