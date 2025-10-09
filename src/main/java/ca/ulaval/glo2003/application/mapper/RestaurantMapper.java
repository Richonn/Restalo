package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.dto.restaurant.RestaurantDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantMapper {
  public static RestaurantDto toRestaurantDto(Restaurant restaurant) {
    return new RestaurantDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursMapper.toHoursDto(restaurant.getHours()));
  }

  public static Restaurant toRestaurant(RestaurantDto restaurantDto, String ownerId) {
    return new Restaurant(
        restaurantDto.name(),
        restaurantDto.capacity(),
        restaurantDto.hours().open(),
        restaurantDto.hours().close(),
        ownerId);
  }
}
