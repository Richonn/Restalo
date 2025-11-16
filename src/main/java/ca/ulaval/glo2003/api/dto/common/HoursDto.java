package ca.ulaval.glo2003.api.dto.common;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record HoursDto(
    @NotNull(message = "The openingHour of the restaurant is required") LocalTime open,
    @NotNull(message = "The closingHour of the restaurant is required") LocalTime close) {}
