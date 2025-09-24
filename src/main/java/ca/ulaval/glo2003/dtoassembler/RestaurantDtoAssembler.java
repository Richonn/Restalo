package ca.ulaval.glo2003.dtoassembler;

import ca.ulaval.glo2003.domain.Restaurant;
import ca.ulaval.glo2003.dto.RestaurantDto;

public class RestaurantDtoAssembler {
  public static RestaurantDto toRestaurantDto(Restaurant restaurant) {
    return new RestaurantDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursDtoAssembler.toHoursDto(restaurant.getHours()));
  }
}
