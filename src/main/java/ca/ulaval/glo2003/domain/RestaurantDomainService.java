package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class RestaurantDomainService {

  public void validateHeaders(String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      throw new MissingParameterException("Missing Owner header");
    }
  }

  public LocalDate convertDateStringToLocalDate(String dateString) {
    if (dateString == null || dateString.isBlank()) {
      throw new MissingParameterException("Missing date query parameter");
    }
    try {
      return LocalDate.parse(dateString);
    } catch (DateTimeParseException e) {
      throw new InvalidParameterException("Invalid date format");
    }
  }

  public void validateRestaurant(Restaurant restaurant) {
    restaurant.validateFields();
  }

  public void isTimeRangeValid(OpenedHours openedHours) {
    openedHours.isTimeRangeValid();
  }

  public Map<LocalTime, Integer> getRestaurantAvailability(
      Restaurant restaurant, List<Reservation> reservations) {
    Map<LocalTime, Integer> availability = restaurant.generateDefaultAvailability();
    for (Reservation reservation : reservations) {
      List<LocalTime> timeSlots = reservation.generateAllTimeSlotsInterval();

      for (LocalTime time : timeSlots) {
        int remainingPlaces = availability.get(time) - reservation.getGroupSize();
        availability.replace(time, remainingPlaces);
      }
    }

    return availability;
  }
}
