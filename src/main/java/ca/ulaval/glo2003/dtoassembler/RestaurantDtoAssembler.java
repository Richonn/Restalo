package ca.ulaval.glo2003.dtoassembler;

import ca.ulaval.glo2003.domain.Restaurant;
import ca.ulaval.glo2003.dto.RestaurantDto;

/** Assembler for the Restaurant Data Transfer Object. */
public class RestaurantDtoAssembler {

  /**
   * Turn a restaurant into a RestaurantDto.
   *
   * @param restaurant The restaurant to transform
   * @return A new RestaurantDto
   */
  public static RestaurantDto toRestaurantDto(Restaurant restaurant) {
    return new RestaurantDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursDtoAssembler.toHoursDto(restaurant.getHours()));
  }
}
