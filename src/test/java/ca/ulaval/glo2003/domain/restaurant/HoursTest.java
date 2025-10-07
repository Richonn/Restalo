package ca.ulaval.glo2003.domain.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

public class HoursTest {

  private static HoursFixture hoursFixture;

  @BeforeEach
  public void setUp() {
    hoursFixture = new HoursFixture();
  }

  @Test
  void
      givenValidHoursAndValidReservationStartTime_whenDefineReservationEndTimeAndDefaultReservationLength_thenReturnReservationEndTime() {
    Hours a_hours =
        hoursFixture.withOpen(LocalTime.of(10, 0, 0)).withClose(LocalTime.of(11, 0, 0)).build();
    LocalTime startTime = LocalTime.of(10, 0, 0);
    LocalTime expectedEndTime = LocalTime.of(11, 0, 0);

    assertThat(a_hours.defineReservationEndTime(startTime)).isEqualTo(expectedEndTime);
  }

  @Test
  void
      givenValidHoursAndValidReservationStartTimeAndReservationLength_whenDefineReservationEndTime_thenReturnReservationEndTime() {
    Hours a_hours =
        hoursFixture
            .withOpen(LocalTime.of(10, 0, 0))
            .withClose(LocalTime.of(11, 0, 0))
            .withReservationLength(10)
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
}
