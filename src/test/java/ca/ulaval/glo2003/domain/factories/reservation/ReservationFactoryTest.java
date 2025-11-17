package ca.ulaval.glo2003.domain.factories.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationFactoryTest {

  private final ReservationFactory reservationFactory = new ReservationFactory();

  private final LocalDate DATE = LocalDate.now();
  private final LocalTime START_TIME = LocalTime.of(10, 0);
  private final LocalTime END_TIME = LocalTime.of(11, 0);
  private final Integer GROUP_SIZE = 2;
  private final String NAME = "John Deer";
  private final String EMAIL = "john.deer@gmail.com";
  private final String PHONE_NUMBER = "1234567890";
  private final Restaurant restaurant = mock(Restaurant.class);

  @BeforeEach
  void setUp() {
    when(restaurant.defineReservationEndTime(START_TIME)).thenReturn(END_TIME);
  }

  @Test
  void givenValidParameters_whenCreateReservation_thenReturnsValidReservation() {

    Reservation reservation =
        reservationFactory.createReservation(
            DATE, START_TIME, GROUP_SIZE, NAME, EMAIL, PHONE_NUMBER, restaurant);

    assertNotNull(reservation);
    assertThat(reservation.getId()).isNotNull();
    assertThat(reservation.getDate()).isEqualTo(DATE);
    assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
    assertThat(reservation.getRestaurantId()).isEqualTo(restaurant.getId());
    assertThat(reservation.getCustomer()).isNotNull();
    assertThat(reservation.getEndTime()).isEqualTo(END_TIME);
  }

  @Test
  void givenZeroGroupSize_whenCreateReservation_thenThrowsInvalidParameterException() {

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () ->
                reservationFactory.createReservation(
                    DATE, START_TIME, 0, NAME, EMAIL, PHONE_NUMBER, restaurant));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The group size of the reservation must be positive");
  }

  @Test
  void givenNegativeGroupSize_whenCreateReservation_thenThrowsInvalidParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () ->
                reservationFactory.createReservation(
                    DATE, START_TIME, -5, NAME, EMAIL, PHONE_NUMBER, restaurant));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The group size of the reservation must be positive");
  }
}
