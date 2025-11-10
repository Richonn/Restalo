package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;

public class RestaurantDocumentFixture {
  private String id = "restaurant123";
  private String name = "Le Bistro";
  private Integer capacity = 50;
  private String openingHour = "10:00";
  private String closingHour = "22:00";
  private Integer reservationDuration = 60;
  private String ownerId = "owner123";

  public RestaurantDocument build() {
    RestaurantDocument document = new RestaurantDocument();
    document.setId(id);
    document.setName(name);
    document.setCapacity(capacity);
    document.setOpeningHour(openingHour);
    document.setClosingHour(closingHour);
    document.setReservationDuration(reservationDuration);
    document.setOwnerId(ownerId);
    return document;
  }

  public RestaurantDocumentFixture withId(String id) {
    this.id = id;
    return this;
  }

  public RestaurantDocumentFixture withName(String name) {
    this.name = name;
    return this;
  }

  public RestaurantDocumentFixture withCapacity(Integer capacity) {
    this.capacity = capacity;
    return this;
  }

  public RestaurantDocumentFixture withOpeningHour(String openingHour) {
    this.openingHour = openingHour;
    return this;
  }

  public RestaurantDocumentFixture withClosingHour(String closingHour) {
    this.closingHour = closingHour;
    return this;
  }

  public RestaurantDocumentFixture withReservationDuration(Integer reservationDuration) {
    this.reservationDuration = reservationDuration;
    return this;
  }

  public RestaurantDocumentFixture withOwnerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }
}
