package ca.ulaval.glo2003.domain.order;

import ca.ulaval.glo2003.domain.reservation.Customer;
import java.time.LocalDate;
import java.util.UUID;

public class Order {

  private final String id;
  private final String restaurantId;
  private final LocalDate date;
  private final String menu;
  private final Customer customer;

  public Order(
      String restaurantId, LocalDate date, String menu, String name, String email, String phone) {
    this.id = UUID.randomUUID().toString();
    this.restaurantId = restaurantId;
    this.date = date;
    this.menu = menu;
    this.customer = new Customer(name, email, phone);
  }

  public Order(String restaurantId, LocalDate date, String menu, Customer customer) {
    this.id = UUID.randomUUID().toString();
    this.restaurantId = restaurantId;
    this.date = date;
    this.menu = menu;
    this.customer = customer;
  }

  public Order(String id, String restaurantId, LocalDate date, String menu, Customer customer) {
    this.id = id;
    this.restaurantId = restaurantId;
    this.date = date;
    this.menu = menu;
    this.customer = customer;
  }

  public String getId() {
    return id;
  }

  public String getRestaurantId() {
    return restaurantId;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getMenu() {
    return menu;
  }

  public String getCustomerName() {
    return customer.getName();
  }

  public String getCustomerEmail() {
    return customer.getEmail();
  }

  public String getCustomerPhoneNumber() {
    return customer.getPhoneNumber();
  }

  public Customer getCustomer() {
    return customer;
  }
}
