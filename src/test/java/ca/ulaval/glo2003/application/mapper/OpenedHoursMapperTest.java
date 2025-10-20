package ca.ulaval.glo2003.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.ulaval.glo2003.api.dto.common.OpenedHoursDto;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class OpenedHoursMapperTest {

  @Test
  public void givenNullDto_whenToOpenedHours_thenReturnsOpenedHoursWithNulls() {
    OpenedHours result = OpenedHoursMapper.toOpenedHours(null);

    assertNotNull(result);
    assertNull(result.getFrom());
    assertNull(result.getTo());
  }

  @Test
  public void givenValidDto_whenToOpenedHours_thenMapsFromCorrectly() {
    LocalTime from = LocalTime.of(10, 0);
    LocalTime to = LocalTime.of(18, 0);
    OpenedHoursDto dto = new OpenedHoursDto(from, to);

    OpenedHours result = OpenedHoursMapper.toOpenedHours(dto);

    assertNotNull(result);
    assertEquals(from, result.getFrom());
  }

  @Test
  public void givenValidDto_whenToOpenedHours_thenMapsToCorrectly() {
    LocalTime from = LocalTime.of(10, 0);
    LocalTime to = LocalTime.of(18, 0);
    OpenedHoursDto dto = new OpenedHoursDto(from, to);

    OpenedHours result = OpenedHoursMapper.toOpenedHours(dto);

    assertNotNull(result);
    assertEquals(to, result.getTo());
  }
}
