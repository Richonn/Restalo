package ca.ulaval.glo2003.infra.persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.reservation.Reservation;

public class InMemoryReservationRepositoryFixture {

  private String RESTAURANT_ID = "restaurantId";
  private String RESERVATION_ID = "reservationId";
  private final Reservation reservation = mock(Reservation.class);

  public Reservation build() {
    when(reservation.getId()).thenReturn(RESERVATION_ID);
    when(reservation.getRestaurantId()).thenReturn(RESTAURANT_ID);
    return reservation;
  }

  public InMemoryReservationRepositoryFixture withRestaurantId(String restaurantId) {
    this.RESTAURANT_ID = restaurantId;
    return this;
  }

  public InMemoryReservationRepositoryFixture withReservationId(String reservationId) {
    this.RESERVATION_ID = reservationId;
    return this;
  }
}
