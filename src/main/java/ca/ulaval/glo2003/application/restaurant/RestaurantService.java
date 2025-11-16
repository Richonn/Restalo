package ca.ulaval.glo2003.application.restaurant;

import ca.ulaval.glo2003.api.assembler.RestaurantAssembler;
import ca.ulaval.glo2003.api.assembler.RestaurantSearchResponseAssembler;
import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.request.SearchRestaurantsRequestDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.application.mapper.OpenedHoursMapper;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  private final RestaurantDomainService restaurantDomainService;
  private final RestaurantRepository repository;
  private final RestaurantFactory restaurantFactory;

  @Inject
  public RestaurantService(
      RestaurantRepository restaurantRepository,
      RestaurantDomainService restaurantDomainService,
      RestaurantFactory restaurantFactory) {
    this.repository = restaurantRepository;
    this.restaurantDomainService = restaurantDomainService;
    this.restaurantFactory = restaurantFactory;
  }

  public String createRestaurant(String ownerId, RestaurantDto restaurantDto) {

    Integer reservationDuration =
        (restaurantDto.reservations() != null) ? restaurantDto.reservations().duration() : null;
    Restaurant restaurant =
        restaurantFactory.createRestaurant(
            ownerId,
            restaurantDto.name(),
            restaurantDto.capacity(),
            restaurantDto.hours().open(),
            restaurantDto.hours().close(),
            reservationDuration);

    repository.save(restaurant);

    return restaurant.getId();
  }

  public List<RestaurantDto> getRestaurantListOfOwner(String ownerId) {

    List<Restaurant> listRestaurant = repository.getRestaurantListOfOwner(ownerId);
    List<RestaurantDto> listRestaurantDto = new ArrayList<>();

    for (Restaurant restaurant : listRestaurant) {
      listRestaurantDto.add(RestaurantAssembler.toRestaurantDto(restaurant));
    }

    return listRestaurantDto;
  }

  public RestaurantDto getRestaurantByIdOfOwner(String ownerId, String restaurantId) {

    Restaurant restaurant = repository.getRestaurantByIdOfOwner(ownerId, restaurantId);

    if (restaurant == null) {
      throw new NotFoundException();
    }

    return RestaurantAssembler.toRestaurantDto(restaurant);
  }

  public List<RestaurantSearchResponseDto> getRestaurantBySearch(
      SearchRestaurantsRequestDto searchRequest) {

    OpenedHours openedHours = OpenedHoursMapper.toOpenedHours(searchRequest.opened());

    restaurantDomainService.isTimeRangeValid(openedHours);

    List<Restaurant> listAllRestaurant = repository.getRestaurantList();

    return listAllRestaurant.stream()
        .filter(restaurant -> restaurant.matchesName(searchRequest.name()))
        .filter(
            restaurant -> restaurant.isFullyOpenDuring(openedHours.getFrom(), openedHours.getTo()))
        .map(RestaurantSearchResponseAssembler::toRestaurantSearchResponseDto)
        .toList();
  }
}
