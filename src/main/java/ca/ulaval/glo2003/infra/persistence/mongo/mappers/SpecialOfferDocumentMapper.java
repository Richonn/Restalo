package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import ca.ulaval.glo2003.domain.restaurant.SpecialOffer;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.SpecialOfferDocument;

public class SpecialOfferDocumentMapper {

  public static SpecialOffer toDomain(SpecialOfferDocument document) {
    if (document == null) {
      return null;
    }
    return new SpecialOffer(
        document.getRestaurantId(),
        document.getTitle(),
        document.getDescription(),
        document.getValidFrom(),
        document.getValidUntil());
  }

  public static SpecialOfferDocument toDocument(SpecialOffer specialOffer) {
    if (specialOffer == null) {
      return null;
    }
    return new SpecialOfferDocument(
        specialOffer.getId(),
        specialOffer.getRestaurantId(),
        specialOffer.getTitle(),
        specialOffer.getDescription(),
        specialOffer.getValidFrom(),
        specialOffer.getValidUntil());
  }
}
