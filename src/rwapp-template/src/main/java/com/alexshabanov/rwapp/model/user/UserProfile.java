package com.alexshabanov.rwapp.model.user;

import com.alexshabanov.rwapp.model.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Represents a single user's profile.
 */
@Entity
public class UserProfile extends DomainObject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 64)
    private String password;

    @NotNull
    @OneToMany(targetEntity = UserAccount.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<UserAccount> accounts;

    @NotNull
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> roles;

    @NotNull
    private Date created;

    public UserProfile() {}

    public UserProfile(Long id, String password, Collection<UserAccount> accounts, Set<UserRole> roles, Date created) {
        this.id = id;
        this.password = password;
        this.accounts = accounts;
        this.roles = roles;
        this.created = created;
    }
    
    public UserProfile(String password, Collection<UserAccount> accounts, Set<UserRole> roles) {
        this(null, password, accounts, roles, new Date());
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

    public Date getCreated() {
        return created;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile profile = (UserProfile) o;

        if (accounts != null ? !accounts.equals(profile.accounts) : profile.accounts != null) return false;
        if (created != null ? !created.equals(profile.created) : profile.created != null) return false;
        if (id != null ? !id.equals(profile.id) : profile.id != null) return false;
        if (password != null ? !password.equals(profile.password) : profile.password != null) return false;
        if (roles != null ? !roles.equals(profile.roles) : profile.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + getId() +
                ", password=" + getPassword() +
                ", accounts=" + getAccounts() +
                ", roles=" + getRoles() +
                ", created=" + getCreated() +
                '}';
    }
}
