package com.alexshabanov.service.impl.dao.postgres;

import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Postgres implementation of the UserAccount DAO.
 */
public final class UserAccountDaoPostgresImpl extends JdbcDaoSupport implements UserAccountDao {
    @Override
    public int addUserAccount(String name, BigDecimal balance) {
        return getJdbcTemplate().queryForInt("SELECT f_add_user(?, ?)", name, balance);
    }

    private static final class UserAccountRowMapper implements RowMapper<UserAccount> {
        static final String PARAMETERS = "user_id, user_name, balance, created";

        @Override
        public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserAccount(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getBigDecimal("balance"),
                    new Date(rs.getTimestamp("created").getTime())
            );
        }
    }

    @Override
    public List<UserAccount> getUserAccounts(int offset, int limit) {
        return getJdbcTemplate().query("SELECT " + UserAccountRowMapper.PARAMETERS +
                " FROM user_account ORDER BY user_id ASC LIMIT ? OFFSET ?",
                new UserAccountRowMapper(),
                limit, offset);
    }

    @Override
    public UserAccount getUserAccountByName(String name) {
        return getJdbcTemplate().queryForObject("SELECT " + UserAccountRowMapper.PARAMETERS +
                " FROM user_account WHERE user_name = ?",
                new UserAccountRowMapper(),
                name);
    }
}
