package com.mysite.aopsetprop;

import com.mysite.aopsetprop.model.BlogPost;
import com.mysite.aopsetprop.model.Person;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

        println("Context created.");

        final Person person1 = (Person) context.getBean("person");
        final BlogPost post1 = (BlogPost) context.getBean("blogPost");

        person1.setAge(19);
        post1.setContent("Sample content");

        context.close();

        println("End.");
    }
}
