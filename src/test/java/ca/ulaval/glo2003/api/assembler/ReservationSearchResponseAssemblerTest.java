package ca.ulaval.glo2003.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.response.ReservationSearchResponseDto;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationSearchResponseAssemblerTest {

  private Reservation reservation;

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
  }

  @Test
  public void givenValidReservation_whenToDto_thenReturnsNotNull() {
    ReservationSearchResponseDto dto =
        ReservationSearchResponseAssembler.toReservationSearchResponseDto(reservation);

    assertNotNull(dto);
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsIdCorrectly() {
    ReservationSearchResponseDto dto =
        ReservationSearchResponseAssembler.toReservationSearchResponseDto(reservation);

    assertEquals(reservation.getId(), dto.number());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsDateCorrectly() {
    ReservationSearchResponseDto dto =
        ReservationSearchResponseAssembler.toReservationSearchResponseDto(reservation);

    assertEquals(DATE, dto.date());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsGroupSizeCorrectly() {
    ReservationSearchResponseDto dto =
        ReservationSearchResponseAssembler.toReservationSearchResponseDto(reservation);

    assertEquals(GROUP_SIZE, dto.groupSize());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsTimeCorrectly() {
    ReservationSearchResponseDto dto =
        ReservationSearchResponseAssembler.toReservationSearchResponseDto(reservation);

    assertNotNull(dto.time());
    assertEquals(START_TIME, dto.time().start());
    assertEquals(END_TIME, dto.time().end());
  }

  @Test
  public void givenValidReservation_whenToDto_thenMapsCustomerCorrectly() {
    ReservationSearchResponseDto dto =
        ReservationSearchResponseAssembler.toReservationSearchResponseDto(reservation);

    assertNotNull(dto.customer());
    assertEquals(CUSTOMER_NAME, dto.customer().name());
    assertEquals(CUSTOMER_EMAIL, dto.customer().email());
    assertEquals(CUSTOMER_PHONE, dto.customer().phoneNumber());
  }
}
