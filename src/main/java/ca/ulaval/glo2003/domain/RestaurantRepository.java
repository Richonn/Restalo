package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.restaurant.dto.SearchRestaurantsDto;
import java.util.List;

public interface RestaurantRepository {
  void save(Restaurant restaurant);

  List<Restaurant> getRestaurantListOfOwner(String ownerId);

  Restaurant getRestaurantByIdOfOwner(String ownerId, String restaurantId);

  List<Restaurant> getRestaurantBySearch(SearchRestaurantsDto searchRequest);
}
