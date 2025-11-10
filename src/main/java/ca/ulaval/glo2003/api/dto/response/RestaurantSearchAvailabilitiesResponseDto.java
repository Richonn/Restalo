package ca.ulaval.glo2003.api.dto.response;

import java.time.LocalDateTime;

public record RestaurantSearchAvailabilitiesResponseDto(LocalDateTime start, int remainingPlaces) {}
