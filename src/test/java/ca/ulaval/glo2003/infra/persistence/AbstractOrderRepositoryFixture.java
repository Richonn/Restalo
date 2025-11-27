package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.order.Order;
import java.time.LocalDate;

public class AbstractOrderRepositoryFixture {

  private String RESTAURANT_ID = "123";
  private LocalDate DATE = LocalDate.now();
  private String MENU = "bouffe";
  private String CUSTOMER_NAME = "John Deer";
  private String CUSTOMER_EMAIL = "john.deer@gmail.com";
  private String CUSTOMER_PHONE = "1234567890";

  public Order build() {
    return new Order(RESTAURANT_ID, DATE, MENU, CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE);
  }

  public AbstractOrderRepositoryFixture withRestaurantId(String restaurantId) {
    this.RESTAURANT_ID = restaurantId;
    return this;
  }

  public AbstractOrderRepositoryFixture withDate(LocalDate date) {
    this.DATE = date;
    return this;
  }

  public AbstractOrderRepositoryFixture withMenu(String menu) {
    this.MENU = menu;
    return this;
  }

  public AbstractOrderRepositoryFixture withCustomerName(String customerName) {
    this.CUSTOMER_NAME = customerName;
    return this;
  }

  public AbstractOrderRepositoryFixture withCustomerEmail(String customerEmail) {
    this.CUSTOMER_EMAIL = customerEmail;
    return this;
  }

  public AbstractOrderRepositoryFixture withCustomerPhone(String customerPhone) {
    this.CUSTOMER_PHONE = customerPhone;
    return this;
  }
}
