package ca.ulaval.glo2003.api.dto.restaurant;

public record RestaurantSearchResponseDto(
    String id, String name, Integer capacity, HoursDto hours) {}
