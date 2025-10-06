package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.api.restaurant.dto.SearchRestaurantsDto;
import ca.ulaval.glo2003.domain.Restaurant;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryRestaurantRepository implements RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();
  private final RestaurantDomainService restaurantDomainService;

  public InMemoryRestaurantRepository(RestaurantDomainService restaurantDomainService) {
    this.restaurantDomainService = restaurantDomainService;
  }

  @Override
  public void save(Restaurant restaurant) {
    restaurantDomainService.validateNameAndCapacityFields(
        restaurant.getName(), restaurant.getCapacity());
    restaurantDomainService.validateHoursFields(
        restaurant.getOpeningHour(), restaurant.getClosingHour());
    restaurants.put(restaurant.getId(), restaurant);
  }

  @Override
  public List<Restaurant> getRestaurantListOfOwner(String ownerId) {
    return restaurants.values().stream()
        .filter(r -> r.getOwnerId().equals(ownerId))
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
    throw new NotFoundException();
  }

  @Override
  public List<Restaurant> getRestaurantBySearch(SearchRestaurantsDto searchRequest) {
    return restaurants.values().stream()
        .filter(r -> restaurantDomainService.matchesSearch(r, searchRequest))
        .collect(Collectors.toList());
  }
}
