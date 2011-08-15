package com.mysite.gwtspringmvc.server.services;

import com.mysite.gwtspringmvc.client.services.DataService;
import com.mysite.gwtspringmvc.server.controllers.GwtProxyController;
import com.mysite.gwtspringmvc.shared.SampleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample service implementation
 */
public class DataServiceImpl extends GwtProxyController implements DataService {
    private static SampleBean createBean(String name, int age, String comment) {
        final SampleBean result = new SampleBean();
        result.setName(name);
        result.setAge(age);
        result.setComment(comment);
        return result;
    }

    public List<SampleBean> getBeanFor(String query) {
        final List<SampleBean> result = new ArrayList<SampleBean>();

        if ("alice".equals(query)) {
            result.add(createBean("bob", 25, "Sample user"));
            result.add(createBean("dave", 32, "Admin"));
            result.add(createBean("fred", 7, "Brat"));
        } else if (query != null && query.contains("bob")) {
            result.add(createBean("gigavolt", 23, "Energy Monster"));
            result.add(createBean("wolfduck", 18, "Werewolf"));
        } else {
            result.add(createBean("anonymous", 0, "Unknown user"));
        }

        return result;
    }
}