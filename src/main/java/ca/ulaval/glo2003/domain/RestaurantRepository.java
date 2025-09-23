package ca.ulaval.glo2003.Domain;

import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Repository for the Restaurant. */
public class RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public void save(Restaurant restaurant) {
    restaurant.validateAll();
    restaurants.put(restaurant.getId(), restaurant);
  }

  /**
   * Find all the restaurant linked with the owner id parameter.
   *
   * @param ownerId The id of the owner of the restaurant
   * @return All restaurant linked to the owner id parameter
   */
  public List<Restaurant> findAllRestaurantByOwnerId(String ownerId) {
    return restaurants.values().stream()
        .filter(r -> r.getOwnerId().equals(ownerId))
        .collect(Collectors.toList());
  }

  /**
   * Find all the restaurant linked with the owner id and restaurant id parameter.
   *
   * @param ownerId The id of the owner of the restaurant
   * @param restaurantId The id of the restaurant
   * @return The restaurant with the restaurant id parameter linked to the owner id parameter
   */
  public Restaurant findRestaurantByOwnerIdAndRestaurantId(String ownerId, String restaurantId) {
    List<Restaurant> restaurantListOfOwner = findAllRestaurantByOwnerId(ownerId);

    for (Restaurant restaurant : restaurantListOfOwner) {
      if (restaurant.getId().equals(restaurantId)) {
        return restaurant;
      }
    }
    throw new NotFoundException();
  }
}
