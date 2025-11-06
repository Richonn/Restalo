package ca.ulaval.glo2003.infra.persistence.mongo.documents;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.time.LocalTime;

@Entity("restaurants")
public class RestaurantDocument {
  @Id private String id;
  private String name;
  private Integer capacity;
  private String openingHour;
  private String closingHour;
  private Integer reservationDuration;
  private String ownerId;

  public RestaurantDocument() {}

  public RestaurantDocument(
      String id,
      String name,
      Integer capacity,
      LocalTime openingHour,
      LocalTime closingHour,
      Integer reservationDuration,
      String ownerId) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
    this.openingHour = openingHour != null ? openingHour.toString() : null;
    this.closingHour = closingHour != null ? closingHour.toString() : null;
    this.reservationDuration = reservationDuration;
    this.ownerId = ownerId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public String getOpeningHour() {
    return openingHour;
  }

  public void setOpeningHour(String openingHour) {
    this.openingHour = openingHour;
  }

  public String getClosingHour() {
    return closingHour;
  }

  public void setClosingHour(String closingHour) {
    this.closingHour = closingHour;
  }

  public Integer getReservationDuration() {
    return reservationDuration;
  }

  public void setReservationDuration(Integer reservationDuration) {
    this.reservationDuration = reservationDuration;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }
}
