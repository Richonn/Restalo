package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.common.HoursDto;
import ca.ulaval.glo2003.domain.restaurant.Hours;

public class HoursAssembler {
  public static HoursDto toHoursDto(Hours hours) {
    return new HoursDto(hours.getOpeningHour(), hours.getClosingHour());
  }
}
