package ca.ulaval.glo2003.dtoassembler;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.dto.HoursDto;

/** Assembler for the Hour Data Transfer Object. */
public class HoursDtoAssembler {
  public static HoursDto toHoursDto(Hours hours) {
    return new HoursDto(hours.getOpeningHour(), hours.getClosingHour());
  }
}
