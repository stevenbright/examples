package com.alexshabanov.service.impl.dao.impl.postgresql;

import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import com.alexshabanov.service.impl.dao.util.UserAccountRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * PostgreSQL implementation of the UserAccount DAO.
 */
public final class UserAccountDaoPostgresqlImpl extends JdbcDaoSupport implements UserAccountDao {
    /**
     * {@inheritDoc}
     */
    @Override
    public int addUserAccount(String name, BigDecimal balance) {
        return getJdbcTemplate().queryForInt("SELECT f_add_user(?, ?)", name, balance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserAccount> getUserAccounts(int offset, int limit) {
        if (limit == 0) {
            return Collections.emptyList();
        }

        return getJdbcTemplate().query("SELECT " + UserAccountRowMapper.PARAMETERS +
                " FROM user_account ORDER BY user_id ASC LIMIT ? OFFSET ?",
                new UserAccountRowMapper(),
                limit, offset);
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
