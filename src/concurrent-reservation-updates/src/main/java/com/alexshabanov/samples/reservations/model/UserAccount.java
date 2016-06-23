package com.alexshabanov.samples.reservations.model;

import java.util.Objects;

/**
 * @author Alexander Shabanov
 */
public final class UserAccount extends DomainObject {
  private final long id;
  private final String name;

  public UserAccount(long id, String name) {
    this.id = id;
    this.name = Objects.requireNonNull(name, "name");
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAccount)) return false;

    final UserAccount that = (UserAccount) o;
    return id == that.id && name.equals(that.name);

  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + name.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "UserAccount{" +
        "id=" + getId() +
        ", name='" + getName() + '\'' +
        '}';
  }
}
