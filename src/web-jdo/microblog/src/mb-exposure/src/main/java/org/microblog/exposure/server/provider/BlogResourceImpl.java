package org.microblog.exposure.server.provider;

import org.microblog.exposure.model.BlogComment;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.server.AbstractResource;
import org.microblog.exposure.server.service.BlogService;
import org.microblog.exposure.shared.ResourceTraits;
import org.microblog.exposure.shared.model.NewBlogComment;
import org.microblog.exposure.shared.model.NewBlogPost;
import org.microblog.exposure.shared.provider.BlogResource;

import javax.ws.rs.Path;
import java.util.Collection;

/**
 * Implements blog resource.
 */
@Path("/blog")
public final class BlogResourceImpl extends AbstractResource implements BlogResource {
    /**
     * {@inheritDoc}
     */
    public Collection<BlogTag> getAllTags() {
        return getService(BlogService.class).getAllTags();
    }

    /**
     * {@inheritDoc}
     */
    public Chunk<Long> getUserPosts(long userId, Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = ResourceTraits.DEFAULT_LIMIT;
        }

        return getService(BlogService.class).getUserPosts(userId, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    public Chunk<Long> getTaggedPosts(long tagId, Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = ResourceTraits.DEFAULT_LIMIT;
        }

        return getService(BlogService.class).getTaggedPosts(tagId, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    public Chunk<Long> getUserComment(long userId, Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = ResourceTraits.DEFAULT_LIMIT;
        }

        return getService(BlogService.class).getUserComments(userId, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    public Chunk<Long> getPostComment(long postId, Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = ResourceTraits.DEFAULT_LIMIT;
        }

        return getService(BlogService.class).getPostComments(postId, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    public Chunk<Long> getChildComment(long commentId, Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = ResourceTraits.DEFAULT_LIMIT;
        }

        return getService(BlogService.class).getChildComments(commentId, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<BlogPost> getBlogPosts(Collection<Long> postIds) {
        return getService(BlogService.class).getBlogPosts(postIds);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<BlogComment> getBlogComments(Collection<Long> postIds) {
        return getService(BlogService.class).getBlogComments(postIds);
    }

    /**
     * {@inheritDoc}
     */
    public long addBlogPost(NewBlogPost newBlogPost) {
        return getService(BlogService.class).addBlogPost(newBlogPost.getTitle(), newBlogPost.getContent(),
                newBlogPost.getAuthorId(), newBlogPost.getTags());
    }

    /**
     * {@inheritDoc}
     */
    public long addBlogComment(NewBlogComment newBlogComment) {
        return getService(BlogService.class).addBlogComment(newBlogComment.getParentPostId(),
                newBlogComment.getParentCommentId(), newBlogComment.getAuthorId(), newBlogComment.getContent());
    }

    /**
     * {@inheritDoc}
     */
    public long addBlogTag(String name) {
        return getService(BlogService.class).addBlogTag(name);
    }
}
