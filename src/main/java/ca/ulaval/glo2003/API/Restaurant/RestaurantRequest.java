package ca.ulaval.glo2003.API.Restaurant;

import ca.ulaval.glo2003.API.Restaurant.Exceptions.MissingParameterException;

import java.security.InvalidParameterException;

public class RestaurantRequest {
  private String id;
  private String name;
  private Integer capacity;
  private String openingHour;
  private String closingHour;

  public RestaurantRequest() {}

  public RestaurantRequest(
      String id, String name, Integer capacity, String openingHour, String closingHour) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
    this.openingHour = openingHour;
    this.closingHour = closingHour;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public String getOpeningHour() {
    return openingHour;
  }

  public void setOpeningHour(String openingHour) {
    this.openingHour = openingHour;
  }

  public String getClosingHour() {
    return closingHour;
  }

  public void setClosingHour(String closingHour) {
    this.closingHour = closingHour;
  }

  public void validate() {
    if (name == null || name.isBlank()) {
      throw new MissingParameterException("name is required");
    }
    if (capacity == null) {
      throw new MissingParameterException("capacity is required");
    }
    if (capacity <= 0) {
      throw new InvalidParameterException("capacity must be positive");
    }
    if (openingHour == null || closingHour == null) {
      throw new MissingParameterException("openingHour and closingHour are required");
    }
  }
}
