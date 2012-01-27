package com.alexshabanov.rwapp.model.user;

import com.alexshabanov.rwapp.model.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents the unique user's role.
 */
@Entity
public class UserRole extends DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 64, unique = true)
    private String code;

    public UserRole() {}

    public UserRole(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return !(code != null ? !code.equals(userRole.code) : userRole.code != null) && !(id != null ? !id.equals(userRole.id) : userRole.id != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + getId() +
                ", code='" + getCode() + '\'' +
                '}';
    }
}
