package com.alexshabanov.samples.reservations.service.dao;

import com.alexshabanov.samples.reservations.model.Reservation;
import com.alexshabanov.samples.reservations.model.Room;
import com.alexshabanov.samples.reservations.model.UserAccount;

import java.util.List;

/**
 * @author Alexander Shabanov
 */
public interface ReservationDao {

  List<UserAccount> getAccounts();

  List<Room> getRooms();

  long insertReservation(Reservation reservation);

  boolean activateReservation(long id);
}
