package com.alexshabanov.service.impl.dao.util;

import com.alexshabanov.service.domain.UserAccount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Row mapper for user account objects.
 */
public final class UserAccountRowMapper implements RowMapper<UserAccount> {
    public static final String PARAMETERS = "user_id, user_name, balance, created";

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
