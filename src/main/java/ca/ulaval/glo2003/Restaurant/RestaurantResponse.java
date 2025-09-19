package ca.ulaval.glo2003.Restaurant;

import java.time.LocalTime;

public class RestaurantResponse {
  private String id;
  private String name;
  private Integer capacity;
  private LocalTime openingHour;
  private LocalTime closedHour;

  public RestaurantResponse(Restaurant restaurant) {
    this.id = restaurant.getId();
    this.name = restaurant.getName();
    this.capacity = restaurant.getCapacity();
    this.openingHour = restaurant.getOpeningHour();
    this.closedHour = restaurant.getClosingHour();
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
    return openingHour;
  }

  public LocalTime getClosedHour() {
    return closedHour;
  }
}
