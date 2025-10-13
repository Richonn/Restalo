package ca.ulaval.glo2003.api.dto.restaurant;

import ca.ulaval.glo2003.api.dto.reservation.ReservationConfigDto;

public record RestaurantDto(
    String id, String name, Integer capacity, HoursDto hours, ReservationConfigDto reservations) {}
