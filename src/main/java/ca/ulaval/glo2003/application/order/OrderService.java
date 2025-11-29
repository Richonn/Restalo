package ca.ulaval.glo2003.application.order;

import ca.ulaval.glo2003.api.assembler.OrderResponseAssembler;
import ca.ulaval.glo2003.api.dto.common.OrderDto;
import ca.ulaval.glo2003.api.dto.response.OrderResponseDto;
import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.domain.order.OrderFactory;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

public class OrderService {

  private final OrderRepository orderRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderFactory orderFactory;

  @Inject
  public OrderService(
      OrderRepository orderRepository,
      RestaurantRepository restaurantRepository,
      OrderFactory orderFactory) {
    this.orderRepository = orderRepository;
    this.restaurantRepository = restaurantRepository;
    this.orderFactory = orderFactory;
  }

  public String createOrder(String restaurantId, OrderDto orderDto) {

    if (restaurantRepository.getRestaurantById(restaurantId) == null) {
      throw new NotFoundException();
    }

    Order order =
        orderFactory.createOrder(
            restaurantId,
            orderDto.date(),
            orderDto.menu(),
            orderDto.customer().name(),
            orderDto.customer().email(),
            orderDto.customer().phoneNumber());

    orderRepository.save(order);

    return order.getId();
  }

  public OrderResponseDto getOrderById(String orderId) {
    Order order = orderRepository.getOrderById(orderId);
    if (order == null) {
      throw new NotFoundException();
    }

    Restaurant restaurant = restaurantRepository.getRestaurantById(order.getRestaurantId());

    return OrderResponseAssembler.toOrderResponseDto(order, restaurant);
  }
}
