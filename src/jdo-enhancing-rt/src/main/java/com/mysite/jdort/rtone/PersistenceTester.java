package com.mysite.jdort.rtone;


import com.mysite.jdort.model.Account;
import com.mysite.jdort.model.BlogItem;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class PersistenceTester implements InitializableRunner {

    private static Account createAccount(String username, int age, String email) {
        final Account account = new Account();
        account.setUsername(username);
        account.setAge(age);
        account.setEmail(email);
        account.setPassword("test");
        account.setCreated(new Date());
        account.setUpdated(null);
        account.setSubscribedToNews(false);
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

        final Account alexAccount = createAccount("alex", 21, "alex@mail.com");
        pm.makePersistentAll(alexAccount);
        pm.makePersistentAll(createAccount("bob", 8, "bob@mail.com"));
        pm.makePersistentAll(createAccount("catherine", 20, "cathy@mail.com"));
        pm.makePersistentAll(createAccount("diana", 40, "di@mail.com"));

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

    private PersistenceManagerFactory persistenceManagerFactory;

    public void init(Map<String, ?> properties) {
        persistenceManagerFactory = (PersistenceManagerFactory) properties.get("persistenceManagerFactory");
    }

    public void run() {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        final Transaction tx = pm.currentTransaction();

        try {
            commitObjects(pm);
            listObjects(pm);
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
