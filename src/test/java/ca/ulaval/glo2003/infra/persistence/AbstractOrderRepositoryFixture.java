package ca.ulaval.glo2003.infra.persistence;

import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.order.Order;
import org.mockito.Mockito;

public class AbstractOrderRepositoryFixture {

  private final Order order = Mockito.mock(Order.class);
  private String ID = "123";

  public Order build() {
    when(order.getId()).thenReturn(ID);

    return order;
  }

  public AbstractOrderRepositoryFixture withOrderId(String orderId) {
    this.ID = orderId;
    return this;
  }
}
