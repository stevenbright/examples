package com.mysite.hiberjpa;

import com.mysite.hiberjpa.dao.AppDao;
import com.mysite.hiberjpa.model.Book;
import com.mysite.hiberjpa.service.AppService;
import com.mysite.hiberjpa.util.ModelUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Main application class.
 */
public class App {
    private static void prn(String msg) {
        System.out.print("[App] ");
        System.out.println(msg);
    }

    private static void testAppDao(ApplicationContext context) {
        final AppDao dao = (AppDao) context.getBean("appDao");

        dao.deleteAll();

        dao.save(ModelUtil.createBook("Violet River", 10));
        dao.save(ModelUtil.createBook("Mathematics", 7));

        prn("App Dao sample");

        final List<Book> daoBookList = dao.getBooks();
        for (final Book b : daoBookList) {
            prn("[Dao] Book = " + ModelUtil.bookToString(b));
        }
    }

    private static void testAppService(ApplicationContext context) {
        final AppService service = (AppService) context.getBean("appService");

        service.deleteAll();

        service.save(ModelUtil.createBook("The War and The Peace", 29,
                "Chapter 1-1", "Chapter 1-2", "Chapter 1-3"));
        service.save(ModelUtil.createBook("Idiot", 0,
                "Knyaz enters Peterburg", "Rogozhin", "Nastasja Fillipovna", "Epilogue"));
        service.save(ModelUtil.createBook("Knyaz Serebryanyi", 12));
        service.save(ModelUtil.createBook("Aelitha", 1,
                "Prologue", "Epilogue"));

        final List<Book> srvBookList = service.getBooks();
        for (final Book b : srvBookList) {
            prn("[Srv] Book = " + ModelUtil.bookToString(b));
        }
    }

    public static void main(String[] args) {
        final AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                "services-context.xml"
        });

        testAppDao(context);

        prn("App Service sample");
        testAppService(context);
        prn("App Service retry 2");
        testAppService(context);

        prn("End");
        context.close();
    }
}
