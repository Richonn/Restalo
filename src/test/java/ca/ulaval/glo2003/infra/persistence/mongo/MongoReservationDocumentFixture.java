package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;

public class ReservationDocumentFixture {
  private String id = "reservation123";
  private String date = "2025-12-25";
  private String startTime = "18:30";
  private String endTime = "20:30";
  private Integer groupSize = 4;
  private CustomerDocument customer =
      new CustomerDocument("John Doe", "john@example.com", "1234567890");
  private String restaurantId = "restaurant123";

  public ReservationDocument build() {
    ReservationDocument document = new ReservationDocument();
    document.setId(id);
    document.setDate(date);
    document.setStartTime(startTime);
    document.setEndTime(endTime);
    document.setGroupSize(groupSize);
    document.setCustomer(customer);
    document.setRestaurantId(restaurantId);
    return document;
  }

  public ReservationDocumentFixture withId(String id) {
    this.id = id;
    return this;
  }

  public ReservationDocumentFixture withDate(String date) {
    this.date = date;
    return this;
  }

  public ReservationDocumentFixture withStartTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  public ReservationDocumentFixture withEndTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  public ReservationDocumentFixture withGroupSize(Integer groupSize) {
    this.groupSize = groupSize;
    return this;
  }

  public ReservationDocumentFixture withCustomer(CustomerDocument customer) {
    this.customer = customer;
    return this;
  }

  public ReservationDocumentFixture withRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }
}
