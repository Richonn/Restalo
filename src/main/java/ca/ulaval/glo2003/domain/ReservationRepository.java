package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import java.util.List;

public interface ReservationRepository {
  void save(Reservation reservation);

  List<Reservation> getReservationListOfRestaurant(String restaurantId);

  Reservation getReservationById(String reservationId);

  void delete(String reservationId);
}
