package ca.ulaval.glo2003.Dto;

/**
 * Restaurant Data Transfer Object.
 *
 * @param id The identifier of the restaurant
 * @param name The name of the restautant
 * @param capacity The capacity of the restaurant
 * @param hours The opening and closing hours of the restaurant
 */
public record RestaurantDto(String id, String name, Integer capacity, HoursDto hours) {}
