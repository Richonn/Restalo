package ca.ulaval.glo2003.domain.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReservationTest {

  private static ReservationFixture reservationFixture;

  private final ReservationTime reservationTime = Mockito.mock(ReservationTime.class);
  private final Customer customer = Mockito.mock(Customer.class);

  @BeforeEach
  public void setUp() {
    reservationFixture = new ReservationFixture();

    when(reservationTime.getStartTime()).thenReturn(LocalTime.of(18, 0));
    when(reservationTime.getEndTime()).thenReturn(LocalTime.of(20, 0));
  }

  @Test
  void givenValidReservation_whenValidateFields_thenNoExceptions() {

    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(13, 0);

    Mockito.when(reservationTime.getStartTime()).thenReturn(startTime);
    Mockito.when(reservationTime.getEndTime()).thenReturn(endTime);

    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    doNothing().when(reservationTime).validateStartTimeIsDefined();
    doNothing().when(customer).validateFields();

    assertDoesNotThrow(a_reservation::validateFields);
  }

  @Test
  void givenNullDate_whenValidateFields_thenThrowsMissingParameterException() {
    Reservation a_reservation = reservationFixture.withDate(null).build(reservationTime, customer);

    doNothing().when(reservationTime).validateStartTimeIsDefined();
    doNothing().when(customer).validateFields();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_reservation::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The date of the reservation is required");
  }

  @Test
  void givenNullGroupSize_whenValidateFields_thenThrowsMissingParameterException() {
    Reservation a_reservation =
        reservationFixture.withGroupSize(null).build(reservationTime, customer);

    doNothing().when(reservationTime).validateStartTimeIsDefined();
    doNothing().when(customer).validateFields();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_reservation::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The group size of the reservation is required");
  }

  @Test
  void givenInvalidDate_whenValidateFields_thenThrowsInvalidParameterException() {
    Reservation a_reservation =
        reservationFixture.withDate(LocalDate.of(1900, 1, 1)).build(reservationTime, customer);

    doNothing().when(reservationTime).validateStartTimeIsDefined();
    doNothing().when(customer).validateFields();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_reservation::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The date of the reservation cannot be in the past");
  }

  @Test
  void givenInvalidGroupSize_whenValidateFields_thenThrowsInvalidParameterException() {
    Reservation a_reservation =
        reservationFixture.withGroupSize(0).build(reservationTime, customer);

    doNothing().when(reservationTime).validateStartTimeIsDefined();
    doNothing().when(customer).validateFields();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_reservation::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The group size of the reservation must be positive");
  }

  @Test
  void givenAValidReservation_whenSetReservationEndTime_thenNoExceptions() {
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);
    LocalTime endTime = LocalTime.of(23, 59, 59);

    doNothing().when(reservationTime).setEndTime(endTime);

    assertDoesNotThrow(() -> a_reservation.setReservationEndTime(endTime));
  }

  @Test
  void givenAValidReservation_whenDdjustStartTimeTo15minSlice_thenNoExceptions() {

    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(13, 0);

    Mockito.when(reservationTime.getStartTime()).thenReturn(startTime);
    Mockito.when(reservationTime.getEndTime()).thenReturn(endTime);

    doNothing().when(reservationTime).adjustStartTimeTo15minSlice();

    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    assertDoesNotThrow(a_reservation::adjustStartTimeTo15minSlice);
  }
}
