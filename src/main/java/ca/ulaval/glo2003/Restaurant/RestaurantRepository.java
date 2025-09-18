package ca.ulaval.glo2003.Restaurant;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public void save(Restaurant restaurant) {
    restaurants.put(restaurant.getId(), restaurant);
  }

  public Optional<Restaurant> findById(String id) {
    return Optional.ofNullable(restaurants.get(id));
  }

  public Collection<Restaurant> findAll() {
    return restaurants.values();
  }
}
