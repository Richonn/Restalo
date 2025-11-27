package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.util.List;

public class SpecialOfferDomainService {

  public void validateNoOverlappingOffers(
      SpecialOffer newOffer, List<SpecialOffer> existingOffers) {
    for (SpecialOffer offer : existingOffers) {
      if (newOffer.overlapsWith(offer)) {
        throw new InvalidParameterException("A special offer already exists for this period.");
      }
    }
  }
}
