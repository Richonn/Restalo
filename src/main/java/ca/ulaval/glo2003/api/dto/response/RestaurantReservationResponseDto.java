package ca.ulaval.glo2003.api.dto.response;

import ca.ulaval.glo2003.api.dto.common.HoursDto;

public record RestaurantReservationResponseDto(
    String id, String name, Integer capacity, HoursDto hours) {}
