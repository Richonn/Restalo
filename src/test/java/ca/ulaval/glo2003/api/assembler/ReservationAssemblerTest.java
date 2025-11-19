package ca.ulaval.glo2003.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.domain.reservation.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationAssemblerTest {

  private Reservation reservation;
  private Restaurant restaurant;

  private static final LocalDate DATE = LocalDate.of(2025, 12, 25);
  private static final LocalTime START_TIME = LocalTime.of(18, 0);
  private static final LocalTime END_TIME = LocalTime.of(20, 0);
  private static final Integer GROUP_SIZE = 4;
  private static final String CUSTOMER_NAME = "John Doe";
  private static final String CUSTOMER_EMAIL = "john@example.com";
  private static final String CUSTOMER_PHONE = "5141234567";
  private static final String RESTAURANT_ID = "resto-123";

  @BeforeEach
  public void setUp() {
    Customer customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE);
    ReservationTime reservationTime = new ReservationTime(START_TIME, END_TIME);
    reservation = new Reservation(DATE, reservationTime, GROUP_SIZE, customer, RESTAURANT_ID);

    Hours hours = new Hours(LocalTime.of(11, 0), LocalTime.of(23, 0), 120);
    restaurant = new Restaurant("Pizza Delamama", 50, hours, "owner-123");
  }

  @Test
  public void givenValidReservationAndRestaurant_whenToDto_thenReturnsNotNull() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto);
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsIdCorrectly() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertEquals(reservation.getId(), dto.number());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsDateCorrectly() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertEquals(DATE, dto.date());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsGroupSizeCorrectly() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertEquals(GROUP_SIZE, dto.groupSize());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsTimeCorrectly() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto.time());
    assertEquals(START_TIME, dto.time().start());
    assertEquals(END_TIME, dto.time().end());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsCustomerCorrectly() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto.customer());
    assertEquals(CUSTOMER_NAME, dto.customer().name());
    assertEquals(CUSTOMER_EMAIL, dto.customer().email());
    assertEquals(CUSTOMER_PHONE, dto.customer().phoneNumber());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsRestaurantCorrectly() {
    ReservationResponseDto dto =
        ReservationAssembler.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto.restaurant());
    assertEquals(restaurant.getId(), dto.restaurant().id());
    assertEquals(restaurant.getName(), dto.restaurant().name());
  }
}
