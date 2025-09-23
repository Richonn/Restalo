package ca.ulaval.glo2003.domain;

import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public void save(Restaurant restaurant) {
    restaurant.validateAll();
    restaurants.put(restaurant.getId(), restaurant);
  }

  public List<Restaurant> getRestaurantListOfOwner(String ownerId) {
    return restaurants.values().stream()
        .filter(r -> r.getOwnerId().equals(ownerId))
        .collect(Collectors.toList());
  }

  public Restaurant getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    List<Restaurant> restaurantListOfOwner = getRestaurantListOfOwner(ownerId);

    for (Restaurant restaurant : restaurantListOfOwner) {
      if (restaurant.getId().equals(restaurantId)) {
        return restaurant;
      }
    }
    throw new NotFoundException();
  }
}
