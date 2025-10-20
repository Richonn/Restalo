package ca.ulaval.glo2003.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.common.HoursDto;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HoursAssemblerTest {

  private Hours hours;
  private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);
  private static final LocalTime CLOSE_TIME = LocalTime.of(17, 0);
  private static final Integer RESERVATION_DURATION = 90;

  @BeforeEach
  public void setUp() {
    hours = new Hours(OPEN_TIME, CLOSE_TIME, RESERVATION_DURATION);
  }

  @Test
  public void givenValidHours_whenToHoursDto_thenReturnsNotNull() {
    HoursDto dto = HoursAssembler.toHoursDto(hours);

    assertNotNull(dto);
  }

  @Test
  public void givenValidHours_whenToHoursDto_thenMapsOpenTimeCorrectly() {
    HoursDto dto = HoursAssembler.toHoursDto(hours);

    assertEquals(OPEN_TIME, dto.open());
  }

  @Test
  public void givenValidHours_whenToHoursDto_thenMapsCloseTimeCorrectly() {
    HoursDto dto = HoursAssembler.toHoursDto(hours);

    assertEquals(CLOSE_TIME, dto.close());
  }
}
