package ca.ulaval.glo2003.domain.specialOffer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialOfferFactoryTest {

  private SpecialOfferFactory specialOfferFactory;
  private SpecialOfferFixture specialOfferFixture;

  private static final String RESTAURANT_ID = "restaurant-1";
  private static final String TITLE = "Pizza Special";
  private static final String DESCRIPTION = "50% off pizza";

  @BeforeEach
  void setUp() {
    specialOfferFactory = new SpecialOfferFactory();
    specialOfferFixture = new SpecialOfferFixture();
  }

  @Test
  void givenValidParameters_whenCreateSpecialOffer_thenReturnsValidSpecialOffer() {

    LocalDate validFrom = LocalDate.now();
    LocalDate validUntil = validFrom.plusDays(7);

    SpecialOffer specialOffer =
        specialOfferFixture
            .withRestaurantId(RESTAURANT_ID)
            .withTitle(TITLE)
            .withDescription(DESCRIPTION)
            .withValidFrom(validFrom)
            .withValidUntil(validUntil)
            .build();

    assertThat(specialOffer).isNotNull();
    assertThat(specialOffer.getId()).isNotNull();
    assertThat(specialOffer.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(specialOffer.getTitle()).isEqualTo(TITLE);
    assertThat(specialOffer.getDescription()).isEqualTo(DESCRIPTION);
    assertThat(specialOffer.getValidFrom()).isEqualTo(validFrom);
    assertThat(specialOffer.getValidUntil()).isEqualTo(validUntil);
  }

  @Test
  void givenEndDateBeforeStartDate_whenCreateSpecialOffer_thenThrowsInvalidParameterException() {
    LocalDate validFrom = LocalDate.now();
    LocalDate validUntil = validFrom.minusDays(1);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () ->
                specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Pizza Special", "50% off pizza", validFrom, validUntil));

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The end date of the offer cannot be before the start date");
  }

  @Test
  void givenEqualStartAndEndDate_whenCreateSpecialOffer_thenReturnsValidSpecialOffer() {
    LocalDate validFrom = LocalDate.now();
    LocalDate validUntil = validFrom;

    SpecialOffer specialOffer =
        specialOfferFixture.withValidFrom(validFrom).withValidUntil(validUntil).build();

    assertThat(specialOffer).isNotNull();
    assertThat(specialOffer.getValidFrom()).isEqualTo(validFrom);
    assertThat(specialOffer.getValidUntil()).isEqualTo(validUntil);
  }
}
