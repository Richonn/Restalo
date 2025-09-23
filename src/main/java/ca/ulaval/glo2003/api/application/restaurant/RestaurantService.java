package ca.ulaval.glo2003.API.Application.Restaurant;

import ca.ulaval.glo2003.Domain.Restaurant;
import ca.ulaval.glo2003.Domain.RestaurantRepository;
import ca.ulaval.glo2003.Dto.RestaurantDto;
import ca.ulaval.glo2003.DtoAssembler.RestaurantDtoAssembler;
import java.util.ArrayList;
import java.util.List;

/** Restaurant service. */
public class RestaurantService {
  private static final RestaurantRepository repository = new RestaurantRepository();

  /**
   * Create a restaurant using the owner id and a restaurant dto.
   *
   * @param ownerId The id of the owner of the restaurant
   * @param restaurantDto The restaurant dto
   * @return The created restaurant
   */
  public String createRestaurant(String ownerId, RestaurantDto restaurantDto) {
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

  /**
   * Find all restaurant for a specific owner id.
   *
   * @param ownerId The id of the owner of the restaurant
   * @return The restaurants linked with the owner id
   */
  public List<RestaurantDto> findAllRestaurantsByOwnerId(String ownerId) {
    List<Restaurant> listRestaurant = repository.findAllRestaurantByOwnerId(ownerId);
    List<RestaurantDto> listRestaurantDto = new ArrayList<>();

    for (Restaurant restaurant : listRestaurant) {
      listRestaurantDto.add(RestaurantDtoAssembler.toRestaurantDto(restaurant));
    }

    return listRestaurantDto;
  }

  /**
   * Find restaurant using its id and the id of the owner of the restaurant.
   *
   * @param ownerId The id of the owner of the restaurant
   * @param restaurantId The id of the restaurant
   * @return The restaurant with the id restaurantId
   */
  public RestaurantDto findRestaurantByOwnerIdAndRestaurantId(String ownerId, String restaurantId) {
    Restaurant restaurant =
        repository.findRestaurantByOwnerIdAndRestaurantId(ownerId, restaurantId);
    return RestaurantDtoAssembler.toRestaurantDto(restaurant);
  }
}
