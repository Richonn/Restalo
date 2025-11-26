package ca.ulaval.glo2003.application.order;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.api.dto.common.OrderDto;
import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.domain.order.OrderFactory;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;
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

  private final OrderDto orderDto = Mockito.mock(OrderDto.class);
  private final CustomerDto customerDto = Mockito.mock(CustomerDto.class);
  private final Order order = Mockito.mock(Order.class);

  @BeforeEach
  void setUp() {
    when(orderDto.customer()).thenReturn(customerDto);
  }

  @Test
  void givenValidRestaurantIdAndOrderDto_whenCreateOrder_thenReturnExpectedId() {
    String orderId = "orderId";

    when(restaurantRepository.getRestaurantById(anyString()))
        .thenReturn(Mockito.mock(Restaurant.class));
    when(orderFactory.createOrder(anyString(), any(), any(), any(), any(), any()))
        .thenReturn(order);
    doNothing().when(orderRepository).save(order);
    when(order.getId()).thenReturn(orderId);

    assertThat(orderService.createOrder(anyString(), orderDto)).isEqualTo(orderId);
  }

  @Test
  void givenInvalidRestaurantIdAndValidOrderDto_whenCreateOrder_thenThrowNotFoundException() {
    String invalidRestaurantId = "invalidRestaurantId";

    when(restaurantRepository.getRestaurantById(invalidRestaurantId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class, () -> orderService.createOrder(invalidRestaurantId, orderDto));
    assertThat(exception).isInstanceOf(NotFoundException.class);
  }
}
