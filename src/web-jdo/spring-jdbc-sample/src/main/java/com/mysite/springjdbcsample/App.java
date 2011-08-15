package com.mysite.springjdbcsample;

import com.mysite.springjdbcsample.dao.CountryDao;
import com.mysite.springjdbcsample.model.Country;
import com.mysite.springjdbcsample.service.CountryService;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Entry point
 */
public class App {
    private static void println(String text) {
        System.out.print("[App] ");
        System.out.println(text);
    }

    public static void main(String[] args) {
        final AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                "application-context.xml"
        });

        final CountryDao dao = (CountryDao) context.getBean("countryDao");

        dao.save(new Country(0, "Russia", "RU"));
        dao.save(new Country("Japan", "JP"));

        final List<Country> countries = dao.getAll();
        for (final Country c : countries) {
            println(c.toString());
        }

        println("Service-driven");

        final CountryService service = (CountryService) context.getBean("countryService");
        service.save(new Country("America", "US"));

        final List<Country> countries2 = service.getAll();
        for (final Country c : countries2) {
            println(c.toString());
        }

        context.close();
        println("End.");
    }
}
