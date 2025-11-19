package ca.ulaval.glo2003.domain.reservation;

import java.time.LocalDate;

public class ReservationFixture {

  private LocalDate DATE = LocalDate.now();
  private Integer GROUP_SIZE = 2;
  private String RESTAURANT_ID = "123";

  public Reservation build(ReservationTime reservationTime, Customer customer) {
    return new Reservation(DATE, reservationTime, GROUP_SIZE, customer, RESTAURANT_ID);
  }

  public ReservationFixture withDate(LocalDate date) {
    DATE = date;
    return this;
  }

  public ReservationFixture withGroupSize(Integer groupSize) {
    GROUP_SIZE = groupSize;
    return this;
  }

  public ReservationFixture withRestaurantId(String restaurantId) {
    RESTAURANT_ID = restaurantId;
    return this;
  }
}
