package ca.ulaval.glo2003.api.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.api.dto.response.OrderResponseDto;
import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.domain.reservation.Customer;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class OrderResponseAssemblerTest {

  private final String NAME = "John Deer";
  private final String EMAIL = "john.deer@gmail.com";
  private final String PHONE = "1234567890";

  private final String RESTAURANT_NAME = "Pizza Delamama";
  private final Integer CAPACITY = 40;
  private final LocalTime OPEN_TIME = LocalTime.of(10, 0);
  private final LocalTime CLOSE_TIME = LocalTime.of(21, 0);
  private final Integer RESERVATION_DURATION = 90;
  private final String OWNER_ID = "owner-456";

  private final Customer CUSTOMER = new Customer(NAME, EMAIL, PHONE);
  private final Hours HOURS = new Hours(OPEN_TIME, CLOSE_TIME, RESERVATION_DURATION);
  private final Restaurant RESTAURANT = new Restaurant(RESTAURANT_NAME, CAPACITY, HOURS, OWNER_ID);

  private final String RESTAURANT_ID = RESTAURANT.getId();
  private final LocalDate DATE = LocalDate.now();
  private final String MENU = "menu";

  private final Order ORDER = new Order(RESTAURANT_ID, DATE, MENU, CUSTOMER);

  @Test
  void givenValidOrderAndRestaurant_whenToOrderResponseDto_thenMapsCorrectly() {
    OrderResponseDto orderResponseDto =
        OrderResponseAssembler.toOrderResponseDto(ORDER, RESTAURANT);

    assertThat(orderResponseDto.number()).isEqualTo(ORDER.getId());
    assertThat(orderResponseDto.date()).isEqualTo(DATE.toString());
    assertThat(orderResponseDto.customer().name()).isEqualTo(NAME);
    assertThat(orderResponseDto.customer().email()).isEqualTo(EMAIL);
    assertThat(orderResponseDto.customer().phoneNumber()).isEqualTo(PHONE);
    assertThat(orderResponseDto.restaurant().name()).isEqualTo(RESTAURANT_NAME);
    assertThat(orderResponseDto.restaurant().id()).isEqualTo(RESTAURANT_ID);
    assertThat(orderResponseDto.restaurant().hour().open()).isEqualTo(OPEN_TIME);
    assertThat(orderResponseDto.restaurant().hour().close()).isEqualTo(CLOSE_TIME);
  }
}
