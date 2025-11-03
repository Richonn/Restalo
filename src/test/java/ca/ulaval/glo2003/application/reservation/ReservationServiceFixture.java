package ca.ulaval.glo2003.application.reservation;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationServiceFixture {

  private LocalDate DATE = LocalDate.now();
  private LocalTime START_TIME = LocalTime.of(11, 0, 0);
  private int GROUP_SIZE = 3;
  private String CUSTOMER_NAME = "customerName";
  private String EMAIL = "email";
  private String PHONE_NUMBER = "phoneNumber";
  private String RESTAURANT_ID = "restaurantId";

  public Reservation build() {
    return new Reservation(
        DATE, START_TIME, GROUP_SIZE, CUSTOMER_NAME, EMAIL, PHONE_NUMBER, RESTAURANT_ID);
  }

  public ReservationServiceFixture withDate(LocalDate date) {
    this.DATE = date;
    return this;
  }

  public ReservationServiceFixture withStartTime(LocalTime startTime) {
    this.START_TIME = startTime;
    return this;
  }

  public ReservationServiceFixture withGroupSize(int groupSize) {
    this.GROUP_SIZE = groupSize;
    return this;
  }

  public ReservationServiceFixture withCustomerName(String customerName) {
    this.CUSTOMER_NAME = customerName;
    return this;
  }

  public ReservationServiceFixture withEmail(String email) {
    this.EMAIL = email;
    return this;
  }

  public ReservationServiceFixture withPhoneNumber(String phoneNumber) {
    this.PHONE_NUMBER = phoneNumber;
    return this;
  }

  public ReservationServiceFixture withRestaurantId(String restaurantId) {
    this.RESTAURANT_ID = restaurantId;
    return this;
  }
}
