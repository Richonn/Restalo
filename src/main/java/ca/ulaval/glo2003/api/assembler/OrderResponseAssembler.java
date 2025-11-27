package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.response.OrderResponseDto;
import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class OrderResponseAssembler {

  public static OrderResponseDto toOrderResponseDto(Order order, Restaurant restaurant) {
    return new OrderResponseDto(
        order.getId(),
        order.getDate().toString(),
        order.getMenu(),
        CustomerAssembler.toCustomerDto(order.getCustomer()),
        OrderRestaurantResponseAssembler.toOrderRestaurantResponseDto(restaurant));
  }
}
