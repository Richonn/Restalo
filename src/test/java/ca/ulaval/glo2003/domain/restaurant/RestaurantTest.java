package ca.ulaval.glo2003.domain.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;

class RestaurantTest {

  private static RestaurantFixture restaurantFixture;

  private final Hours hours = Mockito.mock(Hours.class);

  @BeforeEach
  public void setUp() {
    restaurantFixture = new RestaurantFixture();
  }

  @Test
  void givenRestaurantAndStartTime_whenDefineReservationEndTime_thenReturnReservationEndTime() {
    Restaurant a_restaurant = restaurantFixture.build(hours);
    LocalTime startTime = LocalTime.of(10, 0, 0);
    LocalTime expectedEndTime = LocalTime.of(11, 0, 0);

    Mockito.when(hours.defineReservationEndTime(startTime)).thenReturn(expectedEndTime);

    assertThat(a_restaurant.defineReservationEndTime(startTime)).isEqualTo(expectedEndTime);
  }

  @Test
  void givenValidRestaurant_whenValidateFields_thenNoExceptions() {
    Restaurant a_restaurant = restaurantFixture.build(hours);

    doNothing().when(hours).validateFields();

    assertDoesNotThrow(a_restaurant::validateFields);
  }

  @Test
  void givenMissingRestaurantName_whenValidateFields_thenThrowsMissingParameterException() {
    Restaurant a_restaurant = restaurantFixture.withName(null).build(hours);

    doNothing().when(hours).validateFields();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_restaurant::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The name of the restaurant is required");
  }

  @Test
  void givenBlankRestaurantName_whenValidateFields_thenThrowsMissingParameterException() {
    Restaurant a_restaurant = restaurantFixture.withName("").build(hours);

    doNothing().when(hours).validateFields();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_restaurant::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The name of the restaurant cannot be blank");
  }

  @Test
  void givenMissingRestaurantCapacity_whenValidateFields_thenThrowsMissingParameterException() {
    Restaurant a_restaurant = restaurantFixture.withCapacity(null).build(hours);

    doNothing().when(hours).validateFields();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_restaurant::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The capacity of the restaurant is required");
  }

  @Test
  void givenInvalidRestaurantCapacity_whenValidateFields_thenThrowsInvalidParameterException() {
    Restaurant a_restaurant = restaurantFixture.withCapacity(-1).build(hours);

    doNothing().when(hours).validateFields();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_restaurant::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The capacity of the restaurant must be positive");
  }
}
