package ca.ulaval.glo2003.api.restaurant.mapper;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.api.restaurant.dto.HoursDto;

public class HoursMapper {
  public static HoursDto toHoursDto(Hours hours) {
    return new HoursDto(hours.getOpeningHour(), hours.getClosingHour());
  }
}
