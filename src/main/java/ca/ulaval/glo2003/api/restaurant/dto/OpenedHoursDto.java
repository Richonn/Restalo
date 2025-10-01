package ca.ulaval.glo2003.api.restaurant.dto;

import java.time.LocalTime;

public record OpenedHoursDto(LocalTime from, LocalTime to) {}
