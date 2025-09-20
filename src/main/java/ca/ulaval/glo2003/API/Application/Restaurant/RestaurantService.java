package ca.ulaval.glo2003.API.Application.Restaurant;

import ca.ulaval.glo2003.DTO.RestaurantDTO;
import ca.ulaval.glo2003.DTOAssembler.RestaurantDTOAssembler;
import ca.ulaval.glo2003.Domain.Restaurant;
import ca.ulaval.glo2003.Domain.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  private static final RestaurantRepository repository = new RestaurantRepository();

  public String createRestaurant(String ownerId, RestaurantDTO restaurantDTO) {
    Restaurant restaurant =
        new Restaurant(
            restaurantDTO.name(),
            restaurantDTO.capacity(),
            restaurantDTO.hours().open(),
            restaurantDTO.hours().close(),
            ownerId);

    repository.save(restaurant);

    return restaurant.getId();
  }

  public List<RestaurantDTO> findAllRestaurantsByOwnerId(String ownerId) {
    List<Restaurant> listRestaurant = repository.findAllRestaurantByOwnerId(ownerId);
    List<RestaurantDTO> listRestaurantDTO = new ArrayList<>();

    for (Restaurant restaurant : listRestaurant) {
      listRestaurantDTO.add(RestaurantDTOAssembler.toRestaurantDTO(restaurant));
    }

    return listRestaurantDTO;
  }

  public RestaurantDTO findRestaurantByOwnerIdAndRestaurantId(String ownerId, String restaurantId) {
    Restaurant restaurant =
        repository.findRestaurantByOwnerIdAndRestaurantId(ownerId, restaurantId);
    return RestaurantDTOAssembler.toRestaurantDTO(restaurant);
  }
}
