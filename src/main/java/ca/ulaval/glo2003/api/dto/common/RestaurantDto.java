package ca.ulaval.glo2003.api.dto.common;

public record RestaurantDto(
    String id,
    String name,
    Integer capacity,
    HoursDto hours,
    RestaurantReservationDurationDto reservations) {}
