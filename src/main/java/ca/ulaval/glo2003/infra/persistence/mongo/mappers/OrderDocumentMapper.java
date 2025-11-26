package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.domain.reservation.Customer;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.OrderDocument;
import java.time.LocalDate;

public class OrderDocumentMapper {

  public static OrderDocument toDocument(Order order) {
    if (order == null) {
      return null;
    }

    CustomerDocument customerDocument =
        new CustomerDocument(
            order.getCustomerName(), order.getCustomerEmail(), order.getCustomerPhoneNumber());

    return new OrderDocument(
        order.getId(), order.getRestaurantId(), order.getDate(), order.getMenu(), customerDocument);
  }

  public static Order toDomain(OrderDocument orderDocument) {
    if (orderDocument == null) {
      return null;
    }

    CustomerDocument customerDocument = orderDocument.getCustomer();
    Customer customer =
        new Customer(
            customerDocument.getName(),
            customerDocument.getEmail(),
            customerDocument.getPhoneNumber());

    return new Order(
        orderDocument.getId(),
        orderDocument.getRestaurantId(),
        LocalDate.parse(orderDocument.getDate()),
        orderDocument.getMenu(),
        customer);
  }
}
