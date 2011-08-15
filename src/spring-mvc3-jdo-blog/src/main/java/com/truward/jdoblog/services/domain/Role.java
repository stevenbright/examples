package com.truward.jdoblog.services.domain;

import javax.jdo.annotations.*;
import java.util.Set;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Role {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;

    @Column(name = "ROLE_NAME", length = 16, jdbcType = "VARCHAR", allowsNull = "false")
    private String name;

    @Persistent(mappedBy = "roles")
    private Set<Account> assignees;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<Account> assignees) {
        this.assignees = assignees;
    }
}
