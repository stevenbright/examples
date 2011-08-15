package com.truward.hiberdemo.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Person {
    private int id;
    private String name;
    private int age;
    private Date created;
    private Set<Role> roles = new HashSet<Role>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        String rolesStr = null;
        for (final Role r : roles) {
            rolesStr = (rolesStr == null ? "" : rolesStr + ", ") + r.getName();
        }

        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", created=" + created +
                ", roles=" + rolesStr +
                '}';
    }
}
