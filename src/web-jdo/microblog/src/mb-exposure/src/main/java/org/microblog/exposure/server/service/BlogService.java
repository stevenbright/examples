package org.microblog.exposure.server.service;

import org.microblog.exposure.model.BlogComment;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.server.exception.ServiceException;

import java.util.Collection;

/**
 * Represents blog service.
 */
public interface BlogService extends BaseService {
    /**
     * Gets all the registered tags.
     * @return Registered tags collection.
     * @throws ServiceException On error.
     */
    Collection<BlogTag> getAllTags() throws ServiceException;

    Collection<Long> getBlogPostTags(long postId) throws ServiceException;



    Chunk<Long> getUserPosts(long userId, int offset, int limit) throws ServiceException;

    Chunk<Long> getTaggedPosts(long tagId, int offset, int limit) throws ServiceException;

    Chunk<Long> getUserComments(long userId, int offset, int limit) throws ServiceException;

    Chunk<Long> getPostComments(long postId, int offset, int limit) throws ServiceException;

    Chunk<Long> getChildComments(long commentId, int offset, int limit) throws ServiceException;



    Collection<BlogPost> getBlogPosts(Collection<Long> postIds) throws ServiceException;

    Collection<BlogComment> getBlogComments(Collection<Long> commentIds) throws ServiceException;


    long addBlogPost(String title, String content, long authorId, Collection<Long> tags) throws ServiceException;

    long addBlogComment(long parentPostId, Long parentCommentId, long authorId, String content) throws ServiceException;

    long addBlogTag(String name) throws ServiceException;
}
