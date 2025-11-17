package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Restaurant {
  private final String id;
  private final String name;
  private final Integer capacity;
  private final Hours hours;
  private final String ownerId;

  public Restaurant(
      String name,
      Integer capacity,
      LocalTime openingHour,
      LocalTime closingHour,
      String ownerId,
      Integer reservationDuration) {

    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.capacity = capacity;
    this.hours = new Hours(openingHour, closingHour, reservationDuration);
    this.ownerId = ownerId;
  }

  public Restaurant(String name, Integer capacity, Hours hours, String ownerId) {

    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.capacity = capacity;
    this.hours = hours;
    this.ownerId = ownerId;
  }

  public Restaurant(String id, String name, Integer capacity, Hours hours, String ownerId) {

    this.id = id;
    this.name = name;
    this.capacity = capacity;
    this.hours = hours;
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

  public int getReservationLength() {
    return hours.getReservationDuration();
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

  public Map<LocalTime, Integer> generateDefaultAvailability() {
    List<LocalTime> allSlotIntervalSlices = hours.generateAllSlotIntervalSlices();
    Map<LocalTime, Integer> defaultAvailability = new HashMap<>();

    for (LocalTime slotTime : allSlotIntervalSlices) {
      defaultAvailability.put(slotTime, capacity);
    }

    return defaultAvailability;
  }
}
