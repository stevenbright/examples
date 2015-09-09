package com.alexshabanov.sample.hibernateJoins.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 */
@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonIdSequence")
  @SequenceGenerator(name = "PersonIdSequence", sequenceName = "seq_person", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", length = 20, nullable = false)
  private String name;

  @Column(name = "age", nullable = false)
  private int age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
