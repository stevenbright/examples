package com.truward.myblog.model.user;

import com.truward.myblog.model.PersistentBean;
import com.truward.myblog.model.blog.Comment;
import com.truward.myblog.model.blog.Post;

import java.util.Date;
import java.util.List;

/**
 * Represents full user profile.
 */
public class Profile implements PersistentBean {
    private long id;

    private String login;

    private String email;

    private String password;

    private Date created;

    private List<Role> roles;

    private List<Post> posts;

    private List<Comment> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
