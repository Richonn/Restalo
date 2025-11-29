package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;
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
  public void setUp() {
    abstractOrderRepositoryFixture = new AbstractOrderRepositoryFixture();
    clearOrderRepository();
  }

  @Test
  void givenValidOrder_whenSaveOrder_thenNoExceptions() {
    Order order = abstractOrderRepositoryFixture.build();
    assertDoesNotThrow(() -> getOrderRepository().save(order));
  }

  @Test
  void givenExistingOrder_whenGetOrderById_thenReturnExpectedOrder() {
    Order expectedOrder = abstractOrderRepositoryFixture.build();
    getOrderRepository().save(expectedOrder);

    Order result = getOrderRepository().getOrderById(expectedOrder.getId());

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(expectedOrder.getId());
    assertThat(result.getDate()).isEqualTo(expectedOrder.getDate());
  }

  @Test
  void givenNonExistingOrder_whenGetOrderById_thenReturnNull() {
    assertThat(getOrderRepository().getOrderById("randomId")).isNull();
  }
}
