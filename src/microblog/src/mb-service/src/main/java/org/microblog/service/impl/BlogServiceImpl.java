package org.microblog.service.impl;

import org.microblog.exposure.model.BlogComment;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.server.exception.ServiceException;
import org.microblog.exposure.server.service.BlogService;
import org.microblog.service.dao.BlogDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Blog service implementation.
 */
@Transactional
public final class BlogServiceImpl implements BlogService {
    @Resource
    private BlogDao blogDao;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Collection<BlogTag> getAllTags() throws ServiceException {
        return blogDao.getAllTags();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Collection<Long> getBlogPostTags(long postId) throws ServiceException {
        return blogDao.getPostTags(postId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Chunk<Long> getUserPosts(long userId, int offset, int limit) throws ServiceException {
        final Chunk<Long> result = new Chunk<Long>();
        result.setItems(blogDao.getUserPosts(userId, offset, limit));
        result.setCount(blogDao.getUserPostCount(userId));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Chunk<Long> getTaggedPosts(long tagId, int offset, int limit) throws ServiceException {
        final Chunk<Long> result = new Chunk<Long>();
        result.setItems(blogDao.getTaggedPosts(tagId, offset, limit));
        result.setCount(blogDao.getTaggedPostCount(tagId));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Chunk<Long> getUserComments(long userId, int offset, int limit) throws ServiceException {
        final Chunk<Long> result = new Chunk<Long>();
        result.setItems(blogDao.getUserComments(userId, offset, limit));
        result.setCount(blogDao.getUserCommentCount(userId));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Chunk<Long> getPostComments(long postId, int offset, int limit) throws ServiceException {
        final Chunk<Long> result = new Chunk<Long>();
        result.setItems(blogDao.getPostComments(postId, offset, limit));
        result.setCount(blogDao.getPostCommentCount(postId));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Chunk<Long> getChildComments(long commentId, int offset, int limit) throws ServiceException {
        final Chunk<Long> result = new Chunk<Long>();
        result.setItems(blogDao.getChildComments(commentId, offset, limit));
        result.setCount(blogDao.getChildCommentCount(commentId));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Collection<BlogPost> getBlogPosts(Collection<Long> postIds) throws ServiceException {
        final List<BlogPost> result = new ArrayList<BlogPost>();

        for (final long postId : postIds) {
            result.add(blogDao.getPost(postId));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Collection<BlogComment> getBlogComments(Collection<Long> commentIds) throws ServiceException {
        final List<BlogComment> result = new ArrayList<BlogComment>();

        for (final long commentId : commentIds) {
            result.add(blogDao.getComment(commentId));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public long addBlogPost(String title, String content, long authorId, Collection<Long> tags) throws ServiceException {
        final long postId = blogDao.addPost(title, content, authorId);

        for (final long tagId : tags) {
            blogDao.addTagToPost(postId, tagId);
        }

        return postId;
    }

    /**
     * {@inheritDoc}
     */
    public long addBlogComment(long parentPostId, Long parentCommentId, long authorId, String content) throws ServiceException {
        return blogDao.addComment(parentPostId, parentCommentId, authorId, content);
    }

    /**
     * {@inheritDoc}
     */
    public long addBlogTag(String name) throws ServiceException {
        return blogDao.addNewTag(name);
    }
}
