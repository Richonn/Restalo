package ca.ulaval.glo2003.infra.persistence.mongo.documents;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.time.LocalDate;

@Entity("order")
public class OrderDocument {
  @Id private String id;
  private String restaurantId;
  private String date;
  private String menu;
  private CustomerDocument customer;

  public OrderDocument() {}

  public OrderDocument(
      String id, String restaurantId, LocalDate date, String menu, CustomerDocument customer) {
    this.id = id;
    this.restaurantId = restaurantId;
    this.date = date.toString();
    this.menu = menu;
    this.customer = customer;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getMenu() {
    return menu;
  }

  public void setMenu(String menu) {
    this.menu = menu;
  }

  public CustomerDocument getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDocument customer) {
    this.customer = customer;
  }
}
