package ca.ulaval.glo2003.API.Restaurant.DTO;

import java.time.LocalTime;

public record HoursDTO(LocalTime open, LocalTime close) {}
