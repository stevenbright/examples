package com.truward.hdbdemo;

import com.truward.hdbdemo.dal.beans.Profile;
import com.truward.hdbdemo.dal.services.Service;
import com.truward.hdbdemo.dal.services.SimpleHsqlAccessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Entry point
 */
public class App {
    private static void printSampleProfiles(Service service) {
        final List<Profile> profiles = service.getSampleProfiles();
        for (final Profile p : profiles) {
            System.out.println(p.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println("HDB app");

        final ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"application-context.xml"});

        printSampleProfiles((Service) context.getBean("service"));

        try {
            if (args.length > 10) {
                SimpleHsqlAccessor.playWithHsqldb1();
            }
            SimpleHsqlAccessor.playWithHsqldb2(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
