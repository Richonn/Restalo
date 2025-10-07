package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Reservation {

  private final String id;
  private final LocalDate date;
  private final ReservationTime reservationTime;
  private final Integer groupSize;
  private final Customer customer;
  private final String restaurantId;

  public Reservation(
      LocalDate date,
      LocalTime startTime,
      Integer groupSize,
      String name,
      String email,
      String phoneNumber,
      String restaurantId) {

    this.id = UUID.randomUUID().toString();
    this.date = date;
    this.reservationTime = new ReservationTime(startTime, null);
    this.groupSize = groupSize;
    this.customer = new Customer(name, email, phoneNumber);
    this.restaurantId = restaurantId;
  }

  public String getId() {
    return id;
  }

  public String getRestaurantId() {
    return restaurantId;
  }

  public void setReservationEndTime(LocalTime endTime) {
    reservationTime.setEndTime(endTime);
  }

  public void adjustStartTimeto15minSlice() {
    reservationTime.adjustStartTimeto15minSlice();
  }

  public void validateFields() {
    dateIsValid();
    groupSizeIsValid();
    reservationTime.validateStartTimeIsDefined();
    customer.validateFields();
  }

  private void dateIsValid() {
    if (date == null) {
      throw new MissingParameterException("The date of the reservation is required");
    }
    if (date.isBefore(LocalDate.now())) {
      throw new InvalidParameterException("The date of the reservation cannot be in the past");
    }
  }

  private void groupSizeIsValid() {
    if (groupSize == null) {
      throw new MissingParameterException("The group size of the reservation is required");
    }
    if (groupSize <= 0) {
      throw new InvalidParameterException("The group size of the reservation must be positive");
    }
  }
}
