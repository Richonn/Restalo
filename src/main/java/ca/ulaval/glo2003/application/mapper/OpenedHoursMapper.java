package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.dto.restaurant.OpenedHoursDto;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;

public class OpenedHoursMapper {
  public static OpenedHours toOpenedHours(OpenedHoursDto openedHoursdto) {
    return new OpenedHours(openedHoursdto.from(), openedHoursdto.to());
  }
}
