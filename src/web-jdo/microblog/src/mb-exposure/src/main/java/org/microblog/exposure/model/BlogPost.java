package org.microblog.exposure.model;

import java.util.Date;

/**
 * Represents single post to the blog.
 */
public class BlogPost {
    private long id;

    private String title;

    private String content;

    private long authorId;

    private Date created;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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
        if (!(o instanceof BlogPost)) return false;

        BlogPost blogPost = (BlogPost) o;

        if (authorId != blogPost.authorId) return false;
        if (id != blogPost.id) return false;
        if (content != null ? !content.equals(blogPost.content) : blogPost.content != null) return false;
        if (created != null ? !created.equals(blogPost.created) : blogPost.created != null) return false;
        if (title != null ? !title.equals(blogPost.title) : blogPost.title != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BlogPost");
        sb.append("{id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", authorId=").append(authorId);
        sb.append(", created=").append(created.getTime());
        sb.append('}');
        return sb.toString();
    }
}
