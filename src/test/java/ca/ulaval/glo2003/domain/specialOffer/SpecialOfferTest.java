package ca.ulaval.glo2003.domain.specialOffer;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialOfferTest {

  private SpecialOfferFixture specialOfferFixture;

  private static final String RESTAURANT_ID = "restaurant-1";
  private static final String TITLE = "Pizza Special";
  private static final String DESCRIPTION = "50% off pizza";
  private static final LocalDate FIXED_DATE = LocalDate.of(2025, 1, 1);
  private static final LocalDate VALID_FROM = FIXED_DATE;
  private static final LocalDate VALID_UNTIL = VALID_FROM.plusDays(7);

  private static final String SECOND_OFFER_TITLE = "Burger Special";
  private static final String SECOND_OFFER_DESCRIPTION = "30% off burger";

  @BeforeEach
  void setUp() {
    specialOfferFixture =
        new SpecialOfferFixture().withValidFrom(VALID_FROM).withValidUntil(VALID_UNTIL);
  }

  @Test
  void givenTwoOffers_whenOverlapping_thenReturnTrue() {
    LocalDate today = FIXED_DATE;
    LocalDate nextWeek = today.plusDays(7);

    SpecialOffer offer1 = specialOfferFixture.withValidFrom(today).withValidUntil(nextWeek).build();

    SpecialOffer offer2 =
        specialOfferFixture
            .withRestaurantId(RESTAURANT_ID)
            .withTitle(SECOND_OFFER_TITLE)
            .withDescription(SECOND_OFFER_DESCRIPTION)
            .withValidFrom(nextWeek.minusDays(3))
            .withValidUntil(nextWeek.plusDays(3))
            .build();

    assertThat(offer1.overlapsWith(offer2)).isTrue();
  }

  @Test
  void givenTwoNonOverlappingOffers_whenCheckedForOverlap_thenReturnFalse() {
    LocalDate today = FIXED_DATE;
    LocalDate nextWeek = today.plusDays(7);

    SpecialOffer offer1 = specialOfferFixture.withValidFrom(today).withValidUntil(nextWeek).build();

    SpecialOffer offer2 =
        specialOfferFixture
            .withValidFrom(nextWeek.plusDays(1))
            .withValidUntil(nextWeek.plusDays(7))
            .build();

    assertThat(offer1.overlapsWith(offer2)).isFalse();
  }
}
