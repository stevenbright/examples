package com.truward.myblog;

import com.truward.myblog.dao.UserDao;
import com.truward.myblog.model.user.Profile;
import com.truward.myblog.util.ModelUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Tests all the introduced DAOs.
 */
@RunWith(Parameterized.class)
public class DaoTest extends AbstractJUnit4SpringContextTests {
    private AbstractApplicationContext context;

    private final String contextConfigLocation;


    private UserDao userDao;

    

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "hibernate-dao-context.xml" },
                //{ "jdbc-dao-context.xml" }
        });
    }

    public DaoTest(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext(new String[] {
                contextConfigLocation
        });
        userDao = (UserDao) context.getBean("userDao");
    }

    @After
    public void tearDown() {
        context.close();
    }

    @Test
    public void testUser() {
        userDao.save(ModelUtil.createProfile("Alex", "alex@mail.org"));
        userDao.save(ModelUtil.createProfile("Bob", "bob@mail.org"));

        final List<Profile> profiles = userDao.getProfiles();
        Assert.assertEquals(2, profiles.size());
    }
}
