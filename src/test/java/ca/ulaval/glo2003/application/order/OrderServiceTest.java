package ca.ulaval.glo2003.application.order;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.api.dto.common.OrderDto;
import ca.ulaval.glo2003.api.dto.response.OrderResponseDto;
import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.domain.order.OrderFactory;
import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OrderServiceTest {

  private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
  private final RestaurantRepository restaurantRepository =
      Mockito.mock(RestaurantRepository.class);
  private final OrderFactory orderFactory = Mockito.mock(OrderFactory.class);
  private final OrderService orderService =
      new OrderService(orderRepository, restaurantRepository, orderFactory);

  private final OrderDto mockedOrderDto = Mockito.mock(OrderDto.class);
  private final CustomerDto mockedCustomerDto = Mockito.mock(CustomerDto.class);
  private final Order mockedOrder = Mockito.mock(Order.class);

  private final String RESTAURANT_ID = "restaurantId";
  private final Order order =
      new Order(
          RESTAURANT_ID, LocalDate.now(), "menu", "John Deer", "john.deer@gmail.com", "1234567890");
  private final Restaurant restaurant =
      new Restaurant(
          RESTAURANT_ID,
          "name",
          5,
          new Hours(LocalTime.of(10, 0), LocalTime.of(12, 0), 180),
          "ownerId");

  @BeforeEach
  void setUp() {
    when(mockedOrderDto.customer()).thenReturn(mockedCustomerDto);
  }

  @Test
  void givenValidRestaurantIdAndOrderDto_whenCreateOrder_thenReturnExpectedId() {
    String orderId = "orderId";

    when(restaurantRepository.getRestaurantById(anyString()))
        .thenReturn(Mockito.mock(Restaurant.class));
    when(orderFactory.createOrder(anyString(), any(), any(), any(), any(), any()))
        .thenReturn(mockedOrder);
    doNothing().when(orderRepository).save(mockedOrder);
    when(mockedOrder.getId()).thenReturn(orderId);

    assertThat(orderService.createOrder(anyString(), mockedOrderDto)).isEqualTo(orderId);
  }

  @Test
  void givenInvalidRestaurantIdAndValidOrderDto_whenCreateOrder_thenThrowNotFoundException() {
    String invalidRestaurantId = "invalidRestaurantId";

    when(restaurantRepository.getRestaurantById(invalidRestaurantId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> orderService.createOrder(invalidRestaurantId, mockedOrderDto));
    assertThat(exception).isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenExistingOrderId_whenGetOrderById_thenReturnExpectedOrder() {
    String orderId = order.getId();

    when(orderRepository.getOrderById(orderId)).thenReturn(order);
    when(restaurantRepository.getRestaurantById(RESTAURANT_ID)).thenReturn(restaurant);

    OrderResponseDto orderResponse = orderService.getOrderById(orderId);

    assertThat(orderResponse).isNotNull();
    assertThat(orderResponse.number()).isEqualTo(orderId);
    assertThat(orderResponse.restaurant().id()).isEqualTo(RESTAURANT_ID);
  }

  @Test
  void givenNoExistingOrderId_whenGetOrderById_thenReturnNotFoundException() {
    String orderId = "invalid";

    when(orderRepository.getOrderById(orderId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> orderService.getOrderById(orderId));
    assertThat(exception).isInstanceOf(NotFoundException.class);
  }
}
