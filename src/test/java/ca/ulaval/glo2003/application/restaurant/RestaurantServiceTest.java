package ca.ulaval.glo2003.application.restaurant;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.restaurant.*;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
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
  private RestaurantDto restaurantDto;
  private HoursDto hoursDto;

  @BeforeEach
  public void setUp() {
    restaurantService = new RestaurantService(restaurantRepository, restaurantDomainService);

    hoursDto = new HoursDto(LocalTime.of(11, 0), LocalTime.of(22, 0));
    restaurantDto = new RestaurantDto("id-123", "Pizza Delamama", 50, hoursDto, null);
  }

  @Test
  void givenValidRestaurantDto_whenCreateRestaurant_thenReturnExpectedId() {
    String ownerId = "owner-456";
    Restaurant mockRestaurant =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), ownerId, null);

    doNothing().when(restaurantDomainService).validateHeaders(ownerId);
    doNothing().when(restaurantDomainService).validateRestaurant(any(Restaurant.class));
    doNothing().when(restaurantRepository).save(any(Restaurant.class));

    String result = restaurantService.createRestaurant(ownerId, restaurantDto);

    assertNotNull(result);
    verify(restaurantDomainService).validateHeaders(ownerId);
    verify(restaurantDomainService).validateRestaurant(any(Restaurant.class));
    verify(restaurantRepository).save(any(Restaurant.class));
  }

  @Test
  void givenNullRestaurantDto_whenCreateRestaurant_thenThrowsMissingParameterException() {
    String ownerId = "owner-123";
    doNothing().when(restaurantDomainService).validateHeaders(ownerId);

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantService.createRestaurant(ownerId, null));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing body");
  }

  @Test
  void givenRestaurantDtoWithNullHours_whenCreateRestaurant_thenThrowsMissingParameterException() {
    String ownerId = "owner-123";
    RestaurantDto dtoWithoutHours = new RestaurantDto("id-123", "Pizza Delamama", 50, null, null);
    doNothing().when(restaurantDomainService).validateHeaders(ownerId);

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantService.createRestaurant(ownerId, dtoWithoutHours));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing hours body");
  }

  @Test
  void givenInvalidOwnerId_whenCreateRestaurant_thenThrowsException() {
    String invalidOwnerId = "invalid";
    doThrow(new MissingParameterException("Invalid owner"))
        .when(restaurantDomainService)
        .validateHeaders(invalidOwnerId);

    assertThrows(
        MissingParameterException.class,
        () -> restaurantService.createRestaurant(invalidOwnerId, restaurantDto));
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

    doNothing().when(restaurantDomainService).validateHeaders(ownerId);
    when(restaurantRepository.getRestaurantListOfOwner(ownerId)).thenReturn(mockRestaurants);

    List<RestaurantDto> result = restaurantService.getRestaurantListOfOwner(ownerId);

    assertNotNull(result);
    assertThat(result).hasSize(2);
    assertThat(result.get(0).name()).isEqualTo("Pizza Delamama");
    assertThat(result.get(1).name()).isEqualTo("Burger King");
    verify(restaurantDomainService).validateHeaders(ownerId);
  }

  @Test
  void givenOwnerWithNoRestaurants_whenGetRestaurantListOfOwner_thenReturnsEmptyList() {
    String ownerId = "owner-123";
    doNothing().when(restaurantDomainService).validateHeaders(ownerId);
    when(restaurantRepository.getRestaurantListOfOwner(ownerId))
        .thenReturn(Collections.emptyList());

    List<RestaurantDto> result = restaurantService.getRestaurantListOfOwner(ownerId);

    assertNotNull(result);
    assertThat(result).isEmpty();
  }

  @Test
  void givenInvalidOwnerId_whenGetRestaurantListOfOwner_thenThrowsException() {
    String invalidOwnerId = "invalid";
    doThrow(new MissingParameterException("Invalid owner"))
        .when(restaurantDomainService)
        .validateHeaders(invalidOwnerId);

    assertThrows(
        MissingParameterException.class,
        () -> restaurantService.getRestaurantListOfOwner(invalidOwnerId));
  }

  @Test
  void givenValidOwnerAndRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnsRestaurantDto() {
    String ownerId = "owner-123";
    String restaurantId = "restaurant-456";
    Restaurant mockRestaurant =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), ownerId, null);

    doNothing().when(restaurantDomainService).validateHeaders(ownerId);
    when(restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId))
        .thenReturn(mockRestaurant);

    RestaurantDto result = restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId);

    assertNotNull(result);
    assertThat(result.name()).isEqualTo("Pizza Delamama");
    assertThat(result.capacity()).isEqualTo(50);
    verify(restaurantDomainService).validateHeaders(ownerId);
  }

  @Test
  void givenNonExistentRestaurant_whenGetRestaurantByIdOfOwner_thenThrowsNotFoundException() {
    String ownerId = "owner-123";
    String restaurantId = "nonexistent";

    doNothing().when(restaurantDomainService).validateHeaders(ownerId);
    when(restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId));

    assertThat(exception).isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenInvalidOwnerId_whenGetRestaurantByIdOfOwner_thenThrowsException() {
    String invalidOwnerId = "invalid";
    String restaurantId = "restaurant-123";
    doThrow(new MissingParameterException("Invalid owner"))
        .when(restaurantDomainService)
        .validateHeaders(invalidOwnerId);

    assertThrows(
        MissingParameterException.class,
        () -> restaurantService.getRestaurantByIdOfOwner(invalidOwnerId, restaurantId));
  }

  @Test
  void givenValidSearchRequest_whenGetRestaurantBySearch_thenReturnsFilteredList() {
    OpenedHoursDto openedHours = new OpenedHoursDto(LocalTime.of(12, 0), LocalTime.of(14, 0));
    SearchRestaurantsDto searchRequest = new SearchRestaurantsDto("Pizza", openedHours);

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
    SearchRestaurantsDto searchRequest = new SearchRestaurantsDto(null, openedHours);

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
    SearchRestaurantsDto searchRequest = new SearchRestaurantsDto("Sushi", openedHours);

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
