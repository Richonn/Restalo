package ca.ulaval.glo2003.application.restaurant;

import ca.ulaval.glo2003.api.assembler.RestaurantAssembler;
import ca.ulaval.glo2003.api.assembler.RestaurantSearchResponseAssembler;
import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.request.SearchRestaurantsRequestDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.application.mapper.OpenedHoursMapper;
import ca.ulaval.glo2003.application.mapper.RestaurantMapper;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
  private final RestaurantDomainService restaurantDomainService;
  private final RestaurantRepository restaurantRepository;
  private final ReservationRepository reservationRepository;

  @Inject
  public RestaurantService(
      RestaurantRepository restaurantRepository,
      ReservationRepository reservationRepository,
      RestaurantDomainService restaurantDomainService) {
    this.restaurantRepository = restaurantRepository;
    this.reservationRepository = reservationRepository;
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
    restaurantRepository.save(restaurant);

    return restaurant.getId();
  }

  public List<RestaurantDto> getRestaurantListOfOwner(String ownerId) {
    restaurantDomainService.validateHeaders(ownerId);

    List<Restaurant> listRestaurant = restaurantRepository.getRestaurantListOfOwner(ownerId);
    List<RestaurantDto> listRestaurantDto = new ArrayList<>();

    for (Restaurant restaurant : listRestaurant) {
      listRestaurantDto.add(RestaurantAssembler.toRestaurantDto(restaurant));
    }

    return listRestaurantDto;
  }

  public RestaurantDto getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    restaurantDomainService.validateHeaders(ownerId);

    Restaurant restaurant = restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId);

    if (restaurant == null) {
      throw new NotFoundException();
    }

    return RestaurantAssembler.toRestaurantDto(restaurant);
  }

  public List<RestaurantSearchResponseDto> getRestaurantBySearch(
      SearchRestaurantsRequestDto searchRequest) {

    OpenedHours openedHours = OpenedHoursMapper.toOpenedHours(searchRequest.opened());

    restaurantDomainService.isTimeRangeValid(openedHours);

    List<Restaurant> listAllRestaurant = restaurantRepository.getRestaurantList();

    return listAllRestaurant.stream()
        .filter(restaurant -> restaurant.matchesName(searchRequest.name()))
        .filter(
            restaurant -> restaurant.isFullyOpenDuring(openedHours.getFrom(), openedHours.getTo()))
        .map(RestaurantSearchResponseAssembler::toRestaurantSearchResponseDto)
        .toList();
  }

  public void deleteRestaurant(String ownerId, String restaurantId) {

    restaurantDomainService.validateHeaders(ownerId);

    Restaurant restaurant = restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId);

    if (restaurant == null) {
      throw new NotFoundException();
    }

    reservationRepository.deleteAllReservationOfRestaurant(restaurant.getId());
    restaurantRepository.delete(restaurant.getId());
  }
}
