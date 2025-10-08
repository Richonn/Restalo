package ca.ulaval.glo2003.api.restaurant.mapper;

import ca.ulaval.glo2003.api.restaurant.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.Restaurant;

public class RestaurantMapper {
  public static RestaurantDto toRestaurantDto(Restaurant restaurant) {
    return new RestaurantDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursMapper.toHoursDto(restaurant.getHours()));
  }
}
