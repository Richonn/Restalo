package ca.ulaval.glo2003.api.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.api.dto.response.OrderRestaurantResponseDto;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class OrderRestaurantResponseAssemblerTest {

  private final String RESTAURANT_NAME = "Pizza Delamama";
  private final Integer CAPACITY = 40;
  private final LocalTime OPEN_TIME = LocalTime.of(10, 0);
  private final LocalTime CLOSE_TIME = LocalTime.of(21, 0);
  private final Integer RESERVATION_DURATION = 90;
  private final String OWNER_ID = "owner-456";

  private final Hours hours = new Hours(OPEN_TIME, CLOSE_TIME, RESERVATION_DURATION);
  private final Restaurant restaurant = new Restaurant(RESTAURANT_NAME, CAPACITY, hours, OWNER_ID);

  @Test
  void givenValidRestaurant_whenToOrderRestaurantResponseDto_thenMapsCorrectly() {
    OrderRestaurantResponseDto dto =
        OrderRestaurantResponseAssembler.toOrderRestaurantResponseDto(restaurant);

    assertThat(dto.id()).isEqualTo(restaurant.getId());
    assertThat(dto.name()).isEqualTo(restaurant.getName());
    assertThat(dto.hour().open()).isEqualTo(OPEN_TIME);
    assertThat(dto.hour().close()).isEqualTo(CLOSE_TIME);
  }
}
