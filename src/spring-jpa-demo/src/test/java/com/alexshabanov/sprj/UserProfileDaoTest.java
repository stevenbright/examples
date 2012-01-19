package com.alexshabanov.sprj;

import com.alexshabanov.sprj.model.UserProfile;
import com.alexshabanov.sprj.service.dao.UserProfileDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/jpa-data-config.xml")
@Transactional
public class UserProfileDaoTest {

    @Autowired
    private UserProfileDao userProfileDao;

    private static <T> List<T> toList(Iterable<T> iterable) {
        final List<T> list = new ArrayList<T>();
        for (final T t : iterable) {
            list.add(t);
        }
        return list;
    }

    @Test
    public void testProfileSave() {
        final UserProfile profile = new UserProfile(null, "name", 12);
        final long id = userProfileDao.save(profile).getId();

        final Iterable<UserProfile> profiles = userProfileDao.findAll();

        profile.setId(id);
        assertEquals(Arrays.asList(profile), toList(profiles));

        assertEquals(1, userProfileDao.count());
    }

    @Test
    public void testDeleteAndUpdate() {
        final UserProfile profile1 = new UserProfile(null, "name1", 20);
        final long id1 = userProfileDao.save(profile1).getId();

        final UserProfile profile2 = new UserProfile(null, "name2", 21);
        final long id2 = userProfileDao.save(profile2).getId();

        assertNotSame(id1, id2);

        assertEquals(2, userProfileDao.count());

        profile1.setId(id1);
        profile2.setId(id2);


        assertEquals(Arrays.asList(profile1, profile2), toList(userProfileDao.findAll()));

        // delete
        userProfileDao.delete(id2);
        assertEquals(Arrays.asList(profile1), toList(userProfileDao.findAll()));

        // update
        final UserProfile p12 = new UserProfile(id1, "name1_2", 31);
        final long id12 = userProfileDao.save(p12).getId();
        assertEquals(id12, id1);
        assertEquals(Arrays.asList(p12), toList(userProfileDao.findAll()));
    }
}
