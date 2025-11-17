package ca.ulaval.glo2003.domain.factories.restaurant;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;

public class RestaurantFactory {

  private static final int MIN_OPENING_HOURS = 1;
  private static final int MIN_RESERVATION_DURATION = 5;
  private static final int MAX_RESERVATION_DURATION = 180;

  public RestaurantFactory() {}

  public Restaurant createRestaurant(
      String ownerId,
      String name,
      Integer capacity,
      LocalTime open,
      LocalTime close,
      Integer reservationDuration) {

    validateFieldsAreValid(capacity, open, close, reservationDuration);

    return new Restaurant(name, capacity, open, close, ownerId, reservationDuration);
  }

  private void validateFieldsAreValid(
      Integer capacity, LocalTime open, LocalTime close, Integer reservationDuration) {
    if (capacity <= 0) {
      throw new InvalidParameterException("The capacity of the restaurant must be positive");
    }
    if (close.isBefore(open)) {
      throw new InvalidParameterException(
          "The restaurant closing hour cannot be before the opening hour");
    }
    if (open.plusHours(MIN_OPENING_HOURS).isAfter(close)) {
      throw new InvalidParameterException("The restaurant needs to be open for at least one hour");
    }
    if (reservationDuration != null) {
      if (reservationDuration <= 0) {
        throw new InvalidParameterException(
            "The restaurant reservation length needs to be bigger than 0 minutes");
      }

      if (reservationDuration < MIN_RESERVATION_DURATION
          || reservationDuration > MAX_RESERVATION_DURATION) {
        throw new InvalidParameterException(
            String.format(
                "Reservation reservationDuration must be between %d and %d minutes.",
                MIN_RESERVATION_DURATION, MAX_RESERVATION_DURATION));
      }
    }
  }
}
