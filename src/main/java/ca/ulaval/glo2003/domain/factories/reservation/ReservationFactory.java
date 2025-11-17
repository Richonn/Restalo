package ca.ulaval.glo2003.domain.factories.reservation;

import ca.ulaval.glo2003.domain.TimeUtils;
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

    fieldsAreValid(date, groupSize);

    startTime = TimeUtils.adjustTimeToSlotIntervalSliceUp(startTime);

    return new Reservation(
        date,
        startTime,
        restaurant.defineReservationEndTime(startTime),
        groupSize,
        name,
        email,
        phoneNumber,
        restaurant.getId());
  }

  private void fieldsAreValid(LocalDate date, Integer groupSize) {
    if (groupSize <= 0) {
      throw new InvalidParameterException("The group size of the reservation must be positive");
    }
  }
}
