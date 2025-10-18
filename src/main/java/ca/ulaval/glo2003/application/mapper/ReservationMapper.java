package ca.ulaval.glo2003.application.mapper;

import ca.ulaval.glo2003.api.dto.reservation.CustomerDto;
import ca.ulaval.glo2003.api.dto.reservation.ReservationResponseDto;
import ca.ulaval.glo2003.api.dto.reservation.TimeDto;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class ReservationMapper {

  public static ReservationResponseDto toReservationResponseDto(
      Reservation reservation, Restaurant restaurant) {

    return new ReservationResponseDto(
        reservation.getId(),
        reservation.getDate(),
        new TimeDto(reservation.getStartTime(), reservation.getEndTime()),
        reservation.getGroupSize(),
        new CustomerDto(
            reservation.getCustomer().getName(),
            reservation.getCustomer().getEmail(),
            reservation.getCustomer().getPhoneNumber()),
        RestaurantMapper.toRestaurantDto(restaurant),
        restaurant.getReservationLength());
  }
}
