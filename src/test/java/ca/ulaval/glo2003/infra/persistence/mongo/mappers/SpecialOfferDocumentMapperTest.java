package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.SpecialOfferDocument;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

public class SpecialOfferDocumentMapperTest {

  private static final String RESTAURANT_ID = "restaurant123";
  private static final String OFFER_ID = "offer123";
  private static final String TITLE = "Winter Special";
  private static final String DESCRIPTION = "Special winter menu";
  private static final LocalDate VALID_FROM = LocalDate.of(2024, 12, 1);
  private static final LocalDate VALID_UNTIL = LocalDate.of(2024, 12, 31);
  private static final String INVALID_DATE_FORMAT = "invalid-date";

  @Test
  void givenNullSpecialOffer_whenToDocument_thenReturnNull() {
    SpecialOfferDocument result = SpecialOfferDocumentMapper.toDocument(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidSpecialOffer_whenToDocument_thenReturnExpectedDocument() {
    SpecialOffer specialOffer =
        new SpecialOffer(RESTAURANT_ID, TITLE, DESCRIPTION, VALID_FROM, VALID_UNTIL);

    SpecialOfferDocument document = SpecialOfferDocumentMapper.toDocument(specialOffer);

    assertThat(document).isNotNull();
    assertThat(document.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(document.getTitle()).isEqualTo(TITLE);
    assertThat(document.getDescription()).isEqualTo(DESCRIPTION);
    assertThat(document.getValidFrom()).isEqualTo(VALID_FROM.toString());
    assertThat(document.getValidUntil()).isEqualTo(VALID_UNTIL.toString());
  }

  @Test
  void givenSpecialOfferWithNullFields_whenToDocument_thenReturnExpectedDocument() {
    SpecialOffer specialOffer = new SpecialOffer(RESTAURANT_ID, TITLE, null, VALID_FROM, null);

    SpecialOfferDocument document = SpecialOfferDocumentMapper.toDocument(specialOffer);

    assertThat(document).isNotNull();
    assertThat(document.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(document.getTitle()).isEqualTo(TITLE);
    assertThat(document.getDescription()).isNull();
    assertThat(document.getValidFrom()).isEqualTo(VALID_FROM.toString());
    assertThat(document.getValidUntil()).isNull();
  }

  @Test
  void givenNullDocument_whenToDomain_thenReturnNull() {
    SpecialOffer result = SpecialOfferDocumentMapper.toDomain(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidDocument_whenToDomain_thenReturnExpectedSpecialOffer() {
    SpecialOfferDocument document =
        new SpecialOfferDocument(
            OFFER_ID,
            RESTAURANT_ID,
            TITLE,
            DESCRIPTION,
            VALID_FROM.toString(),
            VALID_UNTIL.toString());

    SpecialOffer specialOffer = SpecialOfferDocumentMapper.toDomain(document);

    assertThat(specialOffer).isNotNull();
    assertThat(specialOffer.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(specialOffer.getTitle()).isEqualTo(TITLE);
    assertThat(specialOffer.getDescription()).isEqualTo(DESCRIPTION);
    assertThat(specialOffer.getValidFrom()).isEqualTo(VALID_FROM);
    assertThat(specialOffer.getValidUntil()).isEqualTo(VALID_UNTIL);
  }

  @Test
  void givenDocumentWithInvalidDateFormat_whenToDomain_thenThrowsDateTimeParseException() {
    SpecialOfferDocument document =
        new SpecialOfferDocument(
            OFFER_ID, RESTAURANT_ID, TITLE, DESCRIPTION, INVALID_DATE_FORMAT, "2024-12-31");

    assertThrows(DateTimeParseException.class, () -> SpecialOfferDocumentMapper.toDomain(document));
  }

  @Test
  void givenDocumentWithMissingValidUntil_whenToDomain_thenHandleGracefully() {
    SpecialOfferDocument document =
        new SpecialOfferDocument(
            OFFER_ID, RESTAURANT_ID, TITLE, DESCRIPTION, VALID_FROM.toString(), null);

    SpecialOffer specialOffer = SpecialOfferDocumentMapper.toDomain(document);

    assertThat(specialOffer).isNotNull();
    assertThat(specialOffer.getValidUntil()).isNull();
  }

  @Test
  void givenSpecialOffer_whenToDocumentAndBackToDomain_thenPreservesData() {
    SpecialOffer originalSpecialOffer =
        new SpecialOffer(RESTAURANT_ID, TITLE, DESCRIPTION, VALID_FROM, VALID_UNTIL);

    SpecialOfferDocument document = SpecialOfferDocumentMapper.toDocument(originalSpecialOffer);
    SpecialOffer restoredSpecialOffer = SpecialOfferDocumentMapper.toDomain(document);

    assertThat(restoredSpecialOffer).isNotNull();
    assertThat(restoredSpecialOffer.getRestaurantId())
        .isEqualTo(originalSpecialOffer.getRestaurantId());
    assertThat(restoredSpecialOffer.getTitle()).isEqualTo(originalSpecialOffer.getTitle());
    assertThat(restoredSpecialOffer.getDescription())
        .isEqualTo(originalSpecialOffer.getDescription());
    assertThat(restoredSpecialOffer.getValidFrom()).isEqualTo(originalSpecialOffer.getValidFrom());
    assertThat(restoredSpecialOffer.getValidUntil())
        .isEqualTo(originalSpecialOffer.getValidUntil());
  }
}
