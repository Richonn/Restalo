package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemorySpecialOfferRepositoryTest extends AbstractSpecialOfferRepositoryTest {

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
            .withRestaurantId("restaurant123")
            .withTitle("Winter Special")
            .withDescription("Special winter menu")
            .withValidFrom(LocalDate.of(2024, 12, 1))
            .withValidUntil(LocalDate.of(2024, 12, 31))
            .build();

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> savedOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("restaurant123");
    assertThat(savedOffers).hasSize(1);
    SpecialOffer retrievedSpecialOffer = savedOffers.get(0);
    assertThat(retrievedSpecialOffer.getRestaurantId()).isEqualTo("restaurant123");
    assertThat(retrievedSpecialOffer.getTitle()).isEqualTo("Winter Special");
  }

  @Test
  void givenMultipleSpecialOffers_whenSave_thenFindAllByRestaurantId() {
    SpecialOffer offer1 =
        fixture
            .withRestaurantId("restaurant123")
            .withTitle("Winter Special")
            .withDescription("Special winter menu")
            .withValidFrom(LocalDate.of(2024, 12, 1))
            .withValidUntil(LocalDate.of(2024, 12, 31))
            .build();

    SpecialOffer offer2 =
        fixture
            .withRestaurantId("restaurant123")
            .withTitle("Summer Special")
            .withDescription("Special summer menu")
            .withValidFrom(LocalDate.of(2024, 6, 1))
            .withValidUntil(LocalDate.of(2024, 6, 30))
            .build();

    specialOfferRepository.save(offer1);
    specialOfferRepository.save(offer2);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("restaurant123");
    assertThat(specialOffers).hasSize(2);
    List<String> titles = specialOffers.stream().map(SpecialOffer::getTitle).toList();

    assertThat(titles).containsExactly("Winter Special", "Summer Special");
  }

  @Test
  void givenNoSpecialOffers_whenGetSpecialOffers_thenReturnEmptyList() {
    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("nonexistentRestaurant");
    assertThat(specialOffers).isEmpty();
  }

  @Test
  void givenSpecialOfferWithNullFields_whenSave_thenHandleNullValuesCorrectly() {
    SpecialOffer specialOffer =
        fixture
            .withRestaurantId("restaurant123")
            .withTitle(null)
            .withDescription(null)
            .withValidFrom(null)
            .withValidUntil(null)
            .build();

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("restaurant123");
    assertThat(specialOffers).hasSize(1);
    SpecialOffer retrievedOffer = specialOffers.get(0);
    assertThat(retrievedOffer.getTitle()).isNull();
    assertThat(retrievedOffer.getDescription()).isNull();
    assertThat(retrievedOffer.getValidFrom()).isNull();
    assertThat(retrievedOffer.getValidUntil()).isNull();
  }
}
