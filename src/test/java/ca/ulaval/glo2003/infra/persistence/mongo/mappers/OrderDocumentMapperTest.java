package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.OrderDocument;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderDocumentMapperTest {

  private OrderDocumentMapperFixture orderDocumentMapperFixture;

  @BeforeEach
  void setUp() {
    orderDocumentMapperFixture = new OrderDocumentMapperFixture();
  }

  @Test
  void givenValidOrder_whenToDocument_thenReturnExpectedDocument() {
    String restaurantId = "restaurantId";
    LocalDate date = LocalDate.now();
    String menu = "bouffe";

    Order order =
        orderDocumentMapperFixture
            .withRestaurantId(restaurantId)
            .withDate(date)
            .withMenu(menu)
            .build();
    OrderDocument orderDocument = OrderDocumentMapper.toDocument(order);

    assertThat(orderDocument).isNotNull();
    assertThat(orderDocument.getRestaurantId()).isEqualTo(restaurantId);
    assertThat(orderDocument.getDate()).isEqualTo(date.toString());
    assertThat(orderDocument.getMenu()).isEqualTo(menu);
  }

  @Test
  void givenNullOrder_whenToDocument_thenReturnNull() {
    assertThat(OrderDocumentMapper.toDocument(null)).isNull();
  }

  @Test
  void givenValidOrderDocument_whenToDomain_thenReturnExpectedOrder() {
    String orderId = "orderId";
    String restaurantId = "restaurantId";
    LocalDate date = LocalDate.now();
    String menu = "bouffe";

    OrderDocument orderDocument =
        orderDocumentMapperFixture
            .withId(orderId)
            .withRestaurantId(restaurantId)
            .withDate(date)
            .withMenu(menu)
            .buildDocument();
    Order order = OrderDocumentMapper.toDomain(orderDocument);

    assertThat(order).isNotNull();
    assertThat(order.getId()).isEqualTo(orderId);
    assertThat(order.getRestaurantId()).isEqualTo(restaurantId);
    assertThat(order.getDate()).isEqualTo(date);
    assertThat(order.getMenu()).isEqualTo(menu);
  }

  @Test
  void givenNullOrderDocument_whenToDomain_thenReturnNull() {
    assertThat(OrderDocumentMapper.toDomain(null)).isNull();
  }
}
