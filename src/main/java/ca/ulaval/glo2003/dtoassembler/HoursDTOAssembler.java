package ca.ulaval.glo2003.dtoassembler;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.dto.HoursDTO;

public class HoursDTOAssembler {
  public static HoursDTO toHoursDTO(Hours hours) {
    return new HoursDTO(hours.getOpeningHour(), hours.getClosingHour());
  }
}
