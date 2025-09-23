package ca.ulaval.glo2003.dtoassembler;

import ca.ulaval.glo2003.DTO.HoursDTO;
import ca.ulaval.glo2003.Domain.Hours;

public class HoursDTOAssembler {
  public static HoursDTO toHoursDTO(Hours hours) {
    return new HoursDTO(hours.getOpeningHour(), hours.getClosingHour());
  }
}
