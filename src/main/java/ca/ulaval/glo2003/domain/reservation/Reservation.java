package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.TimeUtils;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

  public Reservation(
      LocalDate date,
      ReservationTime reservationTime,
      Integer groupSize,
      Customer customer,
      String restaurantId) {

    this.id = UUID.randomUUID().toString();
    this.date = date;
    this.reservationTime = reservationTime;
    this.groupSize = groupSize;
    this.customer = customer;
    this.restaurantId = restaurantId;
  }

  public Reservation(
      String id,
      LocalDate date,
      ReservationTime reservationTime,
      Integer groupSize,
      Customer customer,
      String restaurantId) {

    this.id = id;
    this.date = date;
    this.reservationTime = reservationTime;
    this.groupSize = groupSize;
    this.customer = customer;
    this.restaurantId = restaurantId;
  }

  public String getId() {
    return id;
  }

  public String getRestaurantId() {
    return restaurantId;
  }

  public LocalTime getStartTime() {
    return reservationTime.getStartTime();
  }

  public LocalTime getEndTime() {
    return reservationTime.getEndTime();
  }

  public LocalDate getDate() {
    return date;
  }

  public Integer getGroupSize() {
    return groupSize;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setReservationEndTime(LocalTime endTime) {
    reservationTime.setEndTime(endTime);
  }

  public void adjustStartTimeTo15minSlice() {
    reservationTime.adjustStartTimeToIntervalSlice();
  }

  public boolean matchesDate(String dateString) {
    if (dateString == null || dateString.isBlank()) {
      return true;
    }
    try {
      return date.isEqual(LocalDate.parse(dateString));
    } catch (Exception e) {
      throw new InvalidParameterException("The provided date is not a valid LocalDate format.");
    }
  }

  public boolean matchesCustomerName(String customerName) {
    if (customerName == null || customerName.isBlank()) {
      return true;
    }
    String normalizedQuery = customerName.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
    String normalizedName = customer.getName().replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
    return normalizedName.startsWith(normalizedQuery);
  }

  public List<LocalTime> generateAllTimeSlotsInterval() {
    List<LocalTime> timeSlots = new ArrayList<>();
    LocalTime startTime = reservationTime.getStartTime();
    while (startTime.isBefore(reservationTime.getEndTime())) {
      timeSlots.add(startTime);
      startTime = startTime.plusMinutes(TimeUtils.getSlotInterval());
    }
    return timeSlots;
  }
}
