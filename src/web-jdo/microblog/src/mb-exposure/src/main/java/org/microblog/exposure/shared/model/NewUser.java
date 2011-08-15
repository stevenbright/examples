package org.microblog.exposure.shared.model;

/**
 * New user profile info.
 */
public final class NewUser {
    private String login;

    private String password;

    private String email;

    private String avatarUrl;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUser newUser = (NewUser) o;

        if (avatarUrl != null ? !avatarUrl.equals(newUser.avatarUrl) : newUser.avatarUrl != null) return false;
        if (email != null ? !email.equals(newUser.email) : newUser.email != null) return false;
        if (login != null ? !login.equals(newUser.login) : newUser.login != null) return false;
        if (password != null ? !password.equals(newUser.password) : newUser.password != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }
}
