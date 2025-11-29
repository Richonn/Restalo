package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.response.OrderRestaurantResponseDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class OrderRestaurantResponseAssembler {

  public static OrderRestaurantResponseDto toOrderRestaurantResponseDto(Restaurant restaurant) {
    return new OrderRestaurantResponseDto(
        restaurant.getId(), restaurant.getName(), HoursAssembler.toHoursDto(restaurant.getHours()));
  }
}
