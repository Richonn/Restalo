package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.common.RestaurantReservationDurationDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantAssembler {
  public static RestaurantDto toRestaurantDto(Restaurant restaurant) {
    return new RestaurantDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursAssembler.toHoursDto(restaurant.getHours()),
        new RestaurantReservationDurationDto(restaurant.getReservationLength()));
  }
}
