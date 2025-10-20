package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantDomainServiceTest {

  private static RestaurantDomainServiceFixture restaurantDomainServiceFixture;
  private static RestaurantDomainService restaurantDomainService;

  @BeforeEach
  public void setUp() {
    restaurantDomainServiceFixture = new RestaurantDomainServiceFixture();
    restaurantDomainService = new RestaurantDomainService();
  }

  @Test
  void givenValidOwnerId_whenValidateHeaders_thenNoExceptions() {
    String ownerId = restaurantDomainServiceFixture.build();

    assertDoesNotThrow(() -> restaurantDomainService.validateHeaders(ownerId));
  }

  @Test
  void givenNullOwnerId_whenValidateHeaders_thenThrowsMissingParameterException() {
    String ownerId = restaurantDomainServiceFixture.withOwnerID(null).build();

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateHeaders(ownerId));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing Owner header");
  }

  @Test
  void givenBlankOwnerId_whenValidateHeaders_thenThrowsMissingParameterException() {
    String ownerId = restaurantDomainServiceFixture.withOwnerID("").build();

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateHeaders(ownerId));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing Owner header");
  }

  @Test
  void givenValidRestaurant_whenValidateRestaurant_thenNoExceptions() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-123", null);

    assertDoesNotThrow(() -> restaurantDomainService.validateRestaurant(restaurant));
  }

  @Test
  void givenRestaurantWithNullName_whenValidateRestaurant_thenThrowsMissingParameterException() {
    Restaurant restaurant =
        new Restaurant(null, 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-123", null);

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateRestaurant(restaurant));

    assertThat(exception.getMessage()).isEqualTo("The name of the restaurant is required");
  }

  @Test
  void givenRestaurantWithBlankName_whenValidateRestaurant_thenThrowsMissingParameterException() {
    Restaurant restaurant =
        new Restaurant("   ", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-123", null);

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateRestaurant(restaurant));

    assertThat(exception.getMessage()).isEqualTo("The name of the restaurant cannot be blank");
  }

  @Test
  void
      givenRestaurantWithNullCapacity_whenValidateRestaurant_thenThrowsMissingParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", null, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-123", null);

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateRestaurant(restaurant));

    assertThat(exception.getMessage()).isEqualTo("The capacity of the restaurant is required");
  }

  @Test
  void givenValidOpenedHours_whenIsTimeRangeValid_thenNoExceptions() {
    OpenedHours openedHours = new OpenedHours(LocalTime.of(9, 0), LocalTime.of(17, 0));

    assertDoesNotThrow(() -> restaurantDomainService.isTimeRangeValid(openedHours));
  }

  @Test
  void givenOpenedHoursWithNullTimes_whenIsTimeRangeValid_thenNoExceptions() {
    OpenedHours openedHours = new OpenedHours(null, null);

    assertDoesNotThrow(() -> restaurantDomainService.isTimeRangeValid(openedHours));
  }

  @Test
  void givenOpenedHoursWithSameTime_whenIsTimeRangeValid_thenNoExceptions() {
    OpenedHours openedHours = new OpenedHours(LocalTime.of(12, 0), LocalTime.of(12, 0));

    assertDoesNotThrow(() -> restaurantDomainService.isTimeRangeValid(openedHours));
  }
}
