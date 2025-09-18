package ca.ulaval.glo2003.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public void save(Restaurant restaurant) {
    restaurants.put(restaurant.getId(), restaurant);
  }
}
