package ca.ulaval.glo2003.dto;

import java.time.LocalTime;

/**
 * Hours Data Transfer Object.
 *
 * @param open The opening hour of the restaurant
 * @param close The closing hour of the restaurant
 */
public record HoursDto(LocalTime open, LocalTime close) {}
