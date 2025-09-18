package ca.ulaval.glo2003.Restaurant;

import java.time.LocalTime;

public class RestaurantResponse {
  public String id;
  public String name;
  public Integer capacity;
  public LocalTime openingHour;
  public LocalTime closedHour;

  public RestaurantResponse(Restaurant restaurant) {
    this.id = restaurant.getId();
    this.name = restaurant.getName();
    this.capacity = restaurant.getCapacity();
    this.openingHour = restaurant.getOpeningHour();
    this.closedHour = restaurant.getClosingHour();
  }
}
