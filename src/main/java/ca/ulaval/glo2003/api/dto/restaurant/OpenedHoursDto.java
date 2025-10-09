package ca.ulaval.glo2003.api.dto.restaurant;

import java.time.LocalTime;

public record OpenedHoursDto(LocalTime from, LocalTime to) {}
