package ca.ulaval.glo2003.domain;

import java.time.LocalTime;
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
}
