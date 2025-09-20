package ca.ulaval.glo2003.Domain;

import ca.ulaval.glo2003.API.Restaurant.DTO.RestaurantDTO;
import ca.ulaval.glo2003.API.Restaurant.DTO.RestaurantDTOAssembler;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  public void save(Restaurant restaurant) {
    restaurants.put(restaurant.getId(), restaurant);
  }

  public List<RestaurantDTO> findRestaurantByOwnerId(String ownerId) {
    List<RestaurantDTO> restaurantListOfOwner =
        restaurants.values().stream()
            .filter(r -> r.getOwnerId().equals(ownerId))
            .map(RestaurantDTOAssembler::toRestaurantDTO)
            .toList();

    if (restaurantListOfOwner.isEmpty()) {
      throw new InvalidParameterException("The specified OwnerID doesn't own any restaurants.");
    }
    return restaurantListOfOwner;
  }

  public RestaurantDTO findRestaurantByOwnerIdAndRestaurantId(String ownerId, String restaurantId) {
    List<RestaurantDTO> restaurantListOfOwner = findRestaurantByOwnerId(ownerId);

    for (RestaurantDTO restaurantDTO : restaurantListOfOwner) {
      if (restaurantDTO.id().equals(restaurantId)) {
        return restaurantDTO;
      }
    }
    throw new InvalidParameterException("The specified restaurant ID doesn't exist.");
  }
}
