package ca.ulaval.glo2003.api.dto.response;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.api.dto.common.ReservationTimeDto;
import java.time.LocalDate;

public record ReservationResponseDto(
    String number,
    LocalDate date,
    ReservationTimeDto time,
    Integer groupSize,
    CustomerDto customer,
    RestaurantReservationResponseDto restaurant) {}
