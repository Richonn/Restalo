package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.restaurant.exceptions.MissingParameterException;
import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.UUID;

/** Restaurant class. */
public class Restaurant {
  private final String id;
  private final String name;
  private final Integer capacity;
  private final Hours hours;
  private final String ownerId;

  /**
   * The Restaurant contructor.
   *
   * @param name Name of the restaurant
   * @param capacity Capacity of the restaurant
   * @param openingHour Opening hour of the restaurant (format HH:MM:SS)
   * @param closingHour Closing hour of the restaurant (format HH:MM:SS)
   * @param ownerId Id of the owner of the restaurant
   */
  public Restaurant(
      String name, Integer capacity, LocalTime openingHour, LocalTime closingHour, String ownerId) {

    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.capacity = capacity;
    this.hours = new Hours(openingHour, closingHour);
    this.ownerId = ownerId;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public LocalTime getOpeningHour() {
    return hours.getOpeningHour();
  }

  public LocalTime getClosingHour() {
    return hours.getClosingHour();
  }

  public Hours getHours() {
    return hours;
  }

  public String getOwnerId() {
    return ownerId;
  }

  /** Validate all fields. */
  public void validateAll() {
    validateFieldsAreDefined();
    validateFieldsAreValid();
    hours.validateAll();
  }

  private void validateFieldsAreDefined() {
    if (name == null) {
      throw new MissingParameterException("The name of the restaurant is required");
    }
    if (capacity == null) {
      throw new MissingParameterException("The capacity of the restaurant is required");
    }
  }

  private void validateFieldsAreValid() {
    if (name.isBlank()) {
      throw new MissingParameterException("The name of the restaurant cannot be blank");
    }
    if (capacity <= 0) {
      throw new InvalidParameterException("The capacity of the restaurant must be positive");
    }
  }
}
