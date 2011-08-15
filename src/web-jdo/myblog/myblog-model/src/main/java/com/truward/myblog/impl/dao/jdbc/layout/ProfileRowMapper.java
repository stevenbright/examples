package com.truward.myblog.impl.dao.jdbc.layout;

import com.truward.myblog.model.user.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ProfileRowMapper implements ParameterizedRowMapper<Profile> {
    private final SimpleJdbcTemplate template;

    public ProfileRowMapper(SimpleJdbcTemplate template) {
        this.template = template;
    }

    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Profile profile = new Profile();

        profile.setId(rs.getLong(DbLayout.PAR_ID));
        profile.setLogin(rs.getString(DbLayout.PAR_PROFILE_LOGIN));
        profile.setEmail(rs.getString(DbLayout.PAR_PROFILE_EMAIL));
        profile.setPassword(rs.getString(DbLayout.PAR_PROFILE_PASSWORD));
        profile.setCreated(rs.getTimestamp(DbLayout.PAR_PROFILE_CREATED));

        // eagerly get all the corresponding roles
        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(DbLayout.PAR_PROFILE_ID, profile.getId());
        profile.setRoles(template.query(DbLayout.GET_ROLES_FOR_PROFILE_QUERY,
                new RoleRowMapper(), parameters));

        return profile;
    }
}
