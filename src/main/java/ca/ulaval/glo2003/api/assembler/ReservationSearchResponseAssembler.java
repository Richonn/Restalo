package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.api.dto.common.ReservationTimeDto;
import ca.ulaval.glo2003.api.dto.response.ReservationSearchResponseDto;
import ca.ulaval.glo2003.domain.reservation.Reservation;

public class ReservationSearchResponseAssembler {

  public static ReservationSearchResponseDto toReservationSearchResponseDto(
      Reservation reservation) {

    return new ReservationSearchResponseDto(
        reservation.getId(),
        reservation.getDate(),
        new ReservationTimeDto(reservation.getStartTime(), reservation.getEndTime()),
        reservation.getGroupSize(),
        new CustomerDto(
            reservation.getCustomer().getName(),
            reservation.getCustomer().getEmail(),
            reservation.getCustomer().getPhoneNumber()));
  }
}
