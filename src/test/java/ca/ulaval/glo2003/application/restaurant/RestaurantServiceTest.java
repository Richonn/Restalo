package ca.ulaval.glo2003.application.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.common.HoursDto;
import ca.ulaval.glo2003.api.dto.common.OpenedHoursDto;
import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.request.SearchRestaurantsRequestDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantServiceTest {

  private RestaurantService restaurantService;
  private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
  private final RestaurantDomainService restaurantDomainService =
      mock(RestaurantDomainService.class);
  private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
  private final RestaurantFactory restaurantFactory = mock(RestaurantFactory.class);
  private RestaurantDto restaurantDto;
  private final Restaurant restaurant = mock(Restaurant.class);

  @BeforeEach
  public void setUp() {
    restaurantService =
        new RestaurantService(
            restaurantRepository,
            reservationRepository,
            restaurantDomainService,
            restaurantFactory);

    HoursDto hoursDto = new HoursDto(LocalTime.of(11, 0), LocalTime.of(22, 0));
    restaurantDto = new RestaurantDto("id-123", "Pizza Delamama", 50, hoursDto, null);
  }

  @Test
  void givenValidRestaurantDto_whenCreateRestaurant_thenReturnExpectedId() {
    String ownerId = "owner-456";
    String expectedId = "123";

    when(restaurantFactory.createRestaurant(
            anyString(), anyString(), anyInt(), any(LocalTime.class), any(LocalTime.class), any()))
        .thenReturn(restaurant);
    when(restaurant.getId()).thenReturn(expectedId);
    doNothing().when(restaurantRepository).save(any(Restaurant.class));

    assertThat(restaurantService.createRestaurant(ownerId, restaurantDto)).isEqualTo(expectedId);
    verify(restaurantRepository).save(any(Restaurant.class));
  }

  @Test
  void givenValidOwnerId_whenGetRestaurantListOfOwner_thenReturnsList() {
    String ownerId = "owner-123";
    Restaurant restaurant1 =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), ownerId, null);
    Restaurant restaurant2 =
        new Restaurant("Burger King", 30, LocalTime.of(10, 0), LocalTime.of(21, 0), ownerId, null);
    List<Restaurant> mockRestaurants = Arrays.asList(restaurant1, restaurant2);

    when(restaurantRepository.getRestaurantListOfOwner(ownerId)).thenReturn(mockRestaurants);

    List<RestaurantDto> result = restaurantService.getRestaurantListOfOwner(ownerId);

    assertNotNull(result);
    assertThat(result).hasSize(2);
    assertThat(result.get(0).name()).isEqualTo("Pizza Delamama");
    assertThat(result.get(1).name()).isEqualTo("Burger King");
  }

  @Test
  void givenOwnerWithNoRestaurants_whenGetRestaurantListOfOwner_thenReturnsEmptyList() {
    String ownerId = "owner-123";
    when(restaurantRepository.getRestaurantListOfOwner(ownerId))
        .thenReturn(Collections.emptyList());

    List<RestaurantDto> result = restaurantService.getRestaurantListOfOwner(ownerId);

    assertNotNull(result);
    assertThat(result).isEmpty();
  }

  @Test
  void givenValidOwnerAndRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnsRestaurantDto() {
    String ownerId = "owner-123";
    String restaurantId = "restaurant-456";
    Restaurant mockRestaurant =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), ownerId, null);

    when(restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId))
        .thenReturn(mockRestaurant);

    RestaurantDto result = restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId);

    assertNotNull(result);
    assertThat(result.name()).isEqualTo("Pizza Delamama");
    assertThat(result.capacity()).isEqualTo(50);
  }

  @Test
  void givenNonExistentRestaurant_whenGetRestaurantByIdOfOwner_thenThrowsNotFoundException() {
    String ownerId = "owner-123";
    String restaurantId = "nonexistent";

    when(restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId));

    assertThat(exception).isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenValidSearchRequest_whenGetRestaurantBySearch_thenReturnsFilteredList() {
    OpenedHoursDto openedHours = new OpenedHoursDto(LocalTime.of(12, 0), LocalTime.of(14, 0));
    SearchRestaurantsRequestDto searchRequest =
        new SearchRestaurantsRequestDto("Pizza", openedHours);

    Restaurant restaurant1 =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-1", null);
    Restaurant restaurant2 =
        new Restaurant(
            "Burger King", 30, LocalTime.of(10, 0), LocalTime.of(21, 0), "owner-2", null);
    Restaurant restaurant3 =
        new Restaurant("Pizza Hut", 40, LocalTime.of(11, 0), LocalTime.of(23, 0), "owner-3", null);

    List<Restaurant> allRestaurants = Arrays.asList(restaurant1, restaurant2, restaurant3);

    doNothing().when(restaurantDomainService).isTimeRangeValid(any());
    when(restaurantRepository.getRestaurantList()).thenReturn(allRestaurants);

    List<RestaurantSearchResponseDto> result =
        restaurantService.getRestaurantBySearch(searchRequest);

    assertNotNull(result);
    assertThat(result).isNotEmpty();
    result.forEach(dto -> assertThat(dto.name()).contains("Pizza"));
  }

  @Test
  void givenSearchWithNullName_whenGetRestaurantBySearch_thenReturnsAllMatchingHours() {
    OpenedHoursDto openedHours = new OpenedHoursDto(LocalTime.of(12, 0), LocalTime.of(14, 0));
    SearchRestaurantsRequestDto searchRequest = new SearchRestaurantsRequestDto(null, openedHours);

    Restaurant restaurant1 =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-1", null);
    Restaurant restaurant2 =
        new Restaurant(
            "Burger King", 30, LocalTime.of(10, 0), LocalTime.of(21, 0), "owner-2", null);

    List<Restaurant> allRestaurants = Arrays.asList(restaurant1, restaurant2);

    doNothing().when(restaurantDomainService).isTimeRangeValid(any());
    when(restaurantRepository.getRestaurantList()).thenReturn(allRestaurants);

    List<RestaurantSearchResponseDto> result =
        restaurantService.getRestaurantBySearch(searchRequest);

    assertNotNull(result);
    assertThat(result).hasSize(2);
  }

  @Test
  void givenNoMatchingRestaurants_whenGetRestaurantBySearch_thenReturnsEmptyList() {
    OpenedHoursDto openedHours = new OpenedHoursDto(LocalTime.of(2, 0), LocalTime.of(3, 0));
    SearchRestaurantsRequestDto searchRequest =
        new SearchRestaurantsRequestDto("Sushi", openedHours);

    Restaurant restaurant1 =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-1", null);

    List<Restaurant> allRestaurants = Collections.singletonList(restaurant1);

    doNothing().when(restaurantDomainService).isTimeRangeValid(any());
    when(restaurantRepository.getRestaurantList()).thenReturn(allRestaurants);

    List<RestaurantSearchResponseDto> result =
        restaurantService.getRestaurantBySearch(searchRequest);

    assertNotNull(result);
    assertThat(result).isEmpty();
  }
}
