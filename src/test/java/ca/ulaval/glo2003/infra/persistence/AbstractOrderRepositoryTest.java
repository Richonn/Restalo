package ca.ulaval.glo2003.infra.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractOrderRepositoryTest {

  private AbstractOrderRepositoryFixture abstractOrderRepositoryFixture;

  protected abstract OrderRepository getOrderRepository();

  protected abstract void clearOrderRepository();

  @BeforeEach
  void setUp() {
    abstractOrderRepositoryFixture = new AbstractOrderRepositoryFixture();
    clearOrderRepository();
  }

  @Test
  void givenValidOrder_whenSaveOrder_thenNoExceptions() {
    Order order = abstractOrderRepositoryFixture.build();
    assertDoesNotThrow(() -> getOrderRepository().save(order));
  }
}
