package ca.ulaval.glo2003.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantAssemblerTest {

  private Restaurant restaurant;

  private static final String RESTAURANT_NAME = "Pizza Delamama";
  private static final Integer CAPACITY = 40;
  private static final LocalTime OPEN_TIME = LocalTime.of(10, 0);
  private static final LocalTime CLOSE_TIME = LocalTime.of(21, 0);
  private static final Integer RESERVATION_DURATION = 90;
  private static final String OWNER_ID = "owner-456";

  @BeforeEach
  public void setUp() {
    Hours hours = new Hours(OPEN_TIME, CLOSE_TIME, RESERVATION_DURATION);
    restaurant = new Restaurant(RESTAURANT_NAME, CAPACITY, hours, OWNER_ID);
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenReturnsNotNull() {
    RestaurantDto dto = RestaurantAssembler.toRestaurantDto(restaurant);

    assertNotNull(dto);
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsIdCorrectly() {
    RestaurantDto dto = RestaurantAssembler.toRestaurantDto(restaurant);

    assertEquals(restaurant.getId(), dto.id());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsNameCorrectly() {
    RestaurantDto dto = RestaurantAssembler.toRestaurantDto(restaurant);

    assertEquals(RESTAURANT_NAME, dto.name());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsCapacityCorrectly() {
    RestaurantDto dto = RestaurantAssembler.toRestaurantDto(restaurant);

    assertEquals(CAPACITY, dto.capacity());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsHoursCorrectly() {
    RestaurantDto dto = RestaurantAssembler.toRestaurantDto(restaurant);

    assertNotNull(dto.hours());
    assertEquals(OPEN_TIME, dto.hours().open());
    assertEquals(CLOSE_TIME, dto.hours().close());
  }

  @Test
  public void givenValidRestaurant_whenToDto_thenMapsReservationDurationCorrectly() {
    RestaurantDto dto = RestaurantAssembler.toRestaurantDto(restaurant);

    assertNotNull(dto.reservations());
    assertEquals(RESERVATION_DURATION, dto.reservations().duration());
  }
}
