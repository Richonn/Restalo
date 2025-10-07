package ca.ulaval.glo2003.application.restaurant;

import ca.ulaval.glo2003.api.dto.restaurant.RestaurantDto;
import ca.ulaval.glo2003.api.dto.restaurant.SearchRestaurantsDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.application.mapper.OpenedHoursMapper;
import ca.ulaval.glo2003.application.mapper.RestaurantMapper;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  RestaurantDomainService restaurantDomainService;
  RestaurantRepository repository;

  @Inject
  public RestaurantService(
      RestaurantRepository restaurantRepository, RestaurantDomainService restaurantDomainService) {
    this.repository = restaurantRepository;
    this.restaurantDomainService = restaurantDomainService;
  }

  public String createRestaurant(String ownerId, RestaurantDto restaurantDto) {
    restaurantDomainService.validateHeaders(ownerId);

    if (restaurantDto == null) {
      throw new MissingParameterException("Missing body");
    }

    if (restaurantDto.hours() == null) {
      throw new MissingParameterException("Missing hours body");
    }

    Restaurant restaurant = RestaurantMapper.toRestaurant(restaurantDto, ownerId);

    restaurantDomainService.validateRestaurant(restaurant);

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
    if (restaurant != null) {
      return RestaurantMapper.toRestaurantDto(restaurant);
    }
    throw new NotFoundException();
  }

  public List<RestaurantDto> getRestaurantBySearch(SearchRestaurantsDto searchRequest) {
    restaurantDomainService.isTimeRangeValid(
        OpenedHoursMapper.toOpenedHours(searchRequest.opened()));

    List<Restaurant> listAllRestaurant = repository.getRestaurantList();

    return listAllRestaurant.stream()
        .filter(restaurant -> restaurant.matchesName(searchRequest.name()))
        .filter(
            restaurant ->
                restaurant.isFullyOpenDuring(
                    searchRequest.opened().from(), searchRequest.opened().to()))
        .map(RestaurantMapper::toRestaurantDto)
        .toList();
  }
}
