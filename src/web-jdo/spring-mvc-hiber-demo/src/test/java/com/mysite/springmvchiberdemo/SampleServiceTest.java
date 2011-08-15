package com.mysite.springmvchiberdemo;

import com.mysite.springmvchiberdemo.model.Person;
import com.mysite.springmvchiberdemo.service.SampleService;
import com.mysite.springmvchiberdemo.util.ModelUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


/**
 * Sample service test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/service-context.xml" })
public class SampleServiceTest {

    @Resource
    private SampleService sampleService;


    @Test
    public void testService() {
        sampleService.deleteAll();

        sampleService.save(ModelUtil.createPerson("alex", 19, true, "Sample status #1"));

        final List<Person> persons = sampleService.getAll();
        Assert.assertEquals(1, persons.size());

        final Person person = persons.get(0);

        Assert.assertEquals("alex", person.getName());
        Assert.assertEquals(19, person.getAge());
        Assert.assertEquals(true, person.isSubscribedToPosts());
        Assert.assertEquals("Sample status #1", person.getStatus());
    }
}