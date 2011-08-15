package com.truward.jdoblog.services;


import com.truward.jdoblog.services.domain.Account;
import com.truward.jdoblog.services.domain.BlogComment;
import com.truward.jdoblog.services.domain.BlogPost;
import com.truward.jdoblog.services.domain.Role;
import com.truward.jdoblog.services.service.BlogService;
import com.truward.jdoblog.util.SrvUtil;
import com.truward.jdoblog.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jdo.JdoTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;


/**
 * Integration tests for the blog service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/test-orm-config.xml" })
public class BlogServiceTest extends Assert {
    @Resource
    private BlogService blogService;

    /**
     * Transactional manager is used in order to keep DB unchanged during the test passes.
     * This manager is used by the Spring test runner to perform transactional tests,
     */
    @Resource
    private JdoTransactionManager transactionManager;



    // tests

    @Test
    @Transactional
    public void testAccountsPersistence() {
        SrvUtil.saveAccounts(blogService, 3);
        Collection<Account> accounts = blogService.getAccounts();
        Util.assertAccountsEquals(accounts, 0, 3);

        blogService.deleteAccount(Util.at(accounts, 2).getId());

        accounts = blogService.getAccounts();
        Util.assertAccountsEquals(accounts, 0, 2);

        // check retrieval accounts by their names
        {
            final int accountIndex = 0;
            final Account account = blogService.getAccountByName(Util.getDisplayName(accountIndex));
            assertEquals(Util.getDisplayName(accountIndex), account.getDisplayName());
        }

        {
            final int accountIndex = 1;
            final Account account = blogService.getAccountByName(Util.getDisplayName(accountIndex));
            assertEquals(Util.getDisplayName(accountIndex), account.getDisplayName());
        }
    }

    @Test
    @Transactional
    public void testAccountsUpdate() {
        SrvUtil.saveAccount(blogService, 3);
        SrvUtil.saveAccount(blogService, 4);

        Collection<Account> accounts = blogService.getAccounts();
        Util.assertAccountsEquals(accounts, 3, 5);

        {
            final Iterator<Account> it = accounts.iterator();
            it.next();

            final Account account = it.next();
            blogService.updateAccountData(account.getId(), Util.getAccountData(1));
        }

        // check changed content
        accounts = blogService.getAccounts();
        {
            final Iterator<Account> it = accounts.iterator();
            final Account account0 = it.next();
            final Account account1 = it.next();
            assertFalse(it.hasNext());

            Util.assertAccountEquals(account0, 3);
            Util.assertAccountEquals(account1, 1);
        }
    }

    @Test
    @Transactional
    public void testBlogPosts() {
        SrvUtil.saveAccounts(blogService, 3);

        Collection<Account> accounts = blogService.getAccounts();
        Util.assertAccountsEquals(accounts, 0, 3);

        SrvUtil.saveBlogPost(blogService, accounts, 0, 0);
        SrvUtil.saveBlogPost(blogService, accounts, 2, 1);
        SrvUtil.saveBlogPost(blogService, accounts, 0, 2);
        SrvUtil.saveBlogPost(blogService, accounts, 0, 3);

        Collection<BlogPost> posts = blogService.getBlogPosts();
        Util.assertPostsEquals(posts, 0, 4);

        // check that the second posts's author is the third author
        assertEquals(Util.at(posts, 1).getAuthor().getId(), Util.at(accounts, 2).getId());
        assertEquals(Util.at(posts, 0).getAuthor().getId(), Util.at(accounts, 0).getId());
    }

    @Test
    @Transactional
    public void testRoles() {
        SrvUtil.saveAccounts(blogService, 5);

        blogService.saveRole("WRITER");
        blogService.saveRole("ADMIN");
        blogService.saveRole("READER");
        blogService.saveRole("MODERATOR");

        // assign roles
        {
            final Role[] roles = blogService.getRoles().toArray(new Role[0]);
            assertEquals(4, roles.length);
            assertEquals("ADMIN", roles[0].getName());
            assertEquals("MODERATOR", roles[1].getName());
            assertEquals("READER", roles[2].getName());
            assertEquals("WRITER", roles[3].getName());

            final Account[] accounts = blogService.getAccounts().toArray(new Account[0]);
            assertEquals(5, accounts.length);

            blogService.addAccountRole(accounts[0].getId(), roles[0].getId());
            blogService.addAccountRole(accounts[0].getId(), roles[1].getId());
            blogService.addAccountRole(accounts[0].getId(), roles[2].getId());
            blogService.addAccountRole(accounts[0].getId(), roles[2].getId()); // duplicate (shall be omitted)

            blogService.addAccountRole(accounts[1].getId(), roles[2].getId());

            //blogService.addAccountRole(accounts[2].getId(), roles[2].getId());

            blogService.addAccountRole(accounts[3].getId(), roles[0].getId());
            blogService.addAccountRole(accounts[3].getId(), roles[3].getId());
        }

        // check role contents
        final Comparator<Role> roleComparator = new Comparator<Role>() {
            public int compare(Role lhs, Role rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        };

        // check accounts
        {
            final Account[] accounts = blogService.getAccounts().toArray(new Account[0]);
            assertEquals(5, accounts.length);

            assertEquals(Util.getDisplayName(0), accounts[0].getDisplayName());
            final Role[] roles0 = blogService.getAccountRoles(accounts[0].getId()).toArray(new Role[0]);
            Arrays.sort(roles0, roleComparator);
            assertEquals(3, roles0.length);
            assertEquals("ADMIN", roles0[0].getName());
            assertEquals("MODERATOR", roles0[1].getName());
            assertEquals("READER", roles0[2].getName());

            final Role[] roles1 = blogService.getAccountRoles(accounts[1].getId()).toArray(new Role[0]);
            assertEquals(1, roles1.length);
            assertEquals("READER", roles1[0].getName());

            final Role[] roles2 = accounts[2].getRoles().toArray(new Role[0]);
            assertEquals(0, roles2.length);

            assertEquals(Util.getDisplayName(3), accounts[3].getDisplayName());
            final Role[] roles3 = accounts[3].getRoles().toArray(new Role[0]);
            Arrays.sort(roles3, roleComparator);
            assertEquals(2, roles3.length);
            assertEquals("ADMIN", roles3[0].getName());
            assertEquals("WRITER", roles3[1].getName());

            assertEquals(Util.getDisplayName(4), accounts[4].getDisplayName());
            final Role[] roles4 = accounts[4].getRoles().toArray(new Role[0]);
            assertEquals(0, roles4.length);
        }

        // delete second account (who's have the only reader role)
        {
            final Role[] roles = blogService.getRoles().toArray(new Role[0]);
            blogService.deleteAccount(blogService.getAccounts().toArray(new Account[0])[1].getId());
            blogService.deleteRole(roles[0].getId()); // delete ADMIN role
        }

        // check accounts and roles after deletion
        {
            // check roles
            final Role[] roles = blogService.getRoles().toArray(new Role[0]);
            assertEquals(3, roles.length);
            assertEquals("MODERATOR", roles[0].getName());
            assertEquals("READER", roles[1].getName());
            assertEquals("WRITER", roles[2].getName());

            // check accounts
            final Account[] accounts = blogService.getAccounts().toArray(new Account[0]);
            assertEquals(4, accounts.length);

            assertEquals(Util.getDisplayName(0), accounts[0].getDisplayName());
            final Role[] roles0 = blogService.getAccountRoles(accounts[0].getId()).toArray(new Role[0]);
            assertEquals(2, roles0.length);
            Arrays.sort(roles0, roleComparator);
            assertEquals("MODERATOR", roles0[0].getName());
            assertEquals("READER", roles0[1].getName());

            final Role[] roles1 = blogService.getAccountRoles(accounts[1].getId()).toArray(new Role[0]);
            // check that the correct account gets deleted
            assertEquals(Util.getDisplayName(2), accounts[1].getDisplayName());
            assertEquals(0, roles1.length);

            final Role[] roles2 = blogService.getAccountRoles(accounts[2].getId()).toArray(new Role[0]);
            assertEquals(1, roles2.length);
            Arrays.sort(roles2, roleComparator);
            assertEquals("WRITER", roles2[0].getName());

            final Role[] roles3 = blogService.getAccountRoles(accounts[3].getId()).toArray(new Role[0]);
            assertEquals(Util.getDisplayName(4), accounts[3].getDisplayName());
            assertEquals(0, roles3.length);
        }
    }

    @Test
    @Transactional
    public void testComments() {
        SrvUtil.saveAccounts(blogService, 4);

        final Account[] accounts = blogService.getAccounts().toArray(new Account[0]);
        assertEquals(4, accounts.length);
        final long uids[] = new long[accounts.length];
        for (int i = 0; i < accounts.length; ++i) {
            uids[i] = accounts[i].getId();
        }


        /*

        post1 (user0)
         |
         +- comment11 (user0)
         +- comment12 (user1)
         |    |
         |    +- comment121 (user2)
         |    +- comment122 (user0)
         +- comment13  (user1)

       post2  (user3)
         |
         +- comment21 (user2)
         +- comment22 (user1)

         */

        blogService.saveBlogPost(uids[0], "post1", "post1-content");
        blogService.saveBlogPost(uids[3], "post2", "post2-content");
        blogService.saveBlogPost(uids[1], "post3", "post3-content");

        final long pids[] = new long[3]; // array size must be equals to the posts count

        // save posts and all the corresponding comments
        {
            final BlogPost[] posts = blogService.getBlogPosts().toArray(new BlogPost[0]);
            assertEquals(pids.length, posts.length);

            for (int i = 0; i < posts.length; ++i) {
                pids[i] = posts[i].getId();
            }


            // comments 1X

            blogService.saveComment(pids[0], uids[0], null, "comment11");
            blogService.saveComment(pids[0], uids[1], null, "comment12");

            // nested comments
            final BlogComment comment12 = blogService.getComment("comment12");
            blogService.saveComment(pids[0], uids[2], comment12.getId(), "comment121");
            blogService.saveComment(pids[0], uids[0], comment12.getId(), "comment122");

            blogService.saveComment(pids[0], uids[1], null, "comment13");

            // comments 2X
            blogService.saveComment(pids[1], uids[2], null, "comment21");
            blogService.saveComment(pids[1], uids[1], null, "comment22");
        }


        // check all the comments
        {
            final BlogComment[] comments = blogService.getComments().toArray(new BlogComment[0]);
            assertEquals(7, comments.length);

            Util.assertCommentEquals(comments[0], "comment11", uids[0], pids[0], 0);
            Util.assertCommentEquals(comments[1], "comment12", uids[1], pids[0], 2);
            Util.assertCommentEquals(comments[2], "comment121", uids[2], pids[0], 0);
            Util.assertCommentEquals(comments[3], "comment122", uids[0], pids[0], 0);
            Util.assertCommentEquals(comments[4], "comment13", uids[1], pids[0], 0);
            Util.assertCommentEquals(comments[5], "comment21", uids[2], pids[1], 0);
            Util.assertCommentEquals(comments[6], "comment22", uids[1], pids[1], 0);

            final BlogComment[] comments1 = blogService.getComments(comments[1]).toArray(new BlogComment[0]);
            assertEquals(2, comments1.length);
            Util.assertCommentEquals(comments1[0], "comment121", uids[2], pids[0], 0);
            Util.assertCommentEquals(comments1[1], "comment122", uids[0], pids[0], 0);
        }

        // check child comments that related to the concrete posts
        {
            final BlogPost[] posts = blogService.getBlogPosts().toArray(new BlogPost[0]);
            assertEquals(pids.length, posts.length);

            final BlogComment[] comments0 = blogService.getComments(posts[0]).toArray(new BlogComment[0]);
            assertEquals(3, comments0.length);
            Util.assertCommentEquals(comments0[0], "comment11", uids[0], pids[0], 0);
            Util.assertCommentEquals(comments0[1], "comment12", uids[1], pids[0], 2);
            Util.assertCommentEquals(comments0[2], "comment13", uids[1], pids[0], 0);

            final BlogComment[] comments1 = blogService.getComments(posts[1]).toArray(new BlogComment[0]);
            assertEquals(2, comments1.length);
            Util.assertCommentEquals(comments1[0], "comment21", uids[2], pids[1], 0);
            Util.assertCommentEquals(comments1[1], "comment22", uids[1], pids[1], 0);
        }

        // delete some comments
        {
            final BlogComment[] comments = blogService.getComments().toArray(new BlogComment[0]);
            assertEquals(7, comments.length);

            // check and delete the given comments
            Util.assertCommentEquals(comments[0], "comment11", uids[0], pids[0], 0);
            Util.assertCommentEquals(comments[3], "comment122", uids[0], pids[0], 0);
            Util.assertCommentEquals(comments[6], "comment22", uids[1], pids[1], 0);
            blogService.deleteComment(comments[0].getId());
            blogService.deleteComment(comments[3].getId());
            blogService.deleteComment(comments[6].getId());
        }

        // check all the comments after deletion
        {
            final BlogComment[] comments = blogService.getComments().toArray(new BlogComment[0]);
            assertEquals(4, comments.length);

            Util.assertCommentEquals(comments[0], "comment12", uids[1], pids[0], 2);
            Util.assertCommentEquals(comments[1], "comment121", uids[2], pids[0], 0);
            Util.assertCommentEquals(comments[2], "comment13", uids[1], pids[0], 0);
            Util.assertCommentEquals(comments[3], "comment21", uids[2], pids[1], 0);

            final BlogComment[] comments1 = blogService.getComments(comments[0]).toArray(new BlogComment[0]);
            assertEquals(1, comments1.length);
            Util.assertCommentEquals(comments1[0], "comment121", uids[2], pids[0], 0);
        }
    }
}
