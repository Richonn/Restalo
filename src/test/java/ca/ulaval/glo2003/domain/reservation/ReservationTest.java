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
import java.util.ArrayList;
import java.util.List;
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
  void givenAValidReservation_whenAdjustStartTimeTo15minSlice_thenNoExceptions() {

    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(13, 0);

    Mockito.when(reservationTime.getStartTime()).thenReturn(startTime);
    Mockito.when(reservationTime.getEndTime()).thenReturn(endTime);

    doNothing().when(reservationTime).adjustStartTimeToIntervalSlice();

    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    assertDoesNotThrow(a_reservation::adjustStartTimeTo15minSlice);
  }

  @Test
  void givenAValidReservation_whenMatchesCustomerNameWithExactName_thenReturnTrue() {
    String customerName = "customerName";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(customerName)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesCustomerNameWithCorrectBeginningOfName_thenReturnTrue() {
    String customerName = "customerName";
    String customerBeginningName = "customer";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(customerBeginningName)).isTrue();
  }

  @Test
  void
      givenAValidReservation_whenMatchesCustomerNameWithCorrectNameWithCaseAndSpaces_thenReturnTrue() {
    String customerName = "customerName";
    String customerNameWithCaseAndSpaces = "cUSt oMernAM e";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(customerNameWithCaseAndSpaces)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesCustomerNameWithNull_thenReturnTrue() {
    String customerName = "customerName";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(null)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesCustomerNameWithBlank_thenReturnTrue() {
    String customerName = "customerName";
    String customerNameBlank = "";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(customerNameBlank)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesCustomerNameWithEndOfName_thenReturnFalse() {
    String customerName = "customerName";
    String customerNameEnd = "Name";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(customerNameEnd)).isFalse();
  }

  @Test
  void givenAValidReservation_whenMatchesCustomerNameWithNotCorrectName_thenReturnFalse() {
    String customerName = "customerName";
    String invalidCustomerName = "NotTheCorrectName";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    when(customer.getName()).thenReturn(customerName);

    assertThat(a_reservation.matchesCustomerName(invalidCustomerName)).isFalse();
  }

  @Test
  void givenAValidReservation_whenMatchesDateWithNull_thenReturnTrue() {
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    assertThat(a_reservation.matchesDate(null)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesDateWithBlank_thenReturnTrue() {
    String blankDate = "";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    assertThat(a_reservation.matchesDate(blankDate)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesDateWithCorrectDate_thenReturnTrue() {
    String dateString = "2026-12-12";
    LocalDate date = LocalDate.of(2026, 12, 12);
    Reservation a_reservation = reservationFixture.withDate(date).build(reservationTime, customer);

    assertThat(a_reservation.matchesDate(dateString)).isTrue();
  }

  @Test
  void givenAValidReservation_whenMatchesDateWithNotCorrectDate_thenReturnFalse() {
    String dateString = "2025-11-11";
    LocalDate date = LocalDate.of(2026, 12, 12);
    Reservation a_reservation = reservationFixture.withDate(date).build(reservationTime, customer);

    assertThat(a_reservation.matchesDate(dateString)).isFalse();
  }

  @Test
  void givenAValidReservation_whenMatchesDateWithInvalidDate_thenThrowsInvalidParameterException() {
    String dateString = "invalidDate";
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, () -> a_reservation.matchesDate(dateString));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The provided date is not a valid LocalDate format.");
  }

  @Test
  void
      givenValidReservation_whenGenerateAllTimeSlotsInterval_thenReturnExpectedTimeSlotsInterval() {
    Reservation a_reservation = reservationFixture.build(reservationTime, customer);
    LocalTime startTime = LocalTime.of(10, 0);
    LocalTime endTime = LocalTime.of(11, 0);
    when(reservationTime.getStartTime()).thenReturn(startTime);
    when(reservationTime.getEndTime()).thenReturn(endTime);

    List<LocalTime> expectedTimeSlotsInterval = new ArrayList<>();
    expectedTimeSlotsInterval.add(startTime);
    expectedTimeSlotsInterval.add(LocalTime.of(10, 15));
    expectedTimeSlotsInterval.add(LocalTime.of(10, 30));
    expectedTimeSlotsInterval.add(LocalTime.of(10, 45));

    assertThat(a_reservation.generateAllTimeSlotsInterval()).isEqualTo(expectedTimeSlotsInterval);
  }
}
