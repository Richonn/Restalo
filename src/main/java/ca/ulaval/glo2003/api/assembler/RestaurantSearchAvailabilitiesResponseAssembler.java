package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.response.RestaurantSearchAvailabilitiesResponseDto;
import java.time.LocalDateTime;

public class RestaurantSearchAvailabilitiesResponseAssembler {

  public static RestaurantSearchAvailabilitiesResponseDto toRestaurantSearchAvailabilitiesResponse(
      LocalDateTime start, int remainingPlaces) {
    return new RestaurantSearchAvailabilitiesResponseDto(start, remainingPlaces);
  }
}
