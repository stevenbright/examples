package com.truward.jdoblog.services.domain;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Account {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    long id;

    @Column(name = "DISPLAY_NAME", length = 64, jdbcType = "VARCHAR", allowsNull = "false")
    @Unique()
    String displayName;

    @Column(name = "AVATAR_URL", length = 256, jdbcType = "VARCHAR", allowsNull = "false")
    String avatarUrl;

    @Persistent(mappedBy = "author")
    List<BlogPost> posts;

    @Persistent(mappedBy = "author")
    List<BlogComment> comments;

    @Persistent(table = "ACCOUNT_ROLES")
    @Join(column = "ACCOUNT_ID")
    @Element(column = "ROLE_ID")
    Set<Role> roles;

    @Column(allowsNull = "false")
    Date created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    public List<BlogComment> getComments() {
        return comments;
    }

    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
