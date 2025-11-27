package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.OrderDocument;
import java.time.LocalDate;

public class OrderDocumentMapperFixture {

  private String ID = "123";
  private String RESTAURANT_ID = "123";
  private LocalDate DATE = LocalDate.now();
  private String MENU = "bouffe";
  private String CUSTOMER_NAME = "John Deer";
  private String CUSTOMER_EMAIL = "john.deer@gmail.com";
  private String CUSTOMER_PHONE_NUMBER = "1234567890";

  public Order build() {
    return new Order(
        RESTAURANT_ID, DATE, MENU, CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
  }

  public OrderDocument buildDocument() {
    CustomerDocument customerDocument =
        new CustomerDocument(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
    return new OrderDocument(ID, RESTAURANT_ID, DATE, MENU, customerDocument);
  }

  public OrderDocumentMapperFixture withId(String id) {
    ID = id;
    return this;
  }

  public OrderDocumentMapperFixture withRestaurantId(String restaurantId) {
    RESTAURANT_ID = restaurantId;
    return this;
  }

  public OrderDocumentMapperFixture withDate(LocalDate date) {
    DATE = date;
    return this;
  }

  public OrderDocumentMapperFixture withMenu(String menu) {
    MENU = menu;
    return this;
  }

  public OrderDocumentMapperFixture withCustomerName(String customerName) {
    CUSTOMER_NAME = customerName;
    return this;
  }

  public OrderDocumentMapperFixture withCustomerEmail(String customerEmail) {
    CUSTOMER_EMAIL = customerEmail;
    return this;
  }

  public OrderDocumentMapperFixture withCustomerPhone(String customerPhone) {
    CUSTOMER_PHONE_NUMBER = customerPhone;
    return this;
  }
}
