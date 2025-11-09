package ca.ulaval.glo2003.infra.persistence.mongo.documents;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity("reservations")
public class ReservationDocument {
  @Id private String id;
  private String date;
  private String startTime;
  private String endTime;
  private Integer groupSize;
  private CustomerDocument customer;
  private String restaurantId;

  public ReservationDocument() {}

  public ReservationDocument(
      String id,
      LocalDate date,
      LocalTime startTime,
      LocalTime endTime,
      Integer groupSize,
      CustomerDocument customer,
      String restaurantId) {
    this.id = id;
    this.date = date != null ? date.toString() : null;
    this.startTime = startTime != null ? startTime.toString() : null;
    this.endTime = endTime != null ? endTime.toString() : null;
    this.groupSize = groupSize;
    this.customer = customer;
    this.restaurantId = restaurantId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Integer getGroupSize() {
    return groupSize;
  }

  public void setGroupSize(Integer groupSize) {
    this.groupSize = groupSize;
  }

  public CustomerDocument getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDocument customer) {
    this.customer = customer;
  }

  public String getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
  }
}
