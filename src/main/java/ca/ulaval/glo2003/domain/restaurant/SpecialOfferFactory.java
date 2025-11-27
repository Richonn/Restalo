package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalDate;

public class SpecialOfferFactory {

  public SpecialOfferFactory() {}

  public SpecialOffer createSpecialOffer(
      String restaurantId,
      String title,
      String description,
      LocalDate validFrom,
      LocalDate validUntil) {
    return new SpecialOffer(restaurantId, title, description, validFrom, validUntil);
  }
}
