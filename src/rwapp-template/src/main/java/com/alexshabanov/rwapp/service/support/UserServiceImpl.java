package com.alexshabanov.rwapp.service.support;

import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.model.user.UserRole;
import com.alexshabanov.rwapp.service.UserService;
import com.alexshabanov.rwapp.service.dao.UserProfileDao;
import com.alexshabanov.rwapp.service.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserProfileDao profileDao;

    @Autowired
    private UserRoleDao roleDao;

    @Override
    public UserProfile findProfileByAlias(String accountAlias) {
        return profileDao.findByAccountAlias(accountAlias);
    }

    @Override
    @Transactional
    public UserProfile createProfile(String password, String nickname, String... roleCodes) {
        final List<UserAccount> accounts = Collections.singletonList(
                new UserAccount(nickname, UserAccount.Kind.NICKNAME));
        final Set<UserRole> roles = new HashSet<UserRole>();
        for (final String roleCode : roleCodes) {
            final UserRole role = roleDao.findByCode(roleCode);
            if (role == null) {
                throw new IllegalStateException("Unexpected role code: " + roleCode);
            }
            boolean added = roles.add(role);
            assert added : "Duplicate role " + roleCode;
        }

        return profileDao.save(new UserProfile(password, accounts, roles));
    }

    @Override
    @Transactional
    public void saveRoles(String... roleCodes) {
        for (final String roleCode : roleCodes) {
            roleDao.save(new UserRole(roleCode));
        }
    }
}
