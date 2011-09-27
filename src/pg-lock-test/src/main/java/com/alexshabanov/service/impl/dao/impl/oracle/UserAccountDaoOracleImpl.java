package com.alexshabanov.service.impl.dao.impl.oracle;

import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import com.alexshabanov.service.impl.dao.util.UserAccountRowMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Oracle implementation of the user account DAO.
 */
public final class UserAccountDaoOracleImpl extends OracleJdbcDaoSupport implements UserAccountDao {
    private static final class AddUserAccountProcedure {
        private final StoredProcedure procedure;

        private AddUserAccountProcedure(DataSource dataSource) {
            procedure = new StoredProcedure(dataSource, "usr_api.p_add_user") {};

            procedure.setParameters(new SqlParameter[]{
                    new SqlParameter("pr_user_name", Types.VARCHAR),
                    new SqlParameter("pr_balance", Types.DECIMAL),
                    new SqlOutParameter("pro_user_id", Types.INTEGER),
            });

            procedure.compile();
        }


        public int call(String name, BigDecimal balance) {
            final Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("pr_user_name", name);
            parameters.put("pr_balance", balance);

            final Map<String, ?> results = procedure.execute(parameters);

            final Object idValue = results.get("pro_user_id");
            if (idValue == null || !(idValue instanceof Integer)) {
                throw new DataIntegrityViolationException("Unexpected ID: " + idValue);
            }

            return (Integer) idValue;
        }
    }

    private AddUserAccountProcedure addUserAccountProcedure;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initDao() throws Exception {
        super.initDao();

        addUserAccountProcedure = new AddUserAccountProcedure(getDataSource());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addUserAccount(String name, BigDecimal balance) {
        return addUserAccountProcedure.call(name, balance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserAccount> getUserAccounts(int offset, int limit) {
        return queryWithRange(UserAccountRowMapper.PARAMETERS,
                "FROM user_account ORDER BY user_id ASC",
                new UserAccountRowMapper(),
                offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccount getUserAccountById(int id) {
        return getJdbcTemplate().queryForObject("SELECT " + UserAccountRowMapper.PARAMETERS +
                " FROM user_account WHERE user_id = ?",
                new UserAccountRowMapper(),
                id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccount getUserAccountByName(String name) {
        return getJdbcTemplate().queryForObject("SELECT " + UserAccountRowMapper.PARAMETERS +
                " FROM user_account WHERE user_name = ?",
                new UserAccountRowMapper(),
                name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccount getUserAccountByIdForUpdate(int id) {
        return getJdbcTemplate().queryForObject("SELECT " + UserAccountRowMapper.PARAMETERS +
                " FROM user_account WHERE user_id = ? FOR UPDATE",
                new UserAccountRowMapper(),
                id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserBalance(int id, BigDecimal newBalance) {
        getJdbcTemplate().update("UPDATE user_account SET balance = ? WHERE user_id = ?", newBalance, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(int id) {
        getJdbcTemplate().update("DELETE FROM user_account WHERE user_id = ?", id);
    }
}
