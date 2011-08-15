package org.microblog.exposure.shared.model;

import java.util.Collection;

/**
 * Represents information related for submitting new blog post.
 */
public final class NewBlogPost {
    private String title;

    private String content;

    private long authorId;

    private Collection<Long> tags;


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

    public Collection<Long> getTags() {
        return tags;
    }

    public void setTags(Collection<Long> tags) {
        this.tags = tags;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewBlogPost)) return false;

        NewBlogPost that = (NewBlogPost) o;

        if (authorId != that.authorId) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
