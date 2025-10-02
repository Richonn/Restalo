package ca.ulaval.glo2003.application.restaurant;

import ca.ulaval.glo2003.api.restaurant.dto.RestaurantDto;
import ca.ulaval.glo2003.api.restaurant.dto.SearchRestaurantsDto;
import ca.ulaval.glo2003.api.restaurant.exceptions.MissingParameterException;
import ca.ulaval.glo2003.api.restaurant.mapper.RestaurantMapper;
import ca.ulaval.glo2003.domain.Restaurant;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  RestaurantDomainService restaurantDomainService = new RestaurantDomainService();
  InMemoryRestaurantRepository repository =
      new InMemoryRestaurantRepository(restaurantDomainService);

  public String createRestaurant(String ownerId, RestaurantDto restaurantDto) {
    restaurantDomainService.validateHeaders(ownerId);

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
    restaurantDomainService.validateHeaders(ownerId);
    List<Restaurant> listRestaurant = repository.getRestaurantListOfOwner(ownerId);
    List<RestaurantDto> listRestaurantDto = new ArrayList<>();

    for (Restaurant restaurant : listRestaurant) {
      listRestaurantDto.add(RestaurantMapper.toRestaurantDto(restaurant));
    }

    return listRestaurantDto;
  }

  public RestaurantDto getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    restaurantDomainService.validateHeaders(ownerId);
    Restaurant restaurant = repository.getRestaurantByIdOfOwner(ownerId, restaurantId);
    return RestaurantMapper.toRestaurantDto(restaurant);
  }

  public List<RestaurantDto> getRestaurantBySearch(SearchRestaurantsDto searchRequest) {
    List<Restaurant> listRestaurant = repository.getRestaurantBySearch(searchRequest);
    return listRestaurant.stream().map(RestaurantMapper::toRestaurantDto).toList();
  }
}
