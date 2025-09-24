package ca.ulaval.glo2003.application.restaurant;

import ca.ulaval.glo2003.api.restaurant.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.Restaurant;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.dto.RestaurantDto;
import ca.ulaval.glo2003.dtoassembler.RestaurantDtoAssembler;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  private static final RestaurantRepository repository = new RestaurantRepository();

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
      listRestaurantDto.add(RestaurantDtoAssembler.toRestaurantDto(restaurant));
    }

    return listRestaurantDto;
  }

  public RestaurantDto getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    validateHeaders(ownerId);
    Restaurant restaurant = repository.getRestaurantByIdOfOwner(ownerId, restaurantId);
    return RestaurantDtoAssembler.toRestaurantDto(restaurant);
  }
}
