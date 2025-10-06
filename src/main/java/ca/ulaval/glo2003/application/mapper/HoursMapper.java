package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.restaurant.dto.HoursDto;
import ca.ulaval.glo2003.domain.restaurant.Hours;

public class HoursMapper {
  public static HoursDto toHoursDto(Hours hours) {
    return new HoursDto(hours.getOpeningHour(), hours.getClosingHour());
  }
}
