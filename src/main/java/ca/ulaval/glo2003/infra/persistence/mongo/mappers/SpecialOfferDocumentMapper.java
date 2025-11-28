package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.SpecialOfferDocument;
import java.time.LocalDate;

public class SpecialOfferDocumentMapper {

  public static SpecialOffer toDomain(SpecialOfferDocument document) {
    if (document == null) return null;

    LocalDate validFrom =
        document.getValidFrom() == null ? null : LocalDate.parse(document.getValidFrom());
    LocalDate validUntil =
        document.getValidUntil() == null ? null : LocalDate.parse(document.getValidUntil());

    return new SpecialOffer(
        document.getRestaurantId(),
        document.getTitle(),
        document.getDescription(),
        validFrom,
        validUntil);
  }

  public static SpecialOfferDocument toDocument(SpecialOffer specialOffer) {
    if (specialOffer == null) return null;

    String validFrom =
        specialOffer.getValidFrom() == null ? null : specialOffer.getValidFrom().toString();
    String validUntil =
        specialOffer.getValidUntil() == null ? null : specialOffer.getValidUntil().toString();

    return new SpecialOfferDocument(
        specialOffer.getId(),
        specialOffer.getRestaurantId(),
        specialOffer.getTitle(),
        specialOffer.getDescription(),
        validFrom,
        validUntil);
  }
}
