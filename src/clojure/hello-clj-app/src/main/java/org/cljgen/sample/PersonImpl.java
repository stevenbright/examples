package org.cljgen.sample;

import javax.annotation.Generated;

/**
 * Sample generated code.
 */
@Generated("aeroj")
public final class PersonImpl {

    // Class variables
    private final String fullName;
    private final int age;
    private final Long id;

    // Public constructor
    public PersonImpl(String fullName, int age, Long id) {
        this.fullName = fullName;
        this.age = age;
        this.id = id;
    }

    // Getters
    public final String getFullName() {
        return fullName;
    }
    public final int getAge() {
        return age;
    }
    public final Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PersonImpl rhs = (PersonImpl) o;

        if (fullName != null ? !fullName.equals(rhs.fullName) : rhs.fullName != null) return false;
        if (age != rhs.age) return false;
        if (id != null ? !id.equals(rhs.id) : rhs.id != null) return false;

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PersonImpl#{");
        builder.append(" fullName: ").append(fullName);
        builder.append(" age: ").append(age);
        builder.append(" id: ").append(id);
        builder.append(" }");
        return builder.toString();
    }
}