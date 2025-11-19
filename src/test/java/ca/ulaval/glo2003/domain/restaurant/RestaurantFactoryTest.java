package ca.ulaval.glo2003.domain.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class RestaurantFactoryTest {

  private final RestaurantFactory restaurantFactory = new RestaurantFactory();

  private final String OWNERID = "ownerId";
  private final String NAME = "name";
  private final Integer CAPACITY = 100;
  private final LocalTime OPEN = LocalTime.of(10, 0);
  private final LocalTime CLOSE = LocalTime.of(14, 0);
  private final Integer RESERVATIONDURATION = 120;

  @Test
  void givenValidParameters_whenCreateRestaurant_thenReturnsValidRestaurant() {
    Restaurant restaurant =
        restaurantFactory.createRestaurant(
            OWNERID, NAME, CAPACITY, OPEN, CLOSE, RESERVATIONDURATION);

    assertNotNull(restaurant);
    assertThat(restaurant.getId()).isNotNull();
    assertThat(restaurant.getName()).isEqualTo(NAME);
    assertThat(restaurant.getCapacity()).isEqualTo(CAPACITY);
    assertThat(restaurant.getOpeningHour()).isEqualTo(OPEN);
    assertThat(restaurant.getClosingHour()).isEqualTo(CLOSE);
    assertThat(restaurant.getReservationLength()).isEqualTo(RESERVATIONDURATION);
    assertThat(restaurant.getOwnerId()).isEqualTo(OWNERID);
  }

  @Test
  void givenNegativeCapacity_whenCreateRestaurant_thenThrowsInvalidParameterException() {
    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () ->
                restaurantFactory.createRestaurant(
                    OWNERID, NAME, -1, OPEN, CLOSE, RESERVATIONDURATION));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The capacity of the restaurant must be positive");
  }

  @Test
  void givenInvalidReservationLength_whenCreateRestaurant_thenThrowsInvalidParameterException() {
    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> restaurantFactory.createRestaurant(OWNERID, NAME, CAPACITY, OPEN, CLOSE, 0));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant reservation length needs to be bigger than 0 minutes");
  }

  @Test
  void givenClosingHourBeforeOpening_whenCreateRestaurant_thenThrowsInvalidParameterException() {
    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () ->
                restaurantFactory.createRestaurant(
                    OWNERID,
                    NAME,
                    CAPACITY,
                    LocalTime.of(12, 0),
                    LocalTime.of(10, 0),
                    RESERVATIONDURATION));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant closing hour cannot be before the opening hour");
  }

  @Test
  void
      givenClosingHourLessThanAnHourAfterOpening_whenCreateRestaurant_thenThrowsInvalidParameterException() {
    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () ->
                restaurantFactory.createRestaurant(
                    OWNERID,
                    NAME,
                    CAPACITY,
                    LocalTime.of(10, 0),
                    LocalTime.of(10, 30),
                    RESERVATIONDURATION));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant needs to be open for at least one hour");
  }

  @Test
  void
      givenReservationDurationLessThan5Minutes_whenCreateRestaurant_thenThrowsInvalidParameterException() {
    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> restaurantFactory.createRestaurant(OWNERID, NAME, CAPACITY, OPEN, CLOSE, 4));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("Reservation reservationDuration must be between 5 and 180 minutes.");
  }

  @Test
  void
      givenReservationDurationMoreThan180Minutes_whenCreateRestaurant_thenThrowsInvalidParameterException() {
    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> restaurantFactory.createRestaurant(OWNERID, NAME, CAPACITY, OPEN, CLOSE, 181));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("Reservation reservationDuration must be between 5 and 180 minutes.");
  }
}
