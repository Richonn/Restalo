package ca.ulaval.glo2003.infra.persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.reservation.Reservation;

public class InMemoryReservationRepositoryFixture {

  private String restaurantId = "restaurantId";
  private String reservationId = "reservationId";
  private final Reservation reservation = mock(Reservation.class);

  public Reservation build() {
    when(reservation.getId()).thenReturn(reservationId);
    when(reservation.getRestaurantId()).thenReturn(restaurantId);
    return reservation;
  }

  public InMemoryReservationRepositoryFixture withRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }

  public InMemoryReservationRepositoryFixture withReservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }
}
