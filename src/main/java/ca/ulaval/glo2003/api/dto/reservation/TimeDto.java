package ca.ulaval.glo2003.api.dto.reservation;

import java.time.LocalTime;

public record TimeDto(LocalTime start, LocalTime end) {}
