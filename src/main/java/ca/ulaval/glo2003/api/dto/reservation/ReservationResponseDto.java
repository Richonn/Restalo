package ca.ulaval.glo2003.api.dto.reservation;

import ca.ulaval.glo2003.api.dto.restaurant.RestaurantDto;
import java.time.LocalDate;

public record ReservationResponseDto(
    String number,
    LocalDate date,
    TimeDto time,
    Integer groupSize,
    CustomerDto customer,
    RestaurantDto restaurant) {}
