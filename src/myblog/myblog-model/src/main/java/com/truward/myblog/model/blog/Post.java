package com.truward.myblog.model.blog;

import com.truward.myblog.model.PersistentBean;
import com.truward.myblog.model.user.Profile;

import java.util.Date;
import java.util.List;

/**
 * Represents a single blog post.
 */
public class Post implements PersistentBean {
    private long id;

    private String title;

    private String content;

    private Date created;

    private Date updated;

    private Profile author;

    private List<Comment> comments;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
