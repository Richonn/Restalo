package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.dto.common.OpenedHoursDto;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;

public class OpenedHoursMapper {
  public static OpenedHours toOpenedHours(OpenedHoursDto openedHoursDto) {

    if (openedHoursDto == null) {
      return new OpenedHours(null, null);
    }

    return new OpenedHours(openedHoursDto.from(), openedHoursDto.to());
  }
}
