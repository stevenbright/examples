package com.alexshabanov.samples.reservations.model;

import java.util.Objects;

/**
 * @author Alexander Shabanov
 */
public final class Room extends DomainObject {
  private final long id;
  private final String name;

  public Room(long id, String name) {
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
    if (!(o instanceof Room)) return false;

    Room room = (Room) o;

    return id == room.id && name.equals(room.name);

  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + name.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Room{" +
        "id=" + getId() +
        ", name='" + getName() + '\'' +
        '}';
  }
}
