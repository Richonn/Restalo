package ca.ulaval.glo2003.api.dto.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
    @NotNull(message = "The date of the reservation is required") LocalDate date,
    @NotNull(message = "The start time of the reservation is required") LocalTime startTime,
    @NotNull(message = "The group size of the reservation is required") Integer groupSize,
    @Valid @NotNull(message = "Missing customer body") CustomerDto customer) {}
