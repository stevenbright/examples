package com.truward.myblog.impl.dao.jdbc.layout;

import com.truward.myblog.model.user.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RoleRowMapper implements ParameterizedRowMapper<Role> {
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Role role = new Role();
        role.setId(rs.getInt(DbLayout.PAR_ID));
        role.setName(rs.getString(DbLayout.PAR_ROLE_NAME));
        return role;
    }
}
