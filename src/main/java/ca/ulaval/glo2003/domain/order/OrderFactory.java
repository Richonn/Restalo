package ca.ulaval.glo2003.domain.order;

import java.time.LocalDate;

public class OrderFactory {

  public OrderFactory() {}

  public Order createOrder(
      String restaurantId, LocalDate date, String menu, String name, String email, String phone) {
    return new Order(restaurantId, date, menu, name, email, phone);
  }
}
