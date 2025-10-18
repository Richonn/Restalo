package ca.ulaval.glo2003.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.reservation.ReservationResponseDto;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationMapperTest {

  private Reservation reservation;
  private Restaurant restaurant;

  @BeforeEach
  public void setUp() {
    reservation =
        new Reservation(
            LocalDate.of(2025, 10, 30),
            new ReservationTime(LocalTime.of(18, 0), LocalTime.of(19, 0)),
            4,
            new Customer("John Doe", "john@example.com", "1234567890"),
            "restaurant-123");

    restaurant =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-123", 120);
  }

  @Test
  public void givenValidReservationAndRestaurant_whenToDto_thenAllFieldsMapped() {
    ReservationResponseDto dto =
        ReservationMapper.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto);
    assertNotNull(dto.number());
    assertEquals(LocalDate.of(2025, 10, 30), dto.date());
    assertEquals(4, dto.groupSize());
    assertNotNull(dto.time());
    assertNotNull(dto.customer());
    assertNotNull(dto.restaurant());
  }

  @Test
  public void givenReservationWithTime_whenToDto_thenTimeMappedCorrectly() {
    ReservationResponseDto dto =
        ReservationMapper.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto.time());
    assertEquals(LocalTime.of(18, 0), dto.time().start());
    assertEquals(LocalTime.of(19, 0), dto.time().end());
  }

  @Test
  public void givenReservationWithCustomer_whenToDto_thenCustomerMappedCorrectly() {
    ReservationResponseDto dto =
        ReservationMapper.toReservationResponseDto(reservation, restaurant);

    assertNotNull(dto.customer());
    assertEquals("John Doe", dto.customer().name());
    assertEquals("john@example.com", dto.customer().email());
    assertEquals("1234567890", dto.customer().phoneNumber());
  }
}
