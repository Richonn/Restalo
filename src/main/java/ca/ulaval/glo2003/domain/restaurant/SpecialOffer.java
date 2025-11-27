package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalDate;
import java.util.UUID;

public class SpecialOffer {

  private final String id;
  private final String restaurantId;
  private final String title;
  private final String description;
  private final LocalDate validFrom;
  private final LocalDate validUntil;

  public SpecialOffer(
      String restaurantId,
      String title,
      String description,
      LocalDate validFrom,
      LocalDate validUntil) {
    this.id = UUID.randomUUID().toString();
    this.restaurantId = restaurantId;
    this.title = title;
    this.description = description;
    this.validFrom = validFrom;
    this.validUntil = validUntil;
  }

  public String getId() {
    return id;
  }

  public String getRestaurantId() {
    return restaurantId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getValidFrom() {
    return validFrom;
  }

  public LocalDate getValidUntil() {
    return validUntil;
  }

  public boolean overlapsWith(SpecialOffer other) {
    return !this.validFrom.isAfter(other.getValidUntil())
        && !this.validUntil.isBefore(other.getValidFrom());
  }
}
