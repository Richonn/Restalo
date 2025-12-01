package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemorySpecialOfferRepositoryTest extends AbstractSpecialOfferRepositoryTest {

  private static final String RESTAURANT_ID = "restaurant123";
  private static final String WINTER_SPECIAL_TITLE = "Winter Special";
  private static final String WINTER_SPECIAL_DESCRIPTION = "Special winter menu";
  private static final LocalDate WINTER_SPECIAL_START_DATE = LocalDate.of(2024, 12, 1);
  private static final LocalDate WINTER_SPECIAL_END_DATE = LocalDate.of(2024, 12, 31);

  private static final String SUMMER_SPECIAL_TITLE = "Summer Special";
  private static final String SUMMER_SPECIAL_DESCRIPTION = "Special summer menu";
  private static final LocalDate SUMMER_SPECIAL_START_DATE = LocalDate.of(2024, 6, 1);
  private static final LocalDate SUMMER_SPECIAL_END_DATE = LocalDate.of(2024, 6, 30);

  private static final String NON_EXISTENT_RESTAURANT_ID = "nonexistentRestaurant";

  private SpecialOfferRepository specialOfferRepository;
  private InMemorySpecialOfferRepositoryFixture fixture;

  @BeforeEach
  public void setUp() {
    super.setUp();
    specialOfferRepository = new InMemorySpecialOfferRepository();
    fixture = new InMemorySpecialOfferRepositoryFixture();
  }

  @Override
  protected SpecialOfferRepository getSpecialOfferRepository() {
    return specialOfferRepository;
  }

  @Override
  protected void clearSpecialOfferRepository() {
    specialOfferRepository = new InMemorySpecialOfferRepository();
  }

  @Test
  void givenSpecialOffer_whenSave_thenItIsSavedInMemory() {
    SpecialOffer specialOffer =
        fixture
            .withRestaurantId(RESTAURANT_ID)
            .withTitle(WINTER_SPECIAL_TITLE)
            .withDescription(WINTER_SPECIAL_DESCRIPTION)
            .withValidFrom(WINTER_SPECIAL_START_DATE)
            .withValidUntil(WINTER_SPECIAL_END_DATE)
            .build();

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> savedOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID);
    assertThat(savedOffers).hasSize(1);
    SpecialOffer retrievedSpecialOffer = savedOffers.get(0);
    assertThat(retrievedSpecialOffer.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(retrievedSpecialOffer.getTitle()).isEqualTo(WINTER_SPECIAL_TITLE);
  }

  @Test
  void givenMultipleSpecialOffers_whenSave_thenFindAllByRestaurantId() {
    SpecialOffer offer1 =
        fixture
            .withRestaurantId(RESTAURANT_ID)
            .withTitle(WINTER_SPECIAL_TITLE)
            .withDescription(WINTER_SPECIAL_DESCRIPTION)
            .withValidFrom(WINTER_SPECIAL_START_DATE)
            .withValidUntil(WINTER_SPECIAL_END_DATE)
            .build();

    SpecialOffer offer2 =
        fixture
            .withRestaurantId(RESTAURANT_ID)
            .withTitle(SUMMER_SPECIAL_TITLE)
            .withDescription(SUMMER_SPECIAL_DESCRIPTION)
            .withValidFrom(SUMMER_SPECIAL_START_DATE)
            .withValidUntil(SUMMER_SPECIAL_END_DATE)
            .build();

    specialOfferRepository.save(offer1);
    specialOfferRepository.save(offer2);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID);
    assertThat(specialOffers).hasSize(2);
    List<String> titles = specialOffers.stream().map(SpecialOffer::getTitle).toList();

    assertThat(titles).containsExactly(WINTER_SPECIAL_TITLE, SUMMER_SPECIAL_TITLE);
  }

  @Test
  void givenNoSpecialOffers_whenGetSpecialOffers_thenReturnEmptyList() {
    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(NON_EXISTENT_RESTAURANT_ID);
    assertThat(specialOffers).isEmpty();
  }

  @Test
  void givenSpecialOfferWithNullFields_whenSave_thenHandleNullValuesCorrectly() {
    SpecialOffer specialOffer =
        fixture
            .withRestaurantId(RESTAURANT_ID)
            .withTitle(null)
            .withDescription(null)
            .withValidFrom(null)
            .withValidUntil(null)
            .build();

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID);
    assertThat(specialOffers).hasSize(1);
    SpecialOffer retrievedOffer = specialOffers.get(0);
    assertThat(retrievedOffer.getTitle()).isNull();
    assertThat(retrievedOffer.getDescription()).isNull();
    assertThat(retrievedOffer.getValidFrom()).isNull();
    assertThat(retrievedOffer.getValidUntil()).isNull();
  }
}
