package com.alexshabanov.rwapp.model.user;

import com.alexshabanov.rwapp.model.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Represents a single user's profile.
 */
@Entity
public class UserProfile extends DomainObject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String password;

    @NotNull
    @OneToMany(targetEntity = UserAccount.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<UserAccount> accounts;

    public UserProfile() {}

    public UserProfile(Long id, String password, Collection<UserAccount> accounts) {
        this.id = id;
        this.password = password;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public Collection<UserAccount> getAccounts() {
        return accounts;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        if (accounts != null ? !accounts.equals(that.accounts) : that.accounts != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + getId() +
                ", password=" + getPassword() +
                ", accounts=" + getAccounts() +
                '}';
    }
}
