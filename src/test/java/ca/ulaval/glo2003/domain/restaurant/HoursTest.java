package ca.ulaval.glo2003.domain.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HoursTest {

  private static HoursFixture hoursFixture;

  @BeforeEach
  public void setUp() {
    hoursFixture = new HoursFixture();
  }

  @Test
  void
      givenValidHoursAndValidReservationStartTime_whenDefineReservationEndTimeAndDefaultReservationDuration_thenReturnReservationEndTime() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(11, 0, 0)).build();
    LocalTime startTime = LocalTime.of(10, 0, 0);
    LocalTime expectedEndTime = LocalTime.of(11, 0, 0);

    assertThat(a_hours.defineReservationEndTime(startTime)).isEqualTo(expectedEndTime);
  }

  @Test
  void
      givenValidHoursAndValidReservationStartTimeAndReservationDuration_whenDefineReservationEndTime_thenReturnReservationEndTime() {
    Hours a_hours =
        hoursFixture
            .withOpen(LocalTime.of(10, 0, 0))
            .withClose(LocalTime.of(11, 0, 0))
            .withReservationDuration(10)
            .build();
    LocalTime startTime = LocalTime.of(10, 0, 0);
    LocalTime expectedEndTime = LocalTime.of(10, 10, 0);

    assertThat(a_hours.defineReservationEndTime(startTime)).isEqualTo(expectedEndTime);
  }

  @Test
  void
      givenValidHoursAndTooSoonReservationStartTime_whenDefineReservationEndTime_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(11, 0, 0)).build();
    LocalTime startTime = LocalTime.of(9, 59, 59);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class, () -> a_hours.defineReservationEndTime(startTime));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant is not open for the selected reservation start time");
  }

  @Test
  void
      givenValidHoursAndTooLateReservationStartTime_whenDefineReservationEndTime_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(11, 0, 0)).build();
    LocalTime startTime = LocalTime.of(10, 30, 0);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class, () -> a_hours.defineReservationEndTime(startTime));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant closes before the end of the reservation");
  }

  @Test
  void givenMissingOpenHours_whenValidateFields_thenThrowsMissingParameterException() {
    Hours a_hours = hoursFixture.withOpen(null).withClose(LocalTime.of(11, 0, 0)).build();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The openingHour of the restaurant is required");
  }

  @Test
  void givenMissingCloseHours_whenValidateFields_thenThrowsMissingParameterException() {
    Hours a_hours = hoursFixture.withOpen(LocalTime.of(11, 0, 0)).withClose(null).build();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The closingHour of the restaurant is required");
  }

  @Test
  void givenCloseHoursBeforeOpen_whenValidateFields_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(11, 0, 0)).withClose(LocalTime.of(10, 0, 0)).build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant closing hour cannot be before the opening hour");
  }

  @Test
  void givenCloseHoursNot1HoursAfterOpen_whenValidateFields_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(11, 0, 0)).withClose(LocalTime.of(11, 30, 0)).build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant needs to be open for at least one hour");
  }

  @Test
  void givenNegativeReservationDuration_whenValidateFields_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture
            .withOpen(LocalTime.of(10, 0, 0))
            .withClose(LocalTime.of(12, 0, 0))
            .withReservationDuration(-10)
            .build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant reservation length needs to be bigger than 0 minutes");
  }

  @Test
  void givenZeroReservationDuration_whenValidateFields_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture
            .withOpen(LocalTime.of(10, 0, 0))
            .withClose(LocalTime.of(12, 0, 0))
            .withReservationDuration(0)
            .build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The restaurant reservation length needs to be bigger than 0 minutes");
  }

  @Test
  void givenTooLowReservationDuration_whenValidateFields_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture
            .withOpen(LocalTime.of(10, 0, 0))
            .withClose(LocalTime.of(12, 0, 0))
            .withReservationDuration(3)
            .build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("Reservation reservationDuration must be between 5 and 180 minutes.");
  }

  @Test
  void givenTooHighReservationDuration_whenValidateFields_thenThrowsInvalidParameterException() {
    Hours a_hours =
        hoursFixture
            .withOpen(LocalTime.of(10, 0, 0))
            .withClose(LocalTime.of(12, 0, 0))
            .withReservationDuration(181)
            .build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_hours::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("Reservation reservationDuration must be between 5 and 180 minutes.");
  }

  @Test
  void givenBothFromAndToNotNull_whenIsFullyOpenDuring_thenReturnTrue() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(12, 0, 0), LocalTime.of(18, 0, 0));

    assertThat(result).isTrue();
  }

  @Test
  void givenBothFromAndToNotNull_whenIsFullyOpenDuring_thenReturnFalse() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(9, 0, 0), LocalTime.of(18, 0, 0));

    assertThat(result).isFalse();
  }

  @Test
  void givenFromBeforeOpenAndToAfterClose_whenIsFullyOpenDuring_thenReturnFalse() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(9, 0, 0), LocalTime.of(21, 0, 0));

    assertThat(result).isFalse();
  }

  @Test
  void givenOnlyFromNotNull_whenIsFullyOpenDuring_thenReturnTrue() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(12, 0, 0), null);

    assertThat(result).isTrue();
  }

  @Test
  void givenOnlyFromNotNullBeforeOpen_whenIsFullyOpenDuring_thenReturnFalse() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(9, 0, 0), null);

    assertThat(result).isFalse();
  }

  @Test
  void givenOnlyFromNotNullAfterClose_whenIsFullyOpenDuring_thenReturnFalse() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(20, 0, 0), null);

    assertThat(result).isFalse();
  }

  @Test
  void givenOnlyToNotNull_whenIsFullyOpenDuring_thenReturnTrue() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(null, LocalTime.of(18, 0, 0));

    assertThat(result).isTrue();
  }

  @Test
  void givenOnlyToNotNullBeforeOpen_whenIsFullyOpenDuring_thenReturnFalse() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(null, LocalTime.of(9, 0, 0));

    assertThat(result).isFalse();
  }

  @Test
  void givenOnlyToNotNullAfterClose_whenIsFullyOpenDuring_thenReturnFalse() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(null, LocalTime.of(21, 0, 0));

    assertThat(result).isFalse();
  }

  @Test
  void givenBothFromAndToNull_whenIsFullyOpenDuring_thenReturnTrue() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(null, null);

    assertThat(result).isTrue();
  }

  @Test
  void givenFromEqualsOpen_whenIsFullyOpenDuring_thenReturnTrue() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(10, 0, 0), LocalTime.of(18, 0, 0));

    assertThat(result).isTrue();
  }

  @Test
  void givenToEqualsClose_whenIsFullyOpenDuring_thenReturnTrue() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(20, 0, 0)).build();

    boolean result = a_hours.isFullyOpenDuring(LocalTime.of(12, 0, 0), LocalTime.of(20, 0, 0));

    assertThat(result).isTrue();
  }

  @Test
  void
      givenValidOpenAndCloseAndReservationDuration_whenGenerateAllSlotIntervalSlices_thenReturnExpectedSlots() {
    LocalTime open = LocalTime.of(10, 0, 0);
    LocalTime close = LocalTime.of(11, 0, 0);
    int reservationDuration = 15;
    Hours a_hours =
        hoursFixture
            .withOpen(open)
            .withClose(close)
            .withReservationDuration(reservationDuration)
            .build();
    List<LocalTime> expectedSlots = new ArrayList<>();
    expectedSlots.add(LocalTime.of(10, 0, 0));
    expectedSlots.add(LocalTime.of(10, 15, 0));
    expectedSlots.add(LocalTime.of(10, 30, 0));
    expectedSlots.add(LocalTime.of(10, 45, 0));

    List<LocalTime> actualSlots = a_hours.generateAllSlotIntervalSlices();

    assertThat(actualSlots).isEqualTo(expectedSlots);
  }

  @Test
  void
      givenValidOpenAndCloseAndReservationDurationButNotSlotIntervalAdjusted_whenGenerateAllSlotIntervalSlices_thenReturnExpectedSlots() {
    LocalTime open = LocalTime.of(10, 10, 0);
    LocalTime close = LocalTime.of(11, 10, 0);
    int reservationDuration = 15;
    Hours a_hours =
        hoursFixture
            .withOpen(open)
            .withClose(close)
            .withReservationDuration(reservationDuration)
            .build();
    List<LocalTime> expectedSlots = new ArrayList<>();
    expectedSlots.add(LocalTime.of(10, 15, 0));
    expectedSlots.add(LocalTime.of(10, 30, 0));
    expectedSlots.add(LocalTime.of(10, 45, 0));

    List<LocalTime> actualSlots = a_hours.generateAllSlotIntervalSlices();

    assertThat(actualSlots).isEqualTo(expectedSlots);
  }
}
