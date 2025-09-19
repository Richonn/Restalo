package ca.ulaval.glo2003.Restaurant;

import java.time.LocalTime;
import java.util.UUID;

public class Restaurant {
  private final String id;
  private final String name;
  private final int capacity;
  private final LocalTime openingHour;
  private final LocalTime closingHour;

  public Restaurant(String name, int capacity, LocalTime openingHour, LocalTime closingHour) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.capacity = capacity;
    this.openingHour = openingHour;
    this.closingHour = closingHour;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCapacity() {
    return capacity;
  }

  public LocalTime getOpeningHour() {
    return openingHour;
  }

  public LocalTime getClosingHour() {
    return closingHour;
  }
}
