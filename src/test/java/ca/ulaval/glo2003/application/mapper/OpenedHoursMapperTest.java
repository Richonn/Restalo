package ca.ulaval.glo2003.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.ulaval.glo2003.api.dto.common.OpenedHoursDto;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class OpenedHoursMapperTest {

  private static final LocalTime FROM = LocalTime.of(10, 0);
  private static final LocalTime TO = LocalTime.of(18, 0);

  @Test
  public void givenNullDto_whenToOpenedHours_thenReturnsOpenedHoursWithNulls() {
    OpenedHours result = OpenedHoursMapper.toOpenedHours(null);

    assertNotNull(result);
    assertNull(result.getFrom());
    assertNull(result.getTo());
  }

  @Test
  public void givenValidDto_whenToOpenedHours_thenMapsFromCorrectly() {
    OpenedHoursDto dto = new OpenedHoursDto(FROM, TO);

    OpenedHours result = OpenedHoursMapper.toOpenedHours(dto);

    assertNotNull(result);
    assertEquals(FROM, result.getFrom());
  }

  @Test
  public void givenValidDto_whenToOpenedHours_thenMapsToCorrectly() {
    OpenedHoursDto dto = new OpenedHoursDto(FROM, TO);

    OpenedHours result = OpenedHoursMapper.toOpenedHours(dto);

    assertNotNull(result);
    assertEquals(TO, result.getTo());
  }
}
