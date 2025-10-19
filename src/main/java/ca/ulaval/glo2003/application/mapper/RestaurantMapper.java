package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantMapper {

  public static Restaurant toRestaurant(RestaurantDto restaurantDto, String ownerId) {

    Integer reservationDuration =
        (restaurantDto.reservations() != null)
            ? restaurantDto.reservations().duration()
            : null;

    return new Restaurant(
        restaurantDto.name(),
        restaurantDto.capacity(),
        restaurantDto.hours().open(),
        restaurantDto.hours().close(),
        ownerId,
        reservationDuration);
  }
}
