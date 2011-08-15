package com.truward.myblog;

import com.truward.myblog.dao.UserDao;
import com.truward.myblog.model.user.Profile;
import com.truward.myblog.model.user.Role;
import com.truward.myblog.util.ModelUtil;
import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * Entry point/
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class);

    private static void testDao(UserDao userDao) {
        Profile profile;
        List<Profile> profiles;
        
        userDao.save(ModelUtil.createRole("admin"));
        userDao.save(ModelUtil.createRole("user"));

        final Role roleAdmin = userDao.findRoleByName("admin");
        final Role roleUser = userDao.findRoleByName("user");

        userDao.save(ModelUtil.createProfile("Alex", "alex@m.org"));
        userDao.save(ModelUtil.createProfile("Bob", "bob@m.org"));

        profile = ModelUtil.createProfile("Diana", "diana@m.org");
        profile.setRoles(Arrays.asList(roleUser));
        userDao.save(profile);

        profile = userDao.findProfileByLogin("Alex");
        profile.getRoles().addAll(Arrays.asList(roleAdmin, roleUser));
        profile.setEmail("alexander@m.biz");
        userDao.update(profile);

        profiles = userDao.getProfiles();
        System.out.println("#1: " + ModelUtil.toString(profiles));

        userDao.save(ModelUtil.createProfile("Cole", "cole@m.org"));

        profile = userDao.findProfileByLogin("Diana");
        profile.setLogin("Daina");
        profile.setEmail("danna2@ml.com");
        userDao.update(profile);

        profile = userDao.findProfileByLogin("Bob");
        userDao.remove(profile.getId());

        profiles = userDao.getProfiles();
        System.out.println("#2: " + ModelUtil.toString(profiles));
    }

    private static void testHiberJpaDao() {
        System.out.println("=== JPA+HIBERNATE DAO ===");

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                "hibernate-dao-context.xml"
        });

        testDao((UserDao) context.getBean("userDao"));

        context.close();
    }

    private static void testJdbcDao() {
        System.out.println("=== JDBC DAO ===");

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                "jdbc-dao-context.xml"
        });

        testDao((UserDao) context.getBean("userDao"));

        context.close();
    }

    public static void main(String[] args) {
        log.info("My Blog Model");

        testJdbcDao();
        testHiberJpaDao();

        if (args.length == 55) {
            testJdbcDao();
            testHiberJpaDao();
        }
    }
}
