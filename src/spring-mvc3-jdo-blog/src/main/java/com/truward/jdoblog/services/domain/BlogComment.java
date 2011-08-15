package com.truward.jdoblog.services.domain;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.Set;

@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "BLOG_COMMENT", detachable = "true")
public class BlogComment {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;

    @Persistent
    private Account author;

    @Persistent
    private BlogPost parentPost;

    @Persistent
    private BlogComment parentComment;

    @Column(jdbcType = "VARCHAR", length = 256, allowsNull = "false")
    private String content;

    @Column(allowsNull = "false")
    private Date created;

    private Date updated;

    @Persistent
    private Set<BlogComment> childComments;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public BlogPost getParentPost() {
        return parentPost;
    }

    public void setParentPost(BlogPost parentPost) {
        this.parentPost = parentPost;
    }

    public BlogComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(BlogComment parentComment) {
        this.parentComment = parentComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<BlogComment> getChildComments() {
        return childComments;
    }

    public void setChildComments(Set<BlogComment> childComments) {
        this.childComments = childComments;
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
}
