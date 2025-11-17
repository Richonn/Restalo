package ca.ulaval.glo2003.api.dto.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantDto(
    String id,
    @NotNull(message = "The name of the restaurant is required")
        @NotBlank(message = "The name of the restaurant cannot be blank")
        String name,
    @NotNull(message = "The capacity of the restaurant is required") Integer capacity,
    @Valid @NotNull(message = "Missing hours body") HoursDto hours,
    RestaurantReservationDurationDto reservations) {}
