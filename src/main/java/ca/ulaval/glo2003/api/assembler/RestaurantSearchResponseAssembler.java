package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantSearchResponseAssembler {
  public static RestaurantSearchResponseDto toRestaurantSearchResponseDto(Restaurant restaurant) {
    return new RestaurantSearchResponseDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursAssembler.toHoursDto(restaurant.getHours()));
  }
}
