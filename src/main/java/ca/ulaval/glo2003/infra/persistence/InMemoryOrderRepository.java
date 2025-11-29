package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.order.Order;
import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
  private final Map<String, Order> orders = new HashMap<>();

  public InMemoryOrderRepository() {}

  @Override
  public void save(Order order) {
    orders.put(order.getId(), order);
  }

  @Override
  public Order getOrderById(String orderId) {
    return orders.get(orderId);
  }
}
