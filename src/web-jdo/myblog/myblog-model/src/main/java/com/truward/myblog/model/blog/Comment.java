package com.truward.myblog.model.blog;

import com.truward.myblog.model.PersistentBean;
import com.truward.myblog.model.user.Profile;

import java.util.Date;

/**
 * Represents a comment to the blog post.
 */
public class Comment implements PersistentBean {
    private long id;

    private String content;

    private Date created;

    private Post post;

    private Profile author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }
}
