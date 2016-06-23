package com.alexshabanov.samples.reservations.service.dao.support;

import com.alexshabanov.samples.reservations.model.Reservation;
import com.alexshabanov.samples.reservations.model.Room;
import com.alexshabanov.samples.reservations.model.UserAccount;
import com.alexshabanov.samples.reservations.service.dao.ReservationDao;
import com.truward.time.jdbc.UtcTimeSqlUtil;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Shabanov
 */
@Transactional(propagation = Propagation.REQUIRED)
public class DefaultReservationDao implements ReservationDao {
  private final JdbcOperations db;

  public DefaultReservationDao(JdbcOperations db) {
    this.db = Objects.requireNonNull(db, "db");
  }

  @Override
  public List<UserAccount> getAccounts() {
    return db.query("SELECT id, name FROM user_account ORDER BY id",
        (rs, i) -> new UserAccount(rs.getLong("id"), rs.getString("name")));
  }

  @Override
  public List<Room> getRooms() {
    return db.query("SELECT id, name FROM room ORDER BY id",
        (rs, i) -> new Room(rs.getLong("id"), rs.getString("name")));
  }

  @Override
  public long insertReservation(Reservation reservation) {
    final long id = db.queryForObject("SELECT seq_reservation.nextval", Long.class);
    db.update("INSERT INTO reservation (id, user_id, room_id, ts_from, ts_to, status) VALUES (?, ?, ?, ?, ?, ?)",
        id, reservation.getUserId(), reservation.getRoomId(),
        reservation.getFrom().asCalendar(), reservation.getTo().asCalendar(),
        Reservation.Status.INACTIVE.getCode());
    return id;
  }

  @Override
  public boolean activateReservation(long id) {
    final Reservation r = db.queryForObject("SELECT user_id, room_id, ts_from, ts_to FROM reservation WHERE id=?",
        (rs, i) -> new Reservation(id, rs.getLong("user_id"), rs.getLong("room_id"),
            UtcTimeSqlUtil.getUtcTime(rs, "ts_from"), UtcTimeSqlUtil.getUtcTime(rs, "ts_to")),
        id);

    final Integer conflicts = db.queryForObject("SELECT COUNT(0) FROM reservation\n" +
            "WHERE ((status=?) OR (id<? AND status=?)) AND room_id=? AND ((ts_from<?) AND (ts_to>?))",
        (rs, i) -> rs.getInt(1),
        Reservation.Status.ACTIVE.getCode(),
        id,
        Reservation.Status.INACTIVE.getCode(),
        r.getRoomId(),
        r.getTo().asCalendar(),
        r.getFrom().asCalendar());

    if (conflicts == 0) {
      db.update("UPDATE reservation SET status=? WHERE id=?", Reservation.Status.ACTIVE.getCode(), id);
      return true;
    }

    // delete if conflicts exist
    db.update("DELETE FROM reservation WHERE id=?", id);
    return false;
  }
}
