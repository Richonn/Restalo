package ca.ulaval.glo2003.api.dto.common;

import java.time.LocalTime;

public record ReservationTimeDto(LocalTime start, LocalTime end) {}
