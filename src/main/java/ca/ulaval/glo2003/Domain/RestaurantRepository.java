package ca.ulaval.glo2003.Domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public void save(Restaurant restaurant) {
    restaurants.put(restaurant.getId(), restaurant);
  }

  public List<Restaurant> findByOwnerId(String ownerId) {
    return restaurants.values().stream()
        .filter(r -> r.getOwnerId().equals(ownerId))
        .collect(Collectors.toList());
  }
}
