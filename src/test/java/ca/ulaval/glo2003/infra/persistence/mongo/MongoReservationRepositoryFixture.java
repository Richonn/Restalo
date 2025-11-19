package ca.ulaval.glo2003.infra.persistence.mongo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.reservation.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public class MongoReservationRepositoryFixture {

  private String RESERVATION_ID = "reservationId";
  private String RESTAURANT_ID = "restaurantId";
  private LocalDate DATE = LocalDate.of(2025, 12, 25);
  private LocalTime START_TIME = LocalTime.of(18, 30);
  private LocalTime END_TIME = LocalTime.of(20, 30);
  private Integer GROUP_SIZE = 4;
  private String CUSTOMER_NAME = "John Doe";
  private String CUSTOMER_EMAIL = "john.doe@example.com";
  private String CUSTOMER_PHONE = "1234567890";
  private final Reservation RESERVATION = mock(Reservation.class);
  private final Customer CUSTOMER = mock(Customer.class);

  public Reservation build() {
    when(CUSTOMER.getName()).thenReturn(CUSTOMER_NAME);
    when(CUSTOMER.getEmail()).thenReturn(CUSTOMER_EMAIL);
    when(CUSTOMER.getPhoneNumber()).thenReturn(CUSTOMER_PHONE);

    when(RESERVATION.getId()).thenReturn(RESERVATION_ID);
    when(RESERVATION.getRestaurantId()).thenReturn(RESTAURANT_ID);
    when(RESERVATION.getDate()).thenReturn(DATE);
    when(RESERVATION.getStartTime()).thenReturn(START_TIME);
    when(RESERVATION.getEndTime()).thenReturn(END_TIME);
    when(RESERVATION.getGroupSize()).thenReturn(GROUP_SIZE);
    when(RESERVATION.getCustomer()).thenReturn(CUSTOMER);
    return RESERVATION;
  }

  public MongoReservationRepositoryFixture withReservationId(String reservationId) {
    this.RESERVATION_ID = reservationId;
    return this;
  }

  public MongoReservationRepositoryFixture withRestaurantId(String restaurantId) {
    this.RESTAURANT_ID = restaurantId;
    return this;
  }

  public MongoReservationRepositoryFixture withDate(LocalDate date) {
    this.DATE = date;
    return this;
  }

  public MongoReservationRepositoryFixture withGroupSize(Integer groupSize) {
    this.GROUP_SIZE = groupSize;
    return this;
  }

  public MongoReservationRepositoryFixture withCustomerName(String name) {
    this.CUSTOMER_NAME = name;
    return this;
  }
}
