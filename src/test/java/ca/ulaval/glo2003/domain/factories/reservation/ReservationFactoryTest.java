package ca.ulaval.glo2003.domain.factories.reservation;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class ReservationFactoryTest {

  private final ReservationFactory reservationFactory = new ReservationFactory();

  private final LocalDate DATE = LocalDate.now();
  private final LocalTime START_TIME = LocalTime.of(10, 0, 0);
  private final Integer GROUP_SIZE = 2;
  private final String NAME = "John Deer";
  private final String EMAIL = "john.deer@gmail.com";
  private final String PHONE_NUMBER = "1234567890";

  @Test
  void givenNullRestaurant_whenCreateReservation_thenReturnNull() {
    Reservation a_reservation =
        reservationFactory.createReservation(
            DATE, START_TIME, GROUP_SIZE, NAME, EMAIL, PHONE_NUMBER, null);

    assertThat(a_reservation).isEqualTo(null);
  }
}
