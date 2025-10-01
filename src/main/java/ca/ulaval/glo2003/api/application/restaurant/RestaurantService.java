package ca.ulaval.glo2003.api.application.restaurant;

import ca.ulaval.glo2003.api.domain.Restaurant;
import ca.ulaval.glo2003.api.domain.RestaurantRepository;
import ca.ulaval.glo2003.api.infra.persistence.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.api.restaurant.dto.RestaurantDto;
import ca.ulaval.glo2003.api.restaurant.dto.SearchRestaurantsDto;
import ca.ulaval.glo2003.api.restaurant.exceptions.MissingParameterException;
import ca.ulaval.glo2003.api.restaurant.mapper.RestaurantMapper;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  private static final RestaurantRepository repository = new InMemoryRestaurantRepository();

  private void validateHeaders(String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      throw new MissingParameterException("Missing Owner header");
    }
  }

  public String createRestaurant(String ownerId, RestaurantDto restaurantDto) {
    validateHeaders(ownerId);

    if (restaurantDto == null) {
      throw new MissingParameterException("Missing body");
    }

    Restaurant restaurant =
        new Restaurant(
            restaurantDto.name(),
            restaurantDto.capacity(),
            restaurantDto.hours().open(),
            restaurantDto.hours().close(),
            ownerId);

    repository.save(restaurant);

    return restaurant.getId();
  }

  public List<RestaurantDto> getRestaurantListOfOwner(String ownerId) {
    validateHeaders(ownerId);
    List<Restaurant> listRestaurant = repository.getRestaurantListOfOwner(ownerId);
    List<RestaurantDto> listRestaurantDto = new ArrayList<>();

    for (Restaurant restaurant : listRestaurant) {
      listRestaurantDto.add(RestaurantMapper.toRestaurantDto(restaurant));
    }

    return listRestaurantDto;
  }

  public RestaurantDto getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    validateHeaders(ownerId);
    Restaurant restaurant = repository.getRestaurantByIdOfOwner(ownerId, restaurantId);
    return RestaurantMapper.toRestaurantDto(restaurant);
  }

  public List<RestaurantDto> getRestaurantBySearch(SearchRestaurantsDto searchRequest) {
    LocalTime from = null;
    LocalTime to = null;

    if (searchRequest.opened() != null) {
      from = searchRequest.opened().from();
      to = searchRequest.opened().to();
    }

    List<Restaurant> listRestaurant =
        repository.getRestaurantBySearch(searchRequest.name(), from, to);

    return listRestaurant.stream().map(RestaurantMapper::toRestaurantDto).toList();
  }
}
