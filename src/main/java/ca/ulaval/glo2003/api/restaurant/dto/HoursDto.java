package ca.ulaval.glo2003.api.restaurant.dto;

import java.time.LocalTime;

public record HoursDto(LocalTime open, LocalTime close) {}
