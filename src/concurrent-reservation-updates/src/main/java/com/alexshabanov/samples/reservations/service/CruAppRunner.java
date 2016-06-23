package com.alexshabanov.samples.reservations.service;

import com.alexshabanov.samples.reservations.model.Reservation;
import com.alexshabanov.samples.reservations.service.dao.ReservationDao;
import com.truward.time.UtcTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexander Shabanov
 */
public final class CruAppRunner implements Runnable {
  private final Logger log = LoggerFactory.getLogger(getClass());

  private final ReservationDao reservationDao;

  public CruAppRunner(ReservationDao reservationDao) {
    this.reservationDao = reservationDao;
  }

  @Override
  public void run() {
    log.info("Started: accounts={}, rooms={}", reservationDao.getAccounts(), reservationDao.getRooms());

    final long id1 = reservationDao.insertReservation(new Reservation(0, 100, 1000, UtcTime.days(100), UtcTime.days(110)));
    final long id2 = reservationDao.insertReservation(new Reservation(0, 101, 1000, UtcTime.days(105), UtcTime.days(115)));

    final boolean a2 = reservationDao.activateReservation(id2);
    final boolean a1 = reservationDao.activateReservation(id1);

    log.info("a1={}, a2={}", a1, a2);
  }
}
