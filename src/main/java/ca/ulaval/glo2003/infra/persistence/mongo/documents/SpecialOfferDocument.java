package ca.ulaval.glo2003.infra.persistence.mongo.documents;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.time.LocalDate;

@Entity("special_offers")
public class SpecialOfferDocument {

  @Id private String id;
  private String restaurantId;
  private String title;
  private String description;
  private LocalDate validFrom;
  private LocalDate validUntil;

  public SpecialOfferDocument() {}

  public SpecialOfferDocument(
      String id,
      String restaurantId,
      String title,
      String description,
      LocalDate validFrom,
      LocalDate validUntil) {
    this.id = id;
    this.restaurantId = restaurantId;
    this.title = title;
    this.description = description;
    this.validFrom = validFrom;
    this.validUntil = validUntil;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDate validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDate getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(LocalDate validUntil) {
    this.validUntil = validUntil;
  }
}
