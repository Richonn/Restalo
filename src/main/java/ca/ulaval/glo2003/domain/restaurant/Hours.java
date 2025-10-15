package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalTime;
import java.util.Objects;

public class Hours {
  private final LocalTime open;
  private final LocalTime close;
  private final int reservationDuration;

  private static final int DEFAULT_RESERVATION_LENGTH = 60;

  public Hours(LocalTime open, LocalTime close, Integer reservationLength) {
    this.open = open;
    this.close = close;
    this.reservationDuration =
        Objects.requireNonNullElse(reservationLength, DEFAULT_RESERVATION_LENGTH);
  }

  public LocalTime getOpeningHour() {
    return open;
  }

  public LocalTime getClosingHour() {
    return close;
  }

  public int getReservationDuration() {
    return reservationDuration;
  }

  public void validateFields() {
    validateFieldsAreDefined();
    validateFieldsAreValid();
  }

  private void validateFieldsAreDefined() {
    if (open == null) {
      throw new MissingParameterException("The openingHour of the restaurant is required");
    }
    if (close == null) {
      throw new MissingParameterException("The closingHour of the restaurant is required");
    }
  }

  private void validateFieldsAreValid() {
    if (close.isBefore(open)) {
      throw new InvalidParameterException(
          "The restaurant closing hour cannot be before the opening hour");
    }
    if (open.plusHours(1).compareTo(close) > 0) {
      throw new InvalidParameterException("The restaurant needs to be open for at least one hour");
    }
    if (reservationDuration <= 0) {
      throw new InvalidParameterException(
          "The restaurant reservation length needs to be bigger than 0 minutes");
    }

    if (reservationDuration < 5 || reservationDuration > 180) {
      throw new InvalidParameterException(
          "Reservation reservationDuration must be between 5 and 180 minutes.");
    }
  }

  public boolean isFullyOpenDuring(LocalTime from, LocalTime to) {

    if (from != null && to != null) {
      return !open.isAfter(from) && !close.isBefore(to);
    } else if (from != null) {
      return !open.isAfter(from) && close.isAfter(from);
    } else if (to != null) {
      return open.isBefore(to) && !close.isBefore(to);
    }
    return true;
  }

  public LocalTime defineReservationEndTime(LocalTime startTime) {
    if (startTime.isBefore(open)) {
      throw new InvalidParameterException(
          "The restaurant is not open for the selected reservation start time");
    }
    if (startTime.plusMinutes(reservationDuration).isAfter(close)) {
      throw new InvalidParameterException(
          "The restaurant closes before the end of the reservation");
    }
    return startTime.plusMinutes(reservationDuration);
  }
}
