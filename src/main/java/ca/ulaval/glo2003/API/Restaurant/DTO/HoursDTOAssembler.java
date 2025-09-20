package ca.ulaval.glo2003.API.Restaurant.DTO;

import ca.ulaval.glo2003.Domain.Hours;

public class HoursDTOAssembler {
  public static HoursDTO toHoursDTO(Hours hours) {
    return new HoursDTO(hours.getOpeningHour(), hours.getClosingHour());
  }
}
