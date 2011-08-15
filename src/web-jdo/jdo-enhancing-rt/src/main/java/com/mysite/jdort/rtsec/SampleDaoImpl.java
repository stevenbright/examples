package com.mysite.jdort.rtsec;

import com.mysite.jdort.model.BlogItem;
import com.mysite.jdort.model.PropertyBag;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.Collection;

/**
 * Provides implementation of the sample DAO
 */
public final class SampleDaoImpl implements SampleDao {
    private PersistenceManagerFactory persistenceManagerFactory;

    public void setPersistenceManagerFactory(PersistenceManagerFactory persistenceManagerFactory) {
        this.persistenceManagerFactory = persistenceManagerFactory;
    }

    public void saveBlogItem(String content) {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        final Transaction tx = pm.currentTransaction();

        tx.begin();
        try {
            final BlogItem bi = new BlogItem();
            bi.setContent(content);
            pm.makePersistent(bi);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

    }

    public Collection<PropertyBag> getBlogItems() {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();

        Query biq = pm.newQuery(BlogItem.class);

        @SuppressWarnings("unchecked")
        final Collection<PropertyBag> ret = (Collection<PropertyBag>) biq.execute();

        return ret;
    }
}
