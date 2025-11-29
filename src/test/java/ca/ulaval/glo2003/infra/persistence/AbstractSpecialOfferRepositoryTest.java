package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractSpecialOfferRepositoryTest {

  private InMemorySpecialOfferRepositoryFixture fixture;

  protected abstract SpecialOfferRepository getSpecialOfferRepository();

  protected abstract void clearSpecialOfferRepository();

  @BeforeEach
  public void setUp() {
    fixture = new InMemorySpecialOfferRepositoryFixture();
    clearSpecialOfferRepository();
  }

  @Test
  void givenValidSpecialOffer_whenSaveSpecialOffer_thenNoExceptions() {
    SpecialOffer specialOffer = fixture.build();
    assertDoesNotThrow(() -> getSpecialOfferRepository().save(specialOffer));
  }

  @Test
  void givenValidSpecialOffer_whenSaved_thenItCanBeRetrieved() {
    SpecialOffer specialOffer = fixture.build();
    getSpecialOfferRepository().save(specialOffer);

    SpecialOffer retrievedOffer =
        getSpecialOfferRepository()
            .getSpecialOffersByRestaurantId(specialOffer.getRestaurantId())
            .get(0);
    assertThat(retrievedOffer.getRestaurantId()).isEqualTo(specialOffer.getRestaurantId());
    assertThat(retrievedOffer.getId()).isEqualTo(specialOffer.getId());
  }
}
