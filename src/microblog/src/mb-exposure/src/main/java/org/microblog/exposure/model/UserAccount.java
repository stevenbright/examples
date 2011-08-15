package org.microblog.exposure.model;

import java.util.Date;

/**
 * Represents user account.
 */
public class UserAccount {
    private long id;

    private String login;

    private String email;

    private String avatarUrl;

    private Date created;


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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
        if (!(o instanceof UserAccount)) return false;

        UserAccount account = (UserAccount) o;

        if (id != account.id) return false;
        if (avatarUrl != null ? !avatarUrl.equals(account.avatarUrl) : account.avatarUrl != null) return false;
        if (created != null ? !created.equals(account.created) : account.created != null) return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (login != null ? !login.equals(account.login) : account.login != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
