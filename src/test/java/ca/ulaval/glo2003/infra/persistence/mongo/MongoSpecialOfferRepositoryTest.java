package ca.ulaval.glo2003.infra.persistence.mongo;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class MongoSpecialOfferRepositoryTest {

  private static final String MONGO_DOCKER_IMAGE = "mongo:7.0";
  private static final String MONGO_CLUSTER_URL_PROPERTY = "MONGO_CLUSTER_URL";
  private static final String MONGO_DATABASE_PROPERTY = "MONGO_DATABASE";
  private static final String TEST_DATABASE_NAME = "test_special_offer_db";

  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse(MONGO_DOCKER_IMAGE));

  private static SpecialOfferRepository specialOfferRepository;

  @BeforeAll
  static void initializeMongoConnection() {
    mongoDBContainer.start();

    System.setProperty(MONGO_CLUSTER_URL_PROPERTY, mongoDBContainer.getReplicaSetUrl());
    System.setProperty(MONGO_DATABASE_PROPERTY, TEST_DATABASE_NAME);

    specialOfferRepository = new MongoSpecialOfferRepository(MongoConnection.getInstance());
  }

  @AfterAll
  static void closeMongoConnection() {
    MongoConnection.getInstance().close();
    mongoDBContainer.stop();
  }

  @BeforeEach
  public void clearRepository() {
    MongoConnection.getInstance().getDatastore().getDatabase().drop();
  }

  @Test
  void givenSpecialOffer_whenSave_thenItIsSavedInMongoDB() {
    SpecialOffer specialOffer =
        new SpecialOffer(
            "restaurant123",
            "Winter Special",
            "Special winter menu",
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 31));

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> savedSpecialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("restaurant123");

    assertThat(savedSpecialOffers).hasSize(1);
    SpecialOffer retrievedSpecialOffer = savedSpecialOffers.get(0);
    assertThat(retrievedSpecialOffer.getRestaurantId()).isEqualTo("restaurant123");
    assertThat(retrievedSpecialOffer.getTitle()).isEqualTo("Winter Special");
    assertThat(retrievedSpecialOffer.getDescription()).isEqualTo("Special winter menu");
    assertThat(retrievedSpecialOffer.getValidFrom()).isEqualTo(LocalDate.of(2024, 12, 1));
    assertThat(retrievedSpecialOffer.getValidUntil()).isEqualTo(LocalDate.of(2024, 12, 31));
  }

  @Test
  void givenMultipleSpecialOffers_whenSave_thenFindAllByRestaurantId() {
    SpecialOffer specialOffer1 =
        new SpecialOffer(
            "restaurant123",
            "Winter Special",
            "Special winter menu",
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 31));
    SpecialOffer specialOffer2 =
        new SpecialOffer(
            "restaurant123",
            "Summer Special",
            "Special summer menu",
            LocalDate.of(2024, 6, 1),
            LocalDate.of(2024, 6, 30));

    specialOfferRepository.save(specialOffer1);
    specialOfferRepository.save(specialOffer2);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("restaurant123");

    assertThat(specialOffers).hasSize(2);

    assertThat(specialOffers.get(0).getTitle()).isEqualTo("Winter Special");
    assertThat(specialOffers.get(1).getTitle()).isEqualTo("Summer Special");
  }

  @Test
  void givenNoSpecialOffers_whenGetSpecialOffers_thenReturnEmptyList() {
    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("nonexistentRestaurant");

    assertThat(specialOffers).isEmpty();
  }

  @Test
  void givenSpecialOfferWithNullFields_whenSave_thenHandleNullValuesCorrectly() {
    SpecialOffer specialOffer = new SpecialOffer("restaurant123", null, null, null, null);

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId("restaurant123");

    assertThat(specialOffers).hasSize(1);
    SpecialOffer retrievedSpecialOffer = specialOffers.get(0);
    assertThat(retrievedSpecialOffer.getTitle()).isNull();
    assertThat(retrievedSpecialOffer.getDescription()).isNull();
    assertThat(retrievedSpecialOffer.getValidFrom()).isNull();
    assertThat(retrievedSpecialOffer.getValidUntil()).isNull();
  }
}
