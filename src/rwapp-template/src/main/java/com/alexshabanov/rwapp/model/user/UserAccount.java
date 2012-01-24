package com.alexshabanov.rwapp.model.user;

import com.alexshabanov.rwapp.model.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents extended information about the user's account.
 */
@Entity
public class UserAccount extends DomainObject {

    public static enum Kind {
        PHONE,
        EMAIL,
        NICKNAME
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 64)
    @Column(length = 64, nullable = false, unique = true)
    private String alias;

    @NotNull
    @Column(nullable = false)
    private Kind aliasKind;

    public UserAccount() {}

    public UserAccount(Long id, String alias, Kind aliasKind) {
        this.id = id;
        this.alias = alias;
        this.aliasKind = aliasKind;
    }

    public Long getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public Kind getAliasKind() {
        return aliasKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (aliasKind != that.aliasKind) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (aliasKind != null ? aliasKind.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + getId() +
                ", alias='" + getAlias() + '\'' +
                ", aliasKind=" + getAliasKind() +
                '}';
    }
}
