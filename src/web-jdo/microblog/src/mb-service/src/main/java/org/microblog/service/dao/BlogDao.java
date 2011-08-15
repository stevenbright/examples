package org.microblog.service.dao;

import org.microblog.exposure.model.BlogComment;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * Represents data access object for blog.
 */
public interface BlogDao {
    Collection<BlogTag> getAllTags() throws DataAccessException;

    Collection<Long> getPostTags(long postId) throws DataAccessException;


    long addNewTag(String name) throws DataAccessException;


    long addPost(String title, String content, long authorId) throws DataAccessException;

    long addComment(long parentPostId, Long parentCommentId, long authorId, String content) throws DataAccessException;


    void addTagToPost(long postId, long tagId) throws DataAccessException;


    BlogPost getPost(long postId) throws DataAccessException;

    BlogComment getComment(long commentId) throws DataAccessException;


    Collection<Long> getUserPosts(long userId, int offset, int limit) throws DataAccessException;

    int getUserPostCount(long userId) throws DataAccessException;


    Collection<Long> getTaggedPosts(long tagId, int offset, int limit) throws DataAccessException;

    int getTaggedPostCount(long tagId) throws DataAccessException;


    Collection<Long> getUserComments(long userId, int offset, int limit) throws DataAccessException;

    int getUserCommentCount(long userId) throws DataAccessException;


    Collection<Long> getPostComments(long postId, int offset, int limit) throws DataAccessException;

    int getPostCommentCount(long postId) throws DataAccessException;


    Collection<Long> getChildComments(long commentId, int offset, int limit) throws DataAccessException;

    int getChildCommentCount(long commentId) throws DataAccessException;
}
