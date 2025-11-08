package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  void givenValidDateString_whenConvertDateStringToLocalDate_thenNoExceptions() {
    String dateString = LocalDate.now().toString();
    LocalDate expectedDate = LocalDate.now();

    assertDoesNotThrow(() -> restaurantDomainService.convertDateStringToLocalDate(dateString));
    assertThat(restaurantDomainService.convertDateStringToLocalDate(dateString)).isEqualTo(expectedDate);
  }

  @Test
  void givenNullDateString_whenConvertDateStringToLocalDate_thenThrowsMissingParameterException() {
    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class, () -> restaurantDomainService.convertDateStringToLocalDate(null));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing date query parameter");
  }

  @Test
  void givenBlankDateString_whenConvertDateStringToLocalDate_thenThrowsMissingParameterException() {
    String dateString = "";

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.convertDateStringToLocalDate(dateString));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing date query parameter");
  }

  @Test
  void givenInvalidDateString_whenConvertDateStringToLocalDate_thenThrowsInvalidParameterException() {
    String dateString = "not valid";

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> restaurantDomainService.convertDateStringToLocalDate(dateString));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Invalid date format");
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

  @Test
  void
      givenValidRestaurantAndReservationList_whenGetRestaurantAvailability_thenReturnExpectedMap() {
    LocalTime slot1 = LocalTime.of(10, 15, 0);
    LocalTime slot2 = LocalTime.of(10, 30, 0);
    LocalTime slot3 = LocalTime.of(10, 45, 0);

    int capacity = 5;
    int reservationDuration = 15;
    Restaurant restaurant =
        new Restaurant(
            "test",
            capacity,
            LocalTime.of(10, 10),
            LocalTime.of(11, 0),
            "ownerId",
            reservationDuration);

    int reservationGroupSize1 = 2;
    int reservationGroupSize2 = 5;
    List<Reservation> reservationList = new ArrayList<>();
    Reservation reservation1 =
        new Reservation(
            LocalDate.now(),
            new ReservationTime(slot1, slot1.plusMinutes(reservationDuration)),
            reservationGroupSize1,
            new Customer("test", "test", "test"),
            "id");
    Reservation reservation2 =
        new Reservation(
            LocalDate.now(),
            new ReservationTime(slot3, slot3.plusMinutes(reservationDuration)),
            reservationGroupSize2,
            new Customer("test", "test", "test"),
            "id");
    reservationList.add(reservation1);
    reservationList.add(reservation2);

    Map<LocalTime, Integer> expectedDefaultAvailability = new HashMap<>();
    expectedDefaultAvailability.put(slot1, capacity - reservationGroupSize1);
    expectedDefaultAvailability.put(slot2, capacity);
    expectedDefaultAvailability.put(slot3, capacity - reservationGroupSize2);

    assertThat(restaurantDomainService.getRestaurantAvailability(restaurant, reservationList))
        .isEqualTo(expectedDefaultAvailability);
  }
}
