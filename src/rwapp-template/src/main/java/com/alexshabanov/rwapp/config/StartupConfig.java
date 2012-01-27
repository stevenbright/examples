package com.alexshabanov.rwapp.config;

import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.model.user.UserRole;
import com.alexshabanov.rwapp.service.dao.UserProfileDao;
import com.alexshabanov.rwapp.service.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Initializes initial application data.
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class StartupConfig {
    @Autowired
    private UserProfileDao profileDao;

    @Autowired
    private UserRoleDao roleDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void initDefaultUsers() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                final UserRole userRole = roleDao.save(new UserRole("ROLE_USER"));
                final UserRole adminRole = roleDao.save(new UserRole("ROLE_ADMIN"));

                profileDao.save(new UserProfile("123",
                        Arrays.asList(new UserAccount("admin", UserAccount.Kind.NICKNAME)),
                        new HashSet<UserRole>(Arrays.asList(roleDao.findByCode("ROLE_USER"), roleDao.findByCode("ROLE_ADMIN")))
                ));

                return null;
            }
        });
    }
}
