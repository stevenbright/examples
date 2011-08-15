package com.truward.jdoblog.services.domain;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.Set;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class BlogPost {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    long id;

    String title;

    String content;

    Date created;

    Date updated;

    @Persistent
    Account author;

    @Persistent(mappedBy = "parentPost")
    Set<BlogComment> childComments;


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

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Set<BlogComment> getChildComments() {
        return childComments;
    }

    public void setChildComments(Set<BlogComment> childComments) {
        this.childComments = childComments;
    }
}
