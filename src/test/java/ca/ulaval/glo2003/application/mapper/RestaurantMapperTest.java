package ca.ulaval.glo2003.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.common.HoursDto;
import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.common.RestaurantReservationDurationDto;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class RestaurantMapperTest {

  private static final String OWNER_ID = "owner-123";
  private static final String RESTAURANT_NAME = "Pizza Delamama";
  private static final Integer CAPACITY = 50;
  private static final LocalTime OPEN_TIME = LocalTime.of(11, 0);
  private static final LocalTime CLOSE_TIME = LocalTime.of(22, 0);

  @Test
  public void givenDtoWithNullReservations_whenToRestaurant_thenUsesDefaultDuration() {
    HoursDto hoursDto = new HoursDto(OPEN_TIME, CLOSE_TIME);
    RestaurantDto dto = new RestaurantDto(null, RESTAURANT_NAME, CAPACITY, hoursDto, null);

    Restaurant restaurant = RestaurantMapper.toRestaurant(dto, OWNER_ID);

    assertNotNull(restaurant);
    assertEquals(60, restaurant.getReservationLength());
  }

  @Test
  public void givenDtoWithReservations_whenToRestaurant_thenUsesProvidedDuration() {
    HoursDto hoursDto = new HoursDto(OPEN_TIME, CLOSE_TIME);
    RestaurantReservationDurationDto reservations = new RestaurantReservationDurationDto(120);
    RestaurantDto dto = new RestaurantDto(null, RESTAURANT_NAME, CAPACITY, hoursDto, reservations);

    Restaurant restaurant = RestaurantMapper.toRestaurant(dto, OWNER_ID);

    assertNotNull(restaurant);
    assertEquals(120, restaurant.getReservationLength());
  }

  @Test
  public void givenValidDto_whenToRestaurant_thenMapsNameCorrectly() {
    HoursDto hoursDto = new HoursDto(OPEN_TIME, CLOSE_TIME);
    RestaurantDto dto = new RestaurantDto(null, RESTAURANT_NAME, CAPACITY, hoursDto, null);

    Restaurant restaurant = RestaurantMapper.toRestaurant(dto, OWNER_ID);

    assertEquals(RESTAURANT_NAME, restaurant.getName());
  }

  @Test
  public void givenValidDto_whenToRestaurant_thenMapsCapacityCorrectly() {
    HoursDto hoursDto = new HoursDto(OPEN_TIME, CLOSE_TIME);
    RestaurantDto dto = new RestaurantDto(null, RESTAURANT_NAME, CAPACITY, hoursDto, null);

    Restaurant restaurant = RestaurantMapper.toRestaurant(dto, OWNER_ID);

    assertEquals(CAPACITY, restaurant.getCapacity());
  }

  @Test
  public void givenValidDto_whenToRestaurant_thenMapsHoursCorrectly() {
    HoursDto hoursDto = new HoursDto(OPEN_TIME, CLOSE_TIME);
    RestaurantDto dto = new RestaurantDto(null, RESTAURANT_NAME, CAPACITY, hoursDto, null);

    Restaurant restaurant = RestaurantMapper.toRestaurant(dto, OWNER_ID);

    assertEquals(OPEN_TIME, restaurant.getOpeningHour());
    assertEquals(CLOSE_TIME, restaurant.getClosingHour());
  }

  @Test
  public void givenValidDto_whenToRestaurant_thenMapsOwnerIdCorrectly() {
    HoursDto hoursDto = new HoursDto(OPEN_TIME, CLOSE_TIME);
    RestaurantDto dto = new RestaurantDto(null, RESTAURANT_NAME, CAPACITY, hoursDto, null);

    Restaurant restaurant = RestaurantMapper.toRestaurant(dto, OWNER_ID);

    assertEquals(OWNER_ID, restaurant.getOwnerId());
  }
}
