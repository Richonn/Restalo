package ca.ulaval.glo2003.api.dto.common;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
    LocalDate date, LocalTime startTime, Integer groupSize, CustomerDto customer) {}
