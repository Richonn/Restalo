package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.dto.reservation.ReservationConfigDto;
import ca.ulaval.glo2003.api.dto.restaurant.RestaurantDto;
import ca.ulaval.glo2003.api.dto.restaurant.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantMapper {
  public static RestaurantDto toRestaurantDto(Restaurant restaurant) {
    return new RestaurantDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursMapper.toHoursDto(restaurant.getHours()),
        new ReservationConfigDto(restaurant.getReservationLength()));
  }

  public static Restaurant toRestaurant(RestaurantDto restaurantDto, String ownerId) {

    Integer reservationDuration =
        (restaurantDto.reservations() != null)
            ? restaurantDto.reservations().reservationDuration()
            : null;

    return new Restaurant(
        restaurantDto.name(),
        restaurantDto.capacity(),
        restaurantDto.hours().open(),
        restaurantDto.hours().close(),
        ownerId,
        reservationDuration);
  }

  public static RestaurantSearchResponseDto toRestaurantSearchResponseDto(Restaurant restaurant) {
    return new RestaurantSearchResponseDto(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        HoursMapper.toHoursDto(restaurant.getHours()));
  }
}
