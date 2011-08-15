package com.mysite.jdort.rtsec;

import com.mysite.jdort.model.PropertyBag;

import javax.jdo.PersistenceManagerFactory;
import java.util.Collection;

/**
 * Defines DAO interface
 */
public interface SampleDao {
    void setPersistenceManagerFactory(PersistenceManagerFactory persistenceManagerFactory);

    void saveBlogItem(String content);

    /**
     * We can't use BlogItem here because SampleDAO may be reloaded!
     * @return PropertyBag that is implemented by the BlogItem
     */
    Collection<PropertyBag> getBlogItems();
}
