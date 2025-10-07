package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalTime;
import java.util.Locale;
import java.util.UUID;

public class Restaurant {
  private final String id;
  private final String name;
  private final Integer capacity;
  private final Hours hours;
  private final String ownerId;

  public Restaurant(
      String name, Integer capacity, LocalTime openingHour, LocalTime closingHour, String ownerId) {

    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.capacity = capacity;
    this.hours = new Hours(openingHour, closingHour, null);
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

  public void validateFields() {
    validateFieldsAreDefined();
    validateFieldsAreValid();
    hours.validateFields();
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

  public boolean isFullyOpenDuring(LocalTime from, LocalTime to) {
    return hours.isFullyOpenDuring(from, to);
  }

  public boolean matchesName(String nameQuery) {
    if (nameQuery == null || nameQuery.isBlank()) {
      return true;
    }
    String normalizedQuery = nameQuery.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
    String normalizedName = name.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
    return normalizedName.contains(normalizedQuery);
  }

  public LocalTime defineReservationEndTime(LocalTime startTime) {
    return hours.defineReservationEndTime(startTime);
  }
}
