package com.mysite.jdosample;

import org.junit.Assert;
import org.junit.Test;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

/**
 * Unit test for simple App.
 */
public class AppTest extends Assert {
    @Test
    public void testApp() {
        final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        System.out.println("DataNucleus ORM with JDO");

        final PersistenceManager pm = pmf.getPersistenceManager();
        final Transaction tx = pm.currentTransaction();
        try {
            App.commitObjects(pm);
            App.listObjects(pm);
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }

        assertTrue(true);
    }
}
