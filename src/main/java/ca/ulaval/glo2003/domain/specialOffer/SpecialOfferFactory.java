package ca.ulaval.glo2003.domain.specialOffer;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;

public class SpecialOfferFactory {

  public SpecialOfferFactory() {}

  public SpecialOffer createSpecialOffer(
      String restaurantId,
      String title,
      String description,
      LocalDate validFrom,
      LocalDate validUntil) {

    if (validUntil.isBefore(validFrom)) {
      throw new InvalidParameterException(
          "The end date of the offer cannot be before the start date");
    }

    return new SpecialOffer(restaurantId, title, description, validFrom, validUntil);
  }
}
