package com.mysite.jdort.rtsec;

import com.mysite.jdort.model.PropertyBag;

import java.util.Collection;

/**
 * Tests sample dao
 */
public class DaoTester {
    private final SampleDao dao;

    public DaoTester(SampleDao dao) {
        this.dao = dao;
    }

    public void test() {
        dao.saveBlogItem("one");
        dao.saveBlogItem("two");
        dao.saveBlogItem("three");

        Collection<PropertyBag> l = dao.getBlogItems();

        System.out.print("Sample blog items =");
        for (PropertyBag i : l) {
            System.out.print(" " + i.getStringProperty("content"));
        }
        System.out.println(".");
    }
}
