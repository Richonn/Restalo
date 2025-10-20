package ca.ulaval.glo2003.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantSearchResponseAssemblerTest {

  private Restaurant restaurant;

  private static final String RESTAURANT_NAME = "Pizza Delamama";
  private static final Integer CAPACITY = 25;
  private static final LocalTime OPEN_TIME = LocalTime.of(11, 30);
  private static final LocalTime CLOSE_TIME = LocalTime.of(20, 0);
  private static final Integer RESERVATION_DURATION = 60;
  private static final String OWNER_ID = "owner-321";

  @BeforeEach
  public void setUp() {
    Hours hours = new Hours(OPEN_TIME, CLOSE_TIME, RESERVATION_DURATION);
    restaurant = new Restaurant(RESTAURANT_NAME, CAPACITY, hours, OWNER_ID);
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenReturnsNotNull() {
    RestaurantSearchResponseDto dto =
        RestaurantSearchResponseAssembler.toRestaurantSearchResponseDto(restaurant);

    assertNotNull(dto);
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsIdCorrectly() {
    RestaurantSearchResponseDto dto =
        RestaurantSearchResponseAssembler.toRestaurantSearchResponseDto(restaurant);

    assertEquals(restaurant.getId(), dto.id());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsNameCorrectly() {
    RestaurantSearchResponseDto dto =
        RestaurantSearchResponseAssembler.toRestaurantSearchResponseDto(restaurant);

    assertEquals(RESTAURANT_NAME, dto.name());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsCapacityCorrectly() {
    RestaurantSearchResponseDto dto =
        RestaurantSearchResponseAssembler.toRestaurantSearchResponseDto(restaurant);

    assertEquals(CAPACITY, dto.capacity());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsHoursCorrectly() {
    RestaurantSearchResponseDto dto =
        RestaurantSearchResponseAssembler.toRestaurantSearchResponseDto(restaurant);

    assertNotNull(dto.hours());
    assertEquals(OPEN_TIME, dto.hours().open());
    assertEquals(CLOSE_TIME, dto.hours().close());
  }
}
