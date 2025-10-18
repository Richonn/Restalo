package ca.ulaval.glo2003.domain.factories.reservation;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationFactory {

  public ReservationFactory() {}

  public Reservation createReservation(
      LocalDate date,
      LocalTime startTime,
      Integer groupSize,
      String name,
      String email,
      String phoneNumber,
      Restaurant restaurant) {

    if (restaurant == null) {
      return null;
    }

    Reservation reservation =
        new Reservation(date, startTime, groupSize, name, email, phoneNumber, restaurant.getId());

    reservation.adjustStartTimeTo15minSlice();
    reservation.validateFields();

    reservation.setReservationEndTime(
        restaurant.defineReservationEndTime(reservation.getStartTime()));

    return reservation;
  }
}
