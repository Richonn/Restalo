package ca.ulaval.glo2003.api.domain;

import java.time.LocalTime;
import java.util.List;

public interface RestaurantRepository {
  void save(Restaurant restaurant);

  List<Restaurant> getRestaurantListOfOwner(String ownerId);

  Restaurant getRestaurantByIdOfOwner(String ownerId, String restaurantId);

  List<Restaurant> getRestaurantBySearch(String name, LocalTime from, LocalTime to);
}
