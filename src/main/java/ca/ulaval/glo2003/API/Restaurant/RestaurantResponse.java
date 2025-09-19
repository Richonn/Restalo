package ca.ulaval.glo2003.API.Restaurant;

import ca.ulaval.glo2003.Domain.Restaurant;

public class RestaurantResponse {
  private String id;
  private String name;
  private Integer capacity;
  private Hours hours;

  public RestaurantResponse(Restaurant restaurant) {
    this.id = restaurant.getId();
    this.name = restaurant.getName();
    this.capacity = restaurant.getCapacity();
    this.hours =
        new Hours(restaurant.getOpeningHour().toString(), restaurant.getClosingHour().toString());
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

  public Hours getHours() {
    return hours;
  }

  public static class Hours {
    public String open;
    public String close;

    public Hours(String open, String close) {
      this.open = open;
      this.close = close;
    }

    public String getOpen() {
      return open;
    }

    public String getClose() {
      return close;
    }
  }
}
