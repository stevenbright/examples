package org.microblog.service.dao.jdbc;

import org.microblog.exposure.model.BlogComment;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.microblog.service.dao.BlogDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

/**
 * Implements blog DAO over JDBC support.
 */
public class BlogDaoJdbcImpl extends JdbcDaoSupport implements BlogDao {

    /**
     * Implements RowMapper for result sets that returns set of IDs.
     */
    private static final class IdMapper implements RowMapper<Long> {
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong(1);
        }
    }


    /**
     * {@inheritDoc}
     */
    public Collection<BlogTag> getAllTags() throws DataAccessException {
        return getJdbcTemplate().query("select ID, TAG_NAME from BLOG_TAG order by ID", new RowMapper<BlogTag>() {
            public BlogTag mapRow(ResultSet rs, int rowNum) throws SQLException {
                final BlogTag result = new BlogTag();
                result.setId(rs.getLong(1));
                result.setName(rs.getString(2));
                return result;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Long> getPostTags(long postId) throws DataAccessException {
        return getJdbcTemplate().query("select TAG_ID from BLOG_POST_TAGS where POST_ID=? order by TAG_ID",
                new IdMapper(), postId);
    }

    /**
     * {@inheritDoc}
     */
    public long addNewTag(String name) throws DataAccessException {
        getJdbcTemplate().update("insert into BLOG_TAG (TAG_NAME) values (?)", name);
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public void addTagToPost(long postId, long tagId) throws DataAccessException {
        getJdbcTemplate().update("insert into BLOG_POST_TAGS (POST_ID, TAG_ID) values (?, ?)", postId, tagId);
    }

    /**
     * {@inheritDoc}
     */
    public long addPost(String title, String content, long authorId) throws DataAccessException {
        getJdbcTemplate().update("insert into BLOG_POST (TITLE, CONTENT, AUTHOR_ID, CREATED) " +
                "values (?, ?, ?, now())", title, content, authorId);
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public long addComment(long parentPostId, Long parentCommentId, long authorId, String content) throws DataAccessException {
        getJdbcTemplate().update("insert into BLOG_COMMENT " +
                "(PARENT_POST_ID, PARENT_COMMENT_ID, AUTHOR_ID, CONTENT, CREATED) " +
                "values (?, ?, ?, ?, now())",
                parentPostId, parentCommentId, authorId, content);
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public BlogPost getPost(long postId) throws DataAccessException {
        return getJdbcTemplate().queryForObject("select ID, AUTHOR_ID, TITLE, CONTENT, CREATED " +
                "from BLOG_POST where ID=?",
                new RowMapper<BlogPost>() {
                    public BlogPost mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final BlogPost result = new BlogPost();
                        result.setId(rs.getLong(1));
                        result.setAuthorId(rs.getLong(2));
                        result.setTitle(rs.getString(3));
                        result.setContent(rs.getString(4));
                        result.setCreated(rs.getTimestamp(5));
                        return result;
                    }
                }, postId);
    }

    /**
     * {@inheritDoc}
     */
    public BlogComment getComment(long commentId) throws DataAccessException {
        return getJdbcTemplate().queryForObject("select " +
                "ID, AUTHOR_ID, PARENT_POST_ID, PARENT_COMMENT_ID, CREATED, CONTENT " +
                "from BLOG_COMMENT where ID=?",
                new RowMapper<BlogComment>() {
                    public BlogComment mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final BlogComment result = new BlogComment();
                        result.setId(rs.getLong(1));
                        result.setAuthorId(rs.getLong(2));
                        result.setParentPostId(rs.getLong(3));
                        result.setParentCommentId(rs.getLong(4));
                        result.setCreated(rs.getTimestamp(5));
                        result.setContent(rs.getString(6));
                        return result;
                    }
                }, commentId);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Long> getUserPosts(long userId, int offset, int limit) throws DataAccessException {
        if (limit == 0) {
            return Collections.emptyList();
        }

        return getJdbcTemplate().query("select ID from BLOG_POST where AUTHOR_ID=? " +
                "order by CREATED, ID limit ? offset ?",
                new IdMapper(), userId, limit, offset);
    }

    /**
     * {@inheritDoc}
     */
    public int getUserPostCount(long userId) throws DataAccessException {
        return getJdbcTemplate().queryForInt("select count(0) from BLOG_POST where AUTHOR_ID=?", userId);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Long> getTaggedPosts(long tagId, int offset, int limit) throws DataAccessException {
        if (limit == 0) {
            return Collections.emptyList();
        }

        return getJdbcTemplate().query("select P.ID from BLOG_POST as P " +
                "inner join BLOG_POST_TAGS as G on P.ID=G.POST_ID " +
                "where G.TAG_ID=? " +
                "order by P.CREATED, P.ID limit ? offset ?",
                new IdMapper(), tagId, limit, offset);
    }

    /**
     * {@inheritDoc}
     */
    public int getTaggedPostCount(long tagId) throws DataAccessException {
        return getJdbcTemplate().queryForInt("select count(0) from BLOG_POST as P " +
                "inner join BLOG_POST_TAGS as G on P.ID=G.POST_ID " +
                "where G.TAG_ID=?", tagId);
    }


    /**
     * {@inheritDoc}
     */
    public Collection<Long> getUserComments(long userId, int offset, int limit) throws DataAccessException {
        if (limit == 0) {
            return Collections.emptyList();
        }

        return getJdbcTemplate().query("select ID from BLOG_COMMENT " +
                "where AUTHOR_ID=? order by CREATED, ID limit ? offset ?",
                new IdMapper(), userId, limit, offset);
    }

    /**
     * {@inheritDoc}
     */
    public int getUserCommentCount(long userId) throws DataAccessException {
        return getJdbcTemplate().queryForInt("select count(0) from BLOG_COMMENT where AUTHOR_ID=?", userId);
    }


    /**
     * {@inheritDoc}
     */
    public Collection<Long> getPostComments(long postId, int offset, int limit) throws DataAccessException {
        if (limit == 0) {
            return Collections.emptyList();
        }

        return getJdbcTemplate().query("select ID from BLOG_COMMENT where " +
                "PARENT_POST_ID=? and PARENT_COMMENT_ID is null " +
                "order by CREATED, ID " +
                "limit ? offset ?",
                new IdMapper(), postId, limit, offset);
    }

    /**
     * {@inheritDoc}
     */
    public int getPostCommentCount(long postId) throws DataAccessException {
        return getJdbcTemplate().queryForInt("select count(0) from BLOG_COMMENT where " +
                "PARENT_POST_ID=? and PARENT_COMMENT_ID is null", postId);
    }


    /**
     * {@inheritDoc}
     */
    public Collection<Long> getChildComments(long commentId, int offset, int limit) throws DataAccessException {
        if (limit == 0) {
            return Collections.emptyList();
        }

        return getJdbcTemplate().query("select ID from BLOG_COMMENT " +
                "where PARENT_COMMENT_ID=? order by CREATED, ID limit ? offset ?",
                new IdMapper(), commentId, limit, offset);
    }

    /**
     * {@inheritDoc}
     */
    public int getChildCommentCount(long commentId) throws DataAccessException {
        return getJdbcTemplate().queryForInt("select count(0) from BLOG_COMMENT where PARENT_COMMENT_ID=?", commentId);
    }
}
