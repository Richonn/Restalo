package ca.ulaval.glo2003.api.dto.restaurant;

import java.time.LocalTime;

public record HoursDto(LocalTime open, LocalTime close) {}
