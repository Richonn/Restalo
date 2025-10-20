package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.api.dto.common.ReservationTimeDto;
import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class ReservationAssembler {

  public static ReservationResponseDto toReservationResponseDto(
      Reservation reservation, Restaurant restaurant) {

    return new ReservationResponseDto(
        reservation.getId(),
        reservation.getDate(),
        new ReservationTimeDto(reservation.getStartTime(), reservation.getEndTime()),
        reservation.getGroupSize(),
        new CustomerDto(
            reservation.getCustomer().getName(),
            reservation.getCustomer().getEmail(),
            reservation.getCustomer().getPhoneNumber()),
        RestaurantReservationResponseAssembler.toRestaurantReservationResponseDto(restaurant));
  }
}
