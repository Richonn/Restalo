package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryRestaurantRepository implements RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public InMemoryRestaurantRepository() {}

  @Override
  public void save(Restaurant restaurant) {
    restaurants.put(restaurant.getId(), restaurant);
  }

  @Override
  public List<Restaurant> getRestaurantListOfOwner(String ownerId) {
    return restaurants.values().stream()
        .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
        .collect(Collectors.toList());
  }

  @Override
  public Restaurant getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    List<Restaurant> restaurantListOfOwner = getRestaurantListOfOwner(ownerId);

    for (Restaurant restaurant : restaurantListOfOwner) {
      if (restaurant.getId().equals(restaurantId)) {
        return restaurant;
      }
    }
    return null;
  }

  @Override
  public List<Restaurant> getRestaurantList() {
    return new ArrayList<>(restaurants.values());
  }

  @Override
  public Restaurant getRestaurantById(String restaurantId) {
    return restaurants.get(restaurantId);
  }

  @Override
  public void delete(String restaurantId) {
    restaurants.remove(restaurantId);
  }
}
