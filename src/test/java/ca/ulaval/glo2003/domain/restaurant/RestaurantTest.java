package ca.ulaval.glo2003.domain.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

  @Test
  void givenRestaurant_whenGetOwnerId_thenReturnsOwnerId() {
    String expectedOwnerId = "owner-123";
    Restaurant restaurant = restaurantFixture.withOwnerId(expectedOwnerId).build(hours);

    String ownerId = restaurant.getOwnerId();

    assertThat(ownerId).isEqualTo(expectedOwnerId);
  }

  @Test
  void givenRestaurant_whenGetOpeningHour_thenReturnsOpeningHour() {
    LocalTime expectedOpeningHour = LocalTime.of(10, 0, 0);
    Restaurant restaurant = restaurantFixture.build(hours);

    Mockito.when(hours.getOpeningHour()).thenReturn(expectedOpeningHour);

    LocalTime openingHour = restaurant.getOpeningHour();

    assertThat(openingHour).isEqualTo(expectedOpeningHour);
  }

  @Test
  void givenRestaurant_whenGetClosingHour_thenReturnsClosingHour() {
    LocalTime expectedClosingHour = LocalTime.of(22, 0, 0);
    Restaurant restaurant = restaurantFixture.build(hours);

    Mockito.when(hours.getClosingHour()).thenReturn(expectedClosingHour);

    LocalTime closingHour = restaurant.getClosingHour();

    assertThat(closingHour).isEqualTo(expectedClosingHour);
  }

  @Test
  void givenRestaurant_whenGenerateDefaultAvailability_thenReturnsValidAvailabilityMap() {
    LocalTime slot1 = LocalTime.of(10, 15, 0);
    LocalTime slot2 = LocalTime.of(10, 30, 0);
    LocalTime slot3 = LocalTime.of(10, 45, 0);

    List<LocalTime> slots = new ArrayList<>();
    slots.add(slot1);
    slots.add(slot2);
    slots.add(slot3);

    int capacity = 5;
    Restaurant restaurant = restaurantFixture.withCapacity(capacity).build(hours);

    Map<LocalTime, Integer> expectedDefaultAvailability = new HashMap<>();
    expectedDefaultAvailability.put(slot1, capacity);
    expectedDefaultAvailability.put(slot2, capacity);
    expectedDefaultAvailability.put(slot3, capacity);

    Mockito.when(hours.generateAllSlotIntervalSlices()).thenReturn(slots);

    assertThat(restaurant.generateDefaultAvailability()).isEqualTo(expectedDefaultAvailability);
  }
}
