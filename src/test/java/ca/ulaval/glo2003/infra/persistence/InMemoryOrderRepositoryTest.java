package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.OrderRepository;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryOrderRepositoryTest extends AbstractOrderRepositoryTest {

  private OrderRepository orderRepository;

  @BeforeEach
  public void setUp() {
    super.setUp();
    orderRepository = new InMemoryOrderRepository();
  }

  @Override
  protected OrderRepository getOrderRepository() {
    return orderRepository;
  }

  @Override
  protected void clearOrderRepository() {
    orderRepository = new InMemoryOrderRepository();
  }
}
