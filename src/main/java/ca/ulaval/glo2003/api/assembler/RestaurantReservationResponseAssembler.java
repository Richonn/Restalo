package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.response.RestaurantReservationResponseDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantReservationResponseAssembler {
  public static RestaurantReservationResponseDto toRestaurantReservationResponseDto(
      Restaurant restaurant) {
    return new RestaurantReservationResponseDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursAssembler.toHoursDto(restaurant.getHours()));
  }
}
