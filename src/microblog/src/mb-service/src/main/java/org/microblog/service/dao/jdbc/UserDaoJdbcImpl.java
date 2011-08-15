package org.microblog.service.dao.jdbc;

import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.model.UserRole;
import org.microblog.service.dao.UserDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Implements user DAO.
 */
@Repository
public final class UserDaoJdbcImpl extends JdbcDaoSupport implements UserDao {

    private static UserAccount extractUserAccount(ResultSet rs) throws SQLException {
        final UserAccount result = new UserAccount();
        result.setId(rs.getLong(1));
        result.setLogin(rs.getString(2));
        result.setEmail(rs.getString(3));
        result.setAvatarUrl(rs.getString(4));
        result.setCreated(rs.getTimestamp(5));
        return result;
    }

    private static final class GetAccountById implements PreparedStatementCreator {
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            return con.prepareStatement("select ID, LOGIN, EMAIL, AVATAR_URL, CREATED from USER_ACCOUNT where ID=?");
        }
    }

    private static final class AccountExtractor implements ResultSetExtractor<UserAccount> {
        public UserAccount extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (!rs.next()) {
                // no such user
                return null;
            }

            return extractUserAccount(rs);
        }
    }


    private static final class GetAccountByLoginAndPassword implements PreparedStatementCreator {
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            return con.prepareStatement("select ID from USER_ACCOUNT where LOGIN=? and PASSWORD=?");
        }
    }

    private static final class AccountIdExtractor implements ResultSetExtractor<Long> {
        public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return null;
        }
    }


    /**
     * {@inheritDoc}
     */
    public UserAccount getUserAccount(final long userId) throws DataAccessException {
        return getJdbcTemplate().query(new GetAccountById(), new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, userId);
                    }
                }, new AccountExtractor());
    }

    /**
     * {@inheritDoc}
     */
    public int getUserAccountCount() throws DataAccessException {
        return getJdbcTemplate().queryForInt("select count(0) from USER_ACCOUNT");
    }

    /**
     * {@inheritDoc}
     */
    public Collection<UserAccount> getUserAccounts(int offset, int limit) throws DataAccessException {
        return getJdbcTemplate().query(
                "select ID, LOGIN, EMAIL, AVATAR_URL, CREATED from USER_ACCOUNT order by ID limit ? offset ?",
                new RowMapper<UserAccount>() {
                    public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return extractUserAccount(rs);
                    }
                }, limit, offset);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<UserRole> getUserRoles(long userId) throws DataAccessException {
        return getJdbcTemplate().query(
                "select R.ID, R.ROLE_NAME from USER_ROLES as U " +
                        "inner join ROLE as R on R.ID=U.ROLE_ID " +
                        "where U.USER_ID=?",
                new RowMapper<UserRole>() {
                    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final UserRole result = new UserRole();
                        result.setId(rs.getLong(1));
                        result.setName(rs.getString(2));
                        return result;
                    }
                }, userId);
    }

    /**
     * {@inheritDoc}
     */
    public Long getRoleId(final String name) throws DataAccessException {
        return getJdbcTemplate().query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement("select ID from ROLE where ROLE_NAME=?");
            }
        }, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, name);
            }
        }, new ResultSetExtractor<Long>() {
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                return rs.next() ? rs.getLong(1) : null;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public long addNewRole(String name) throws DataAccessException {
        getJdbcTemplate().update("insert into ROLE (ROLE_NAME) values (?)", name);
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public long addUser(String login, String password, String email, String avatarUrl) throws DataAccessException {
        getJdbcTemplate().update(
                "insert into USER_ACCOUNT (LOGIN, PASSWORD, EMAIL, AVATAR_URL, CREATED) " +
                        "values (?, ?, ?, ?, now())",
                login, password, email, avatarUrl);
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public Long getUserByLoginAndPassword(final String login, final String password) throws DataAccessException {
        return getJdbcTemplate().query(new GetAccountByLoginAndPassword(), new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, login);
                        ps.setString(2, password);
                    }
                }, new AccountIdExtractor());
    }

    /**
     * {@inheritDoc}
     */
    public void addRoleToUser(long userId, long roleId) throws DataAccessException {
        getJdbcTemplate().update("insert into USER_ROLES (USER_ID, ROLE_ID) values (?, ?)", userId, roleId);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteUserRole(long userId, long roleId) throws DataAccessException {
        getJdbcTemplate().update("delete from USER_ROLES where USER_ID=? and ROLE_ID=?", userId, roleId);
    }
}
