package com.mysite.jdosample;

import com.mysite.jdosample.model.Account;
import com.mysite.jdosample.model.BlogItem;

import javax.jdo.*;
import java.util.Collection;
import java.util.Date;

/**
 * Entry point
 */
public class App {
    private static Account createAccount(String username, int age, String email, double latitude, double longitude) {
        final Account account = new Account();
        account.setUsername(username);
        account.setAge(age);
        account.setEmail(email);
        account.setPassword("test");
        account.setCreated(new Date());
        account.setUpdated(null);
        account.setSubscribedToNews(false);
        account.setLatitude(latitude);
        account.setLongitude(longitude);
        return account;
    }

    public static BlogItem createBlogItem(String content) {
        final BlogItem blogItem = new BlogItem();
        blogItem.setContent(content);
        return blogItem;
    }

    private static String toString(Account account) {
        return "{ " +
                "id: " + account.getId() + ", " +
                "username: '" + account.getUsername() + "', " +
                "age: " + account.getAge() + ", " +
                "email: '" + account.getEmail() + "', " +
                "lat: " + account.getLatitude() + ", " +
                "long: " + account.getLongitude() + ", " +
                "stn: " + account.isSubscribedToNews() +
                "}";
    }

    private static String toString(BlogItem item) {
        return "{ " +
                "content: " + item.getContent() +
                "}";
    }

    public static void commitObjects(PersistenceManager pm) throws Exception {
        final Transaction tx = pm.currentTransaction();
        tx.begin();

        System.out.println("Persisting objects");

        pm.makePersistentAll(createAccount("alex", 21, "alex@mail.com", 4.0, 4.0));
        pm.makePersistentAll(createAccount("bob", 8, "bob@mail.com", 5.0, 3.0));
        pm.makePersistentAll(createAccount("catherine", 20, "cathy@mail.com", 1.0, 5.0));
        pm.makePersistentAll(createAccount("diana", 40, "di@mail.com", 2.0, 3.0));

        pm.makePersistentAll(createBlogItem("Here we go"));

        tx.commit();
    }

    public static void listObjects(PersistenceManager pm) throws Exception {
        // account query

        final Query accountQuery = pm.newQuery(Account.class);

        @SuppressWarnings("unchecked")
        final Collection<Account> accounts = (Collection<Account>) accountQuery.execute();

        for (final Account account : accounts) {
            System.out.println("Account: " + toString(account));
        }

        // blog query

        final Query blogQuery = pm.newQuery(BlogItem.class);

        @SuppressWarnings("unchecked")
        final Collection<BlogItem> blogItems = (Collection<BlogItem>) blogQuery.execute();

        for (final BlogItem item : blogItems) {
            System.out.println("BlogItem: " + toString(item));
        }
    }

    public static void queryObjects(PersistenceManager pm) throws Exception {
        final Query complexQuery = pm.newQuery(Account.class);
        complexQuery.setOrdering("(latitude - 1)*(latitude - 1) + (longitude - 1)*(longitude - 1) ascending");

        @SuppressWarnings("unchecked")
        final Collection<Account> accounts = (Collection<Account>) complexQuery.execute();

        for (final Account account : accounts) {
            System.out.println("Account: " + toString(account));
        }
    }

    public static void main(String[] args) {
        final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        System.out.println("DataNucleus ORM with JDO");

        final PersistenceManager pm = pmf.getPersistenceManager();
        final Transaction tx = pm.currentTransaction();
        try {
            commitObjects(pm);
            listObjects(pm);
            queryObjects(pm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
    }
}
