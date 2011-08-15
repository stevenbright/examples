package com.mysite.springmvchiberdemo;

import com.mysite.springmvchiberdemo.dao.SampleDao;
import com.mysite.springmvchiberdemo.model.Person;
import com.mysite.springmvchiberdemo.util.ModelUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sample DAO test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/service-context.xml" })
public class SampleDaoTest {
    @Resource
    private SampleDao sampleDao;

    @Test
    public void testSamplePersons() throws Exception {
        sampleDao.deleteAll();

        sampleDao.save(ModelUtil.createPerson("alice", 19, true, "Status #1"));
        sampleDao.save(ModelUtil.createPerson("bob", 24, false, "Status #2"));

        List<Person> persons = sampleDao.getAll();

        Assert.assertEquals(2, persons.size());
        Assert.assertNotSame(persons.get(0).getId(), persons.get(1).getId());
    }
}
