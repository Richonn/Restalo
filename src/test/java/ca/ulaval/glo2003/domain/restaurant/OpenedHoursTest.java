package ca.ulaval.glo2003.domain.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpenedHoursTest {

  private static OpenedHoursFixture openedHoursFixture;

  @BeforeEach
  public void setUp() {
    openedHoursFixture = new OpenedHoursFixture();
  }

  @Test
  void givenValidOpenedHoursTimeRange_whenIsTimeRangeValid_thenNoExceptions() {
    OpenedHours openedHours = openedHoursFixture.build();

    assertDoesNotThrow(openedHours::isTimeRangeValid);
  }

  @Test
  void givenInvalidOpenedHoursTimeRange_whenIsTimeRangeValid_thenThrowsInvalidParameterException() {
    OpenedHours openedHours =
        openedHoursFixture.withFrom(LocalTime.of(13, 0, 0)).withTo(LocalTime.of(10, 0, 0)).build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, openedHours::isTimeRangeValid);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Closing hour cannot be before opening hours");
  }

  @Test
  void givenNullOpenedHoursTimeRange_whenIsTimeRangeValid_thenNoExceptions() {
    OpenedHours openedHours = openedHoursFixture.withFrom(null).withTo(null).build();

    assertDoesNotThrow(openedHours::isTimeRangeValid);
  }
}
