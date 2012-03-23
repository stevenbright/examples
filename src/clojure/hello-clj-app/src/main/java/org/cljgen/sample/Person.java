package org.cljgen.sample;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 3/23/12
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Person {
    private final Long id;
    private final long torp;
    private final String name;
    private final int age;

    public Person(Long id, long torp, String name, int age) {
        this.id = id;
        this.torp = torp;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTorp() {
        return torp;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (torp != person.torp) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (int) (torp ^ (torp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }
}
