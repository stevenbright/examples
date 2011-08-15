package com.truward.myblog.impl.dao.jdbc;

import com.truward.myblog.dao.UserDao;
import com.truward.myblog.dao.exception.ExpectationFailedException;
import com.truward.myblog.impl.dao.jdbc.layout.DbLayout;
import com.truward.myblog.impl.dao.jdbc.layout.ProfileRowMapper;
import com.truward.myblog.impl.dao.jdbc.layout.RoleRowMapper;
import com.truward.myblog.impl.dao.jdbc.util.DaoUtil;
import com.truward.myblog.model.user.Profile;
import com.truward.myblog.model.user.Role;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements user DAO using spring JDBC support
 */
public class UserDaoJdbcImpl implements UserDao {
    private SimpleJdbcTemplate template;


    private SimpleJdbcInsert profileInserter;

    private SimpleJdbcInsert roleInserter;

    private SimpleJdbcInsert profileToRoleInserter;

    public void setDataSource(DataSource dataSource) {
        template = new SimpleJdbcTemplate(dataSource);

        // right after having done with assigning data source it is required to
        // create table schema here
        template.update(DbLayout.getSchema());

        // create helpers
        profileInserter = new SimpleJdbcInsert(dataSource)
                .withTableName(DbLayout.PROFILE_TABLE_NAME)
                .usingGeneratedKeyColumns(DbLayout.COL_ID);
        profileInserter.compile();

        roleInserter = new SimpleJdbcInsert(dataSource)
                .withTableName(DbLayout.ROLE_TABLE_NAME)
                .usingGeneratedKeyColumns(DbLayout.COL_ID);
        roleInserter.compile();

        profileToRoleInserter = new SimpleJdbcInsert(dataSource)
                .withTableName(DbLayout.PROFILE_TO_ROLE_TABLE_NAME);
        profileToRoleInserter.compile();
    }

    private void updateRoles(long profileId, List<Role> newRole) {
        if (newRole == null || newRole.size() == 0) {
            return;
        }

        // rewrite roles as well: first of all remove roles
        template.update(DbLayout.REMOVE_PROFILE_ROLES_QUERY,
                new MapSqlParameterSource(DbLayout.PAR_PROFILE_ID, profileId));

        // ... then insert them from scratch
        final Map<String, Object> profileToRoleParameters = new HashMap<String, Object>();
        profileToRoleParameters.put(DbLayout.COL_PROFILE_TO_ROLE_PROFILE_ID, profileId);
        for (final Role role : newRole) {
            profileToRoleParameters.put(DbLayout.COL_PROFILE_TO_ROLE_ROLE_ID, role.getId());
            profileToRoleInserter.execute(profileToRoleParameters);
        }
    }

    public void save(Profile profile) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(DbLayout.COL_PROFILE_LOGIN, profile.getLogin());
        parameters.put(DbLayout.COL_PROFILE_EMAIL, DaoUtil.adapt(profile.getEmail()));
        parameters.put(DbLayout.COL_PROFILE_PASSWORD, DaoUtil.adapt(profile.getPassword()));
        parameters.put(DbLayout.COL_PROFILE_CREATED, DaoUtil.adapt(profile.getCreated()));

        // save profile-only data
        final long profileId = (Long) profileInserter.executeAndReturnKey(parameters);

        updateRoles(profileId, profile.getRoles());
    }

    public void save(Role role) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(DbLayout.COL_ROLE_NAME, role.getName());
        roleInserter.execute(parameters);
    }

    public void update(Profile profile) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(DbLayout.PAR_ID, profile.getId());
        parameters.addValue(DbLayout.PAR_PROFILE_LOGIN, profile.getLogin());
        parameters.addValue(DbLayout.PAR_PROFILE_EMAIL, DaoUtil.adapt(profile.getEmail()));
        parameters.addValue(DbLayout.PAR_PROFILE_PASSWORD, DaoUtil.adapt(profile.getPassword()));
        template.update(DbLayout.UPDATE_PROFILE_QUERY, parameters);

        updateRoles(profile.getId(), profile.getRoles());
    }

    public void remove(long profileId) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(DbLayout.PAR_ID, profileId);
        template.update(DbLayout.REMOVE_PROFILE_QUERY, parameters);
    }

    public Profile findProfileByLogin(String login) {
        final List<Profile> profiles = template.query(DbLayout.GET_PROFILE_BY_LOGIN_QUERY,
                new ProfileRowMapper(template),
                new MapSqlParameterSource(DbLayout.PAR_PROFILE_LOGIN, login));
        
        if (profiles.size() == 0) {
            return null;
        } else if (profiles.size() > 1) {
            throw new ExpectationFailedException("There are too much profiles that corresponds to the login given");
        }

        return profiles.get(0);
    }

    public Role findRoleByName(String name) {
        final List<Role> roles = template.query(DbLayout.GET_ROLE_BY_NAME_QUERY,
                new RoleRowMapper(),
                new MapSqlParameterSource(DbLayout.PAR_ROLE_NAME, name));

        if (roles.size() == 0) {
            return null;
        } else if (roles.size() > 1) {
            throw new ExpectationFailedException("There are too much roles that corresponds to the name given");
        }

        return roles.get(0);
    }

    public List<Profile> getProfiles() {
        return template.query(DbLayout.GET_PROFILES_QUERY, new ProfileRowMapper(template));
    }
}
