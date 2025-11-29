package ca.ulaval.glo2003.domain.specialOffer;

import java.time.LocalDate;

public class SpecialOfferFixture {

  private String restaurantId = "restaurant-1";
  private String title = "Pizza Special";
  private String description = "50% off pizza";
  private LocalDate validFrom = LocalDate.now();
  private LocalDate validUntil = validFrom.plusDays(7);

  public SpecialOffer build() {
    return new SpecialOffer(restaurantId, title, description, validFrom, validUntil);
  }

  public SpecialOfferFixture withRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }

  public SpecialOfferFixture withTitle(String title) {
    this.title = title;
    return this;
  }

  public SpecialOfferFixture withDescription(String description) {
    this.description = description;
    return this;
  }

  public SpecialOfferFixture withValidFrom(LocalDate validFrom) {
    this.validFrom = validFrom;
    return this;
  }

  public SpecialOfferFixture withValidUntil(LocalDate validUntil) {
    this.validUntil = validUntil;
    return this;
  }
}
