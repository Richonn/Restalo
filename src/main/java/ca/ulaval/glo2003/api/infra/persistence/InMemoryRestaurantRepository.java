package ca.ulaval.glo2003.api.infra.persistence;

import ca.ulaval.glo2003.api.domain.Restaurant;
import ca.ulaval.glo2003.api.domain.RestaurantRepository;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryRestaurantRepository implements RestaurantRepository {
  private final Map<String, Restaurant> restaurants = new HashMap<>();

  @Override
  public void save(Restaurant restaurant) {
    restaurant.validateFields();
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

  private boolean matchesName(Restaurant restaurant, String nameQuery) {
    if (nameQuery == null || nameQuery.isBlank()) return true;

    String normalizedQuery = nameQuery.replaceAll("\\s+", "").toLowerCase();
    String normalizedName = restaurant.getName().replaceAll("\\s+", "").toLowerCase();
    return normalizedName.contains(normalizedQuery);
  }

  private boolean matchesOpenHours(Restaurant restaurant, LocalTime from, LocalTime to) {
    LocalTime openingHour = restaurant.getOpeningHour();
    LocalTime closingHour = restaurant.getClosingHour();

    if (from != null && to != null && from.isAfter(to)) {
      return false;
    } else if (from != null && to != null) {
      return !openingHour.isAfter(from) && !closingHour.isBefore(to);
    } else if (from != null) {
      return !openingHour.isAfter(from);
    } else if (to != null) {
      return !closingHour.isBefore(to);
    }
    return true;
  }

  @Override
  public List<Restaurant> getRestaurantBySearch(String name, LocalTime from, LocalTime to) {
    return restaurants.values().stream()
        .filter(r -> matchesName(r, name))
        .filter(r -> matchesOpenHours(r, from, to))
        .collect(Collectors.toList());
  }
}
