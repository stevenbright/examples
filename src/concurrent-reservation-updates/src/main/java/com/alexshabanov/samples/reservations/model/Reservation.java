package com.alexshabanov.samples.reservations.model;

import com.truward.time.UtcTime;

import java.util.Objects;

/**
 * @author Alexander Shabanov
 */
public final class Reservation extends DomainObject {

  public enum Status {
    ACTIVE(1),
    INACTIVE(2);

    private final int code;

    public int getCode() {
      return code;
    }

    Status(int code) {
      this.code = code;
    }
  }

  private final long id;
  private final long userId;
  private final long roomId;
  private final UtcTime from;
  private final UtcTime to;

  public Reservation(long id, long userId, long roomId, UtcTime from, UtcTime to) {
    this.id = id;
    this.userId = userId;
    this.roomId = roomId;
    this.from = Objects.requireNonNull(from, "from");
    this.to = Objects.requireNonNull(to, "to");
  }

  public long getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public long getRoomId() {
    return roomId;
  }

  public UtcTime getFrom() {
    return from;
  }

  public UtcTime getTo() {
    return to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Reservation)) return false;

    Reservation that = (Reservation) o;

    return id == that.id && userId == that.userId && roomId == that.roomId && from.equals(that.from) &&
        to.equals(that.to);

  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (userId ^ (userId >>> 32));
    result = 31 * result + (int) (roomId ^ (roomId >>> 32));
    result = 31 * result + from.hashCode();
    result = 31 * result + to.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Reservation{" +
        "id=" + getId() +
        ", userId=" + getUserId() +
        ", roomId=" + getRoomId() +
        ", from=" + getFrom() +
        ", to=" + getTo() +
        '}';
  }
}
