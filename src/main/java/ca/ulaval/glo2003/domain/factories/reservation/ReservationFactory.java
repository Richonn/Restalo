package ca.ulaval.glo2003.domain.factories.reservation;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
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

    fieldsAreValid(date, groupSize);

    Reservation reservation =
        new Reservation(date, startTime, groupSize, name, email, phoneNumber, restaurant.getId());

    reservation.adjustStartTimeTo15minSlice();

    reservation.setReservationEndTime(
        restaurant.defineReservationEndTime(reservation.getStartTime()));

    return reservation;
  }

  private void fieldsAreValid(LocalDate date, Integer groupSize) {
    if (date.isBefore(LocalDate.now())) {
      throw new InvalidParameterException("The date of the reservation cannot be in the past");
    }
    if (groupSize <= 0) {
      throw new InvalidParameterException("The group size of the reservation must be positive");
    }
  }
}
