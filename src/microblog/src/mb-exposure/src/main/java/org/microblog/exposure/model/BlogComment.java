package org.microblog.exposure.model;

import java.util.Date;

/**
 * Comment to the post entry.
 */
public class BlogComment {
    public long id;

    public long parentPostId;

    public Long parentCommentId;

    public long authorId;

    public boolean hasChildComments;

    public String content;

    public Date created;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(long parentPostId) {
        this.parentPostId = parentPostId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public boolean isHasChildComments() {
        return hasChildComments;
    }

    public void setHasChildComments(boolean hasChildComments) {
        this.hasChildComments = hasChildComments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogComment)) return false;

        BlogComment that = (BlogComment) o;

        if (authorId != that.authorId) return false;
        if (hasChildComments != that.hasChildComments) return false;
        if (id != that.id) return false;
        if (parentPostId != that.parentPostId) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (parentCommentId != null ? !parentCommentId.equals(that.parentCommentId) : that.parentCommentId != null)
            return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (parentPostId ^ (parentPostId >>> 32));
        result = 31 * result + (parentCommentId != null ? parentCommentId.hashCode() : 0);
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (hasChildComments ? 1 : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
