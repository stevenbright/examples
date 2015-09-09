package com.alexshabanov.sample.hibernateJoins.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 */
@Entity
@Table(name = "person")
public class Person {

  @Id
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true, length = 11)
  private long id;

  @Column(name = "name", length = 20, nullable = false)
  private String name;

  @Column(name = "age", nullable = false)
  private int age;

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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + getId() +
        ", name='" + getName() + '\'' +
        ", age=" + getAge() +
        '}';
  }
}
