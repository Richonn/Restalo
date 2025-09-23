package ca.ulaval.glo2003.dtoassembler;

import ca.ulaval.glo2003.DTO.RestaurantDTO;
import ca.ulaval.glo2003.Domain.Restaurant;

public class RestaurantDTOAssembler {
  public static RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
    return new RestaurantDTO(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursDTOAssembler.toHoursDTO(restaurant.getHours()));
  }
}
