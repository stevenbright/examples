package org.microblog.exposure;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import org.junit.Before;
import org.junit.Test;
import org.microblog.exposure.model.*;
import org.microblog.exposure.server.service.BlogService;
import org.microblog.exposure.server.service.UserService;
import org.microblog.exposure.shared.ResourceTraits;
import org.microblog.exposure.shared.model.NewBlogComment;
import org.microblog.exposure.shared.model.NewBlogPost;
import org.microblog.exposure.shared.model.NewUser;
import org.microblog.exposure.shared.provider.GsonAwareContextResolver;
import org.microblog.exposure.shared.provider.GsonMessageBodyHandler;
import org.microblog.exposure.testutil.provider.MockServiceContextResolver;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
public class RestApiTest extends JerseyTest {
    /**
     * Creates custom REST client config which is mandatory since we don't use any JSON providers.
     * @return Jersey Client Config with the required classes to read/write in(out)coming data.
     */
    private static ClientConfig createClientConfig() {
        final ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(GsonMessageBodyHandler.class);
        config.getClasses().add(GsonAwareContextResolver.class);
        return config;
    }

    private static final String[] SERVER_PACKAGES = new String[] {
            "org.microblog.exposure.shared.provider",
            "org.microblog.exposure.server.provider",
            "org.microblog.exposure.testutil.provider"
    };

    /**
     * Public ctor
     * @throws TestContainerException On error
     */
    public RestApiTest() throws TestContainerException {
        super(new WebAppDescriptor.Builder(SERVER_PACKAGES)
                .clientConfig(createClientConfig())
                .contextPath("/")
                .build());
    }


    private UserService userService;

    private BlogService blogService;

    @Before
    public void setupServices() {
        userService = MockServiceContextResolver.USER_SERVICE;
        blogService = MockServiceContextResolver.BLOG_SERVICE;
    }




    @Test
    public void testUserResourceEndpoint() {
        reset(userService);

        final UserAccount account = new UserAccount();
        account.setId(1L);
        account.setLogin("login");
        expect(userService.getUserAccount(1L)).andReturn(account);

        final Collection<UserRole> roles;
        {
            final UserRole role1 = new UserRole();
            role1.setId(1L);
            role1.setName("ROLE_USER");

            final UserRole role2 = new UserRole();
            role2.setId(2L);
            role2.setName("ROLE_ADMIN");

            roles = Arrays.asList(role1, role2);
        }
        expect(userService.getUserRoles(10L)).andReturn(roles);

        expect(userService.addUserRole("ROLE_MODERATOR")).andReturn(3L);

        final NewUser newUser = new NewUser();
        newUser.setAvatarUrl("avatarUrl");
        newUser.setEmail("email");
        newUser.setLogin("login");
        newUser.setPassword("password");
        expect(userService.registerUser(newUser.getLogin(), newUser.getPassword(),
                newUser.getEmail(), newUser.getAvatarUrl())).andReturn(2L);

        userService.assignUserRole(eq(7L), eq(70L));
        expectLastCall().once();

        userService.revokeUserRole(eq(3L), eq(30L));
        expectLastCall().once();

        //-----------------------------------------------------------------
        replay(userService);
        final WebResource resource = resource();

        // getUserAccount
        final UserAccount resultAccount = resource.path("/user/account")
                .queryParam("userId", "1")
                .type(MediaType.APPLICATION_JSON)
                .get(UserAccount.class);
        assertEquals(account, resultAccount);

        // getUserRoles
        final Collection<UserRole> resultRoles = resource.path("/user/assigned/roles")
                .queryParam("userId", "10")
                .type(MediaType.APPLICATION_JSON)
                .get(new GenericType<Collection<UserRole>>() {});
        assertThat(roles, is(resultRoles));

        // addUserRole
        final long resultRoleId = resource.path("/user/role")
                .queryParam("name", "ROLE_MODERATOR")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Long.class);
        assertEquals(3L, resultRoleId);

        // registerUser
        final long resultUserId = resource.path("/user/account")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Long.class, newUser);
        assertEquals(2L, resultUserId);

        // assignUserRole
        resource.path("/user/assigned/role")
                .queryParam("userId", "7")
                .queryParam("roleId", "70")
                .post();

        // revokeUserRole
        resource.path("/user/assigned/role")
                .queryParam("userId", "3")
                .queryParam("roleId", "30")
                .delete();

        verify(userService);
    }

    @Test
    public void testBlogResourceEndpoint() {
        reset(blogService);

        final Collection<BlogTag> tags;
        {
            final BlogTag tag = new BlogTag();
            tag.setId(1L);
            tag.setName("tag");
            tags = Arrays.asList(tag);
        }
        expect(blogService.getAllTags()).andReturn(tags);


        final Chunk<Long> testChunk = new Chunk<Long>();
        testChunk.setCount(10);
        testChunk.setItems(Arrays.asList(1L, 2L, 3L, 4L));

        expect(blogService.getUserPosts(3, 1, 4)).andReturn(testChunk);
        expect(blogService.getTaggedPosts(5, 1, 4)).andReturn(testChunk);
        expect(blogService.getUserComments(7, 1, 4)).andReturn(testChunk);
        expect(blogService.getPostComments(9, 1, 4)).andReturn(testChunk);
        expect(blogService.getChildComments(11, 1, 4)).andReturn(testChunk);

        final Collection<Long> blogPostsIds = Arrays.asList(10L);
        final Collection<BlogPost> blogPosts;
        {
            final BlogPost post = new BlogPost();
            post.setAuthorId(1L);
            post.setContent("content");
            post.setCreated(new Date());
            post.setId(10L);
            post.setTitle("title");
            blogPosts = Arrays.asList(post);
        }
        expect(blogService.getBlogPosts(blogPostsIds)).andReturn(blogPosts);

        final Collection<Long> blogCommentsIds = Arrays.asList(12L);
        final Collection<BlogComment> blogComments;
        {
            final BlogComment comment = new BlogComment();
            comment.setAuthorId(123L);
            comment.setContent("content");
            comment.setCreated(new Date());
            comment.setHasChildComments(true);
            comment.setId(11L);
            comment.setParentCommentId(12L);
            comment.setParentPostId(13L);
            blogComments = Arrays.asList(comment);
        }
        expect(blogService.getBlogComments(blogCommentsIds)).andReturn(blogComments);


        final NewBlogPost newBlogPost = new NewBlogPost();
        newBlogPost.setAuthorId(1L);
        newBlogPost.setContent("content");
        newBlogPost.setTags(Arrays.asList(1L, 2L, 3L));
        newBlogPost.setTitle("title");
        final long postId = 20L;
        expect(blogService.addBlogPost(newBlogPost.getTitle(), newBlogPost.getContent(), newBlogPost.getAuthorId(),
                newBlogPost.getTags())).andReturn(postId);

        final NewBlogComment newBlogComment = new NewBlogComment();
        newBlogComment.setAuthorId(1L);
        newBlogComment.setContent("content");
        newBlogComment.setParentCommentId(234L);
        newBlogComment.setParentPostId(345L);
        final long commentId = 34L;
        expect(blogService.addBlogComment(newBlogComment.getParentPostId(), newBlogComment.getParentCommentId(),
                newBlogComment.getAuthorId(), newBlogComment.getContent())).andReturn(commentId);

        final String tagName = "tagName";
        final long tagId = 1235L;
        expect(blogService.addBlogTag(tagName)).andReturn(tagId);

        //-----------------------------------------------------------------
        replay(blogService);
        final WebResource resource = resource();

        // getAllTags
        final Collection<BlogTag> resultTags = resource.path("/blog/tags")
                .type(MediaType.APPLICATION_JSON)
                .get(new GenericType<Collection<BlogTag>>() {});
        assertEquals(tags, resultTags);

        class ChunkMatcherHelper {
            void checkResource(String path) {
                final Chunk<Long> resultChunk = resource.path(path)
                        .queryParam("offset", "1")
                        .queryParam("limit", "4")
                        .type(MediaType.APPLICATION_JSON)
                        .get(new GenericType<Chunk<Long>>() {
                        });
                assertThat(testChunk, is(resultChunk));
            }
        }
        final ChunkMatcherHelper chunkMatcherHelper = new ChunkMatcherHelper();

        // getUserPosts, getTaggedPosts, getUserComments, getPostComments, getChildComments
        chunkMatcherHelper.checkResource("/blog/user/3/posts");
        chunkMatcherHelper.checkResource("/blog/tag/5/posts");
        chunkMatcherHelper.checkResource("/blog/user/7/comments");
        chunkMatcherHelper.checkResource("/blog/post/9/comments");
        chunkMatcherHelper.checkResource("/blog/comment/11/comments");

        // getBlogPosts
        final Collection<BlogPost> resultBlogPost = resource.path("/blog/posts")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(new GenericType<Collection<BlogPost>>() {}, blogPostsIds);
        assertThat(blogPosts, is(resultBlogPost));

        // getBlogComments
        final Collection<BlogComment> resultBlogComments = resource.path("/blog/comments")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(new GenericType<Collection<BlogComment>>() {}, blogCommentsIds);
        assertThat(blogComments, is(resultBlogComments));

        // addBlogPost
        final long resultPostId = resource.path("/blog/post")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Long.class, newBlogPost);
        assertEquals(postId, resultPostId);

        // addBlogComment
        final long resultCommentId = resource.path("/blog/comment")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Long.class, newBlogComment);
        assertEquals(commentId, resultCommentId);

        // addBlogTag
        final long resultTagId = resource.path("/blog/tag")
                .queryParam("name", tagName)
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Long.class);
        assertEquals(tagId, resultTagId);

        verify(blogService);
    }

    @Test
    public void testUnspecifiedOffsetLimit() {
        reset(blogService);

        final Chunk<Long> testChunk = new Chunk<Long>();
        testChunk.setCount(4);
        testChunk.setItems(Arrays.asList(1L, 2L, 3L, 4L));

        expect(blogService.getUserPosts(1, 0, ResourceTraits.DEFAULT_LIMIT)).andReturn(testChunk);
        expect(blogService.getTaggedPosts(2, 0, ResourceTraits.DEFAULT_LIMIT)).andReturn(testChunk);
        expect(blogService.getUserComments(3, 0, ResourceTraits.DEFAULT_LIMIT)).andReturn(testChunk);
        expect(blogService.getPostComments(4, 0, ResourceTraits.DEFAULT_LIMIT)).andReturn(testChunk);
        expect(blogService.getChildComments(5, 0, ResourceTraits.DEFAULT_LIMIT)).andReturn(testChunk);

        //-----------------------------------------------------------------
        replay(blogService);
        final WebResource resource = resource();

        class ChunkMatcherHelper {
            void checkResource(String path) {
                final Chunk<Long> resultChunk = resource.path(path)
                        .type(MediaType.APPLICATION_JSON)
                        .get(new GenericType<Chunk<Long>>() {
                        });
                assertThat(testChunk, is(resultChunk));
            }
        }
        final ChunkMatcherHelper chunkMatcherHelper = new ChunkMatcherHelper();

        // getUserPosts, getTaggedPosts, getUserComments, getPostComments, getChildComments
        chunkMatcherHelper.checkResource("/blog/user/1/posts");
        chunkMatcherHelper.checkResource("/blog/tag/2/posts");
        chunkMatcherHelper.checkResource("/blog/user/3/comments");
        chunkMatcherHelper.checkResource("/blog/post/4/comments");
        chunkMatcherHelper.checkResource("/blog/comment/5/comments");

        verify(blogService);
    }
}
