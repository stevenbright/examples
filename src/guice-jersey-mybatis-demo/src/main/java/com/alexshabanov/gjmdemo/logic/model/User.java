package com.alexshabanov.gjmdemo.logic.model;

import com.google.common.base.Objects;

/**
 * Represents user object
 */
public final class User {
  private Long id;
  private String username;
  private int age;
  private String note;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public User copy() {
    return copy(this);
  }

  public static User copy(User user) {
    final User newUser = new User();
    newUser.setId(user.getId());
    newUser.setAge(user.getAge());
    newUser.setNote(user.getNote());
    newUser.setUsername(user.getUsername());
    return user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equal(age, user.age) &&
        Objects.equal(id, user.id) &&
        Objects.equal(username, user.username) &&
        Objects.equal(note, user.note);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, username, age, note);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("id", id)
        .add("username", username)
        .add("age", age)
        .add("note", note)
        .toString();
  }
}
