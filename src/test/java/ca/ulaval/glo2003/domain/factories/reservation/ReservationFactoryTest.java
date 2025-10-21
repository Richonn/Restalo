package ca.ulaval.glo2003.domain.factories.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
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

  @Test
  void givenValidParameters_whenCreateReservation_thenReturnsValidReservation() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    Reservation reservation =
        reservationFactory.createReservation(
            DATE, START_TIME, GROUP_SIZE, NAME, EMAIL, PHONE_NUMBER, restaurant);

    assertNotNull(reservation);
    assertThat(reservation.getId()).isNotNull();
    assertThat(reservation.getDate()).isEqualTo(DATE);
    assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
    assertThat(reservation.getRestaurantId()).isEqualTo(restaurant.getId());
    assertThat(reservation.getCustomer()).isNotNull();
    assertThat(reservation.getEndTime()).isNotNull();
  }

  @Test
  void givenNullDate_whenCreateReservation_thenThrowsMissingParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    assertThrows(
        MissingParameterException.class,
        () ->
            reservationFactory.createReservation(
                null, START_TIME, GROUP_SIZE, NAME, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenNullGroupSize_whenCreateReservation_thenThrowsMissingParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    assertThrows(
        MissingParameterException.class,
        () ->
            reservationFactory.createReservation(
                DATE, START_TIME, null, NAME, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenPastDate_whenCreateReservation_thenThrowsInvalidParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);
    LocalDate pastDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        InvalidParameterException.class,
        () ->
            reservationFactory.createReservation(
                pastDate, START_TIME, GROUP_SIZE, NAME, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenZeroGroupSize_whenCreateReservation_thenThrowsInvalidParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    assertThrows(
        InvalidParameterException.class,
        () ->
            reservationFactory.createReservation(
                DATE, START_TIME, 0, NAME, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenNegativeGroupSize_whenCreateReservation_thenThrowsInvalidParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    assertThrows(
        InvalidParameterException.class,
        () ->
            reservationFactory.createReservation(
                DATE, START_TIME, -5, NAME, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenInvalidCustomerData_whenCreateReservation_thenThrowsException() {
    Restaurant restaurant =
        new Restaurant(
            "Chez Luigi", 50, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    assertThrows(
        MissingParameterException.class,
        () ->
            reservationFactory.createReservation(
                DATE, START_TIME, GROUP_SIZE, null, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenGroupSizeExceedsCapacity_whenCreateReservation_thenThrowsInvalidParameterException() {
    Restaurant restaurant =
        new Restaurant(
            "Pizza Delamama", 4, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    assertThrows(
        InvalidParameterException.class,
        () ->
            reservationFactory.createReservation(
                DATE, START_TIME, 100, NAME, EMAIL, PHONE_NUMBER, restaurant));
  }

  @Test
  void givenGroupSizeEqualsCapacity_whenCreateReservation_thenReturnsValidReservation() {
    Restaurant restaurant =
        new Restaurant(
            "Pizza Delamama", 4, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    Reservation reservation =
        reservationFactory.createReservation(
            DATE, START_TIME, 4, NAME, EMAIL, PHONE_NUMBER, restaurant);

    assertNotNull(reservation);
    assertThat(reservation.getGroupSize()).isEqualTo(4);
  }

  @Test
  void givenGroupSizeLessThanCapacity_whenCreateReservation_thenReturnsValidReservation() {
    Restaurant restaurant =
        new Restaurant(
            "Pizza Delamama", 10, LocalTime.of(9, 0), LocalTime.of(23, 0), "owner-123", null);

    Reservation reservation =
        reservationFactory.createReservation(
            DATE, START_TIME, 5, NAME, EMAIL, PHONE_NUMBER, restaurant);

    assertNotNull(reservation);
    assertThat(reservation.getGroupSize()).isEqualTo(5);
  }
}
