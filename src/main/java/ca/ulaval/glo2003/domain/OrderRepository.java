package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.order.Order;

public interface OrderRepository {
  void save(Order order);
}
