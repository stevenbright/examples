package org.microblog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.server.exception.ServiceException;
import org.microblog.exposure.server.service.BlogService;
import org.microblog.exposure.server.service.UserService;
import org.microblog.service.exception.ServiceDaoException;
import org.microblog.service.util.TestUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/blog-service-test-context.xml" })
@Transactional
public class BlogServiceTest {
    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private BlogService blogService;

    @Resource
    private UserService userService;


    @Test
    public void testTags() {
        final long id1 = blogService.addBlogTag("tag1");
        final long id2 = blogService.addBlogTag("tag2");
        assertNotSame(id1, id2);

        final Collection<BlogTag> tags = blogService.getAllTags();

        final BlogTag[] expectedTags;
        {
            final BlogTag tag1 = new BlogTag();
            tag1.setId(id1);
            tag1.setName("tag1");
            final BlogTag tag2 = new BlogTag();
            tag2.setId(id2);
            tag2.setName("tag2");
            expectedTags = new BlogTag[] { tag1, tag2 };
        }

        assertArrayEquals(expectedTags, tags.toArray());
    }

    @Test(expected = ServiceDaoException.class)
    public void testTagNameUniquenessVerificationFailure() {
        try {
            blogService.addBlogTag("tag1");
            blogService.addBlogTag("tag2");
        } catch (ServiceException e) {
            fail("Unexpected service exception: " + e);
        }

        blogService.addBlogTag("tag2");
    }

    @Test
    public void testBasicPostScenario() {
        final long javaTagId = blogService.addBlogTag("java");
        final long cppTagId = blogService.addBlogTag("cpp");

        final UserAccount[] accounts = userService.getUserAccounts(0, 9).getItems().toArray(new UserAccount[0]);
        assertTrue("Expect at least 3 accounts in the DB", accounts.length > 3);

        final long userId1 = accounts[0].getId();
        final long userId2 = accounts[1].getId();
        final long userId3 = accounts[2].getId();


        // check for initial state
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId1, 0, 9), 0);
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId2, 0, 9), 0);
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId3, 0, 9), 0);

        // make posts
        final long post1 = blogService.addBlogPost("post1", "content1", userId1, Arrays.asList(javaTagId, cppTagId));
        final long post2 = blogService.addBlogPost("post2", "content2", userId1, Arrays.asList(cppTagId));
        final long post3 = blogService.addBlogPost("post3", "content3", userId1, Arrays.asList(javaTagId));
        final long post4 = blogService.addBlogPost("post4", "content4", userId2, Arrays.asList(javaTagId));
        final long post5 = blogService.addBlogPost("post5", "content5", userId1, Arrays.asList(cppTagId));

        // check user1 posts
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId1, 0, 9),
                4, post1, post2, post3, post5);

        // check user2 posts
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId2, 0, 9),
                1, post4);

        // check user3 posts
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId3, 0, 9), 0);

        // check posts tagged with java
        TestUtil.assertChunkEquals(blogService.getTaggedPosts(javaTagId, 0, 9),
                3, post1, post3, post4);
    }

    @Test
    public void testPostsPagination() {
        final long javaTagId = blogService.addBlogTag("java");
        final long cppTagId = blogService.addBlogTag("cpp");

        final UserAccount[] accounts = userService.getUserAccounts(0, 9).getItems().toArray(new UserAccount[0]);
        assertTrue(accounts.length > 0);
        final long userId = accounts[0].getId();

        // check no posts exists for the new user

        final long post1 = blogService.addBlogPost("post1", "content1", userId, Arrays.asList(javaTagId, cppTagId));
        final long post2 = blogService.addBlogPost("post2", "content2", userId, Arrays.asList(cppTagId));
        final long post3 = blogService.addBlogPost("post3", "content3", userId, Arrays.asList(javaTagId));
        final long post4 = blogService.addBlogPost("post4", "content4", userId, Arrays.asList(javaTagId));
        final long post5 = blogService.addBlogPost("post5", "content5", userId, Arrays.asList(cppTagId));

        // check all the posts
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 0, 9),
                5, post1, post2, post3, post4, post5);

        // check posts 0:2
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 0, 2),
                5, post1, post2);

        // check posts 1:3
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 1, 3),
                5, post2, post3, post4);

        // check posts 1:0 (zero limit)
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 1, 0), 5);

        // check for post 3:1
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 3, 1), 5, post4);

        // check for post 2:8 (query with overflow)
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 2, 8),
                5, post3, post4, post5);

        // check for post 5:1 (query out of range)
        TestUtil.assertChunkEquals(blogService.getUserPosts(userId, 5, 1), 5);

        // check for tagged posts 1:0 (zero limit)
        TestUtil.assertChunkEquals(blogService.getTaggedPosts(javaTagId, 1, 0), 3);

        // check for tagged posts 1:5
        TestUtil.assertChunkEquals(blogService.getTaggedPosts(cppTagId, 1, 5), 3, post2, post5);
    }


    @Test
    public void testPostContents() throws InterruptedException {
        final long javaTagId = blogService.addBlogTag("java");
        final long cppTagId = blogService.addBlogTag("cpp");

        final UserAccount[] accounts = userService.getUserAccounts(0, 9).getItems().toArray(new UserAccount[0]);
        assertTrue(accounts.length > 1);
        final long[] uids = new long[accounts.length];
        for (int i = 0; i < accounts.length; ++i) {
            uids[i] = accounts[i].getId();
        }

        final long post1 = blogService.addBlogPost("post1", "content1", uids[0], Arrays.asList(javaTagId, cppTagId));
        final long post2 = blogService.addBlogPost("post2", "content2", uids[1], Arrays.asList(cppTagId));
        final long post3 = blogService.addBlogPost("post3", "content3", uids[1], Arrays.asList(javaTagId));
        final long post4 = blogService.addBlogPost("post4", "content4", uids[0], Arrays.asList(javaTagId));
        final long post5 = blogService.addBlogPost("post5", "content5", uids[0], Arrays.asList(cppTagId));

        final long[] allPosts = new long[] { post1, post2, post3, post4, post5 };


        // check post 1, 2, 3, 4, 5
        {
            final BlogPost[] posts = blogService.getBlogPosts(Arrays.asList(post1, post2, post3, post4, post5))
                    .toArray(new BlogPost[0]);

            final class ComparisonHelper {
                void assertPostEquals(int index, int uidIndex, Long ... tags) {
                    final int contentIndex = index + 1;
                    assertEquals("post" + contentIndex, posts[index].getTitle());
                    assertEquals("content" + contentIndex, posts[index].getContent());
                    assertEquals(uids[uidIndex], posts[index].getAuthorId());
                    assertEquals(allPosts[index], posts[index].getId());

                    if (index > 0) {
                        assertTrue("Created date shall be greater than of previous post",
                                posts[index].getCreated().compareTo(posts[index-1].getCreated()) >= 0);
                    }

                    assertArrayEquals(tags,
                            blogService.getBlogPostTags(posts[index].getId()).toArray(new Long[0]));
                }
            }
            final ComparisonHelper helper = new ComparisonHelper();

            helper.assertPostEquals(0, 0, javaTagId, cppTagId);
            helper.assertPostEquals(1, 1, cppTagId);
            helper.assertPostEquals(2, 1, javaTagId);
            helper.assertPostEquals(3, 0, javaTagId);
            helper.assertPostEquals(4, 0, cppTagId);
        }
    }


    @Test
    public void testComments() {
        final long javaTagId = blogService.addBlogTag("java");
        final long cppTagId = blogService.addBlogTag("cpp");

        final UserAccount[] accounts = userService.getUserAccounts(0, 9).getItems().toArray(new UserAccount[0]);
        assertTrue(accounts.length > 4);
        final long[] uids = new long[accounts.length];
        for (int i = 0; i < accounts.length; ++i) {
            uids[i] = accounts[i].getId();
        }

        final long[] posts = new long[] {
                blogService.addBlogPost("post0", "content0", uids[0], Arrays.asList(javaTagId, cppTagId)),
                blogService.addBlogPost("post1", "content1", uids[1], Arrays.asList(cppTagId)),
                blogService.addBlogPost("post2", "content2", uids[1], Arrays.asList(javaTagId))
        };

        // check for absence of child post comments
        for (int i = 0; i < posts.length; ++i) {
            final Chunk<Long> comments = blogService.getPostComments(posts[i], 0, 9);
            TestUtil.assertChunkEquals(comments, 0);
        }

        // check for absence of user comments
        for (int i = 0; i < uids.length; ++i) {
            final Chunk<Long> comments = blogService.getUserComments(uids[i], 0, 9);
            TestUtil.assertChunkEquals(comments, 0);
        }

        /*
        ======== Add comments as follows:

        post0 (u0)
          |
          +-comment1 (u2)
            |
            +-comment2 (u1)
            +-comment5 (u0)
            | |
            | +-comment7 (u1)
            |
            +-comment6 (u2)

        post1 (u1)
          |
          +-comment3 (u0)
          +-comment4 (u0)

        post2 (u1)
          |
          +-comment0 (u0)
         */

        final long[] comments;
        {
            final long c0 = blogService.addBlogComment(posts[2], null, uids[0], "comment0");
            final long c1 = blogService.addBlogComment(posts[0], null, uids[2], "comment1");
            final long c2 = blogService.addBlogComment(posts[0], c1,   uids[1], "comment2");
            final long c3 = blogService.addBlogComment(posts[1], null, uids[0], "comment3");
            final long c4 = blogService.addBlogComment(posts[1], null, uids[0], "comment4");
            final long c5 = blogService.addBlogComment(posts[0], c1,   uids[0], "comment5");
            final long c6 = blogService.addBlogComment(posts[0], null, uids[2], "comment6");
            final long c7 = blogService.addBlogComment(posts[0], c5,   uids[1], "comment7");
            
            comments = new long[] { c0, c1, c2, c3, c4, c5, c6, c7  };
        }

        // ---------------------------------------------------------------------------------

        // check user #0's comments
        TestUtil.assertChunkEquals(blogService.getUserComments(uids[0], 0, 9),
                4, comments[0], comments[3], comments[4], comments[5]);

        // check user #1's comments
        TestUtil.assertChunkEquals(blogService.getUserComments(uids[1], 0, 9),
                2, comments[2], comments[7]);

        // check user #2's comments
        TestUtil.assertChunkEquals(blogService.getUserComments(uids[2], 0, 9),
                2, comments[1], comments[6]);

        // ---------------------------------------------------------------------------------

        // check post #0 comment
        TestUtil.assertChunkEquals(blogService.getPostComments(posts[0], 0, 9),
                2, comments[1], comments[6]);

        // check post #1 comment
        TestUtil.assertChunkEquals(blogService.getPostComments(posts[1], 0, 9),
                2, comments[3], comments[4]);

        // check post #2 comment
        TestUtil.assertChunkEquals(blogService.getPostComments(posts[2], 0, 9),
                1, comments[0]);

        // ---------------------------------------------------------------------------------

        TestUtil.assertChunkEquals(blogService.getChildComments(comments[1], 0, 9),
                2, comments[2], comments[5]);

        TestUtil.assertChunkEquals(blogService.getChildComments(comments[5], 0, 9),
                1, comments[7]);

        TestUtil.assertChunkEquals(blogService.getChildComments(comments[6], 0, 9), 0);
    }
}
