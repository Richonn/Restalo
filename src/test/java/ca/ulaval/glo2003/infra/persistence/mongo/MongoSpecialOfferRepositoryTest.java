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

  private static final String RESTAURANT_ID = "restaurant123";
  private static final String WINTER_SPECIAL_TITLE = "Winter Special";
  private static final String WINTER_SPECIAL_DESCRIPTION = "Special winter menu";
  private static final String SUMMER_SPECIAL_TITLE = "Summer Special";
  private static final String SUMMER_SPECIAL_DESCRIPTION = "Special summer menu";
  private static final LocalDate WINTER_SPECIAL_START_DATE = LocalDate.of(2024, 12, 1);
  private static final LocalDate WINTER_SPECIAL_END_DATE = LocalDate.of(2024, 12, 31);
  private static final LocalDate SUMMER_SPECIAL_START_DATE = LocalDate.of(2024, 6, 1);
  private static final LocalDate SUMMER_SPECIAL_END_DATE = LocalDate.of(2024, 6, 30);
  private static final String NON_EXISTENT_RESTAURANT_ID = "nonexistentRestaurant";

  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse(MONGO_DOCKER_IMAGE));

  private static MongoConnection mongoConnection;
  private static SpecialOfferRepository specialOfferRepository;

  @BeforeAll
  static void initializeMongoConnection() {
    mongoDBContainer.start();

    System.setProperty(MONGO_CLUSTER_URL_PROPERTY, mongoDBContainer.getReplicaSetUrl());
    System.setProperty(MONGO_DATABASE_PROPERTY, TEST_DATABASE_NAME);

    mongoConnection = new MongoConnection();
    specialOfferRepository = new MongoSpecialOfferRepository(mongoConnection);
  }

  @AfterAll
  static void closeMongoConnection() {
    if (mongoConnection != null) {
      mongoConnection.close();
    }
    mongoDBContainer.stop();
  }

  @BeforeEach
  public void clearRepository() {
    mongoConnection.getDatastore().getDatabase().drop();
  }

  @Test
  void givenSpecialOffer_whenSave_thenItIsSavedInMongoDB() {
    SpecialOffer specialOffer =
        new SpecialOffer(
            RESTAURANT_ID,
            WINTER_SPECIAL_TITLE,
            WINTER_SPECIAL_DESCRIPTION,
            WINTER_SPECIAL_START_DATE,
            WINTER_SPECIAL_END_DATE);

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> savedSpecialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID);

    assertThat(savedSpecialOffers).hasSize(1);
    SpecialOffer retrievedSpecialOffer = savedSpecialOffers.get(0);
    assertThat(retrievedSpecialOffer.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(retrievedSpecialOffer.getTitle()).isEqualTo(WINTER_SPECIAL_TITLE);
    assertThat(retrievedSpecialOffer.getDescription()).isEqualTo(WINTER_SPECIAL_DESCRIPTION);
    assertThat(retrievedSpecialOffer.getValidFrom()).isEqualTo(WINTER_SPECIAL_START_DATE);
    assertThat(retrievedSpecialOffer.getValidUntil()).isEqualTo(WINTER_SPECIAL_END_DATE);
  }

  @Test
  void givenMultipleSpecialOffers_whenSave_thenFindAllByRestaurantId() {
    SpecialOffer specialOffer1 =
        new SpecialOffer(
            RESTAURANT_ID,
            WINTER_SPECIAL_TITLE,
            WINTER_SPECIAL_DESCRIPTION,
            WINTER_SPECIAL_START_DATE,
            WINTER_SPECIAL_END_DATE);
    SpecialOffer specialOffer2 =
        new SpecialOffer(
            RESTAURANT_ID,
            SUMMER_SPECIAL_TITLE,
            SUMMER_SPECIAL_DESCRIPTION,
            SUMMER_SPECIAL_START_DATE,
            SUMMER_SPECIAL_END_DATE);

    specialOfferRepository.save(specialOffer1);
    specialOfferRepository.save(specialOffer2);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID);

    assertThat(specialOffers).hasSize(2);

    assertThat(specialOffers.get(0).getTitle()).isEqualTo(WINTER_SPECIAL_TITLE);
    assertThat(specialOffers.get(1).getTitle()).isEqualTo(SUMMER_SPECIAL_TITLE);
  }

  @Test
  void givenNoSpecialOffers_whenGetSpecialOffers_thenReturnEmptyList() {
    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(NON_EXISTENT_RESTAURANT_ID);

    assertThat(specialOffers).isEmpty();
  }

  @Test
  void givenSpecialOfferWithNullFields_whenSave_thenHandleNullValuesCorrectly() {
    SpecialOffer specialOffer = new SpecialOffer(RESTAURANT_ID, null, null, null, null);

    specialOfferRepository.save(specialOffer);

    List<SpecialOffer> specialOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID);

    assertThat(specialOffers).hasSize(1);
    SpecialOffer retrievedSpecialOffer = specialOffers.get(0);
    assertThat(retrievedSpecialOffer.getTitle()).isNull();
    assertThat(retrievedSpecialOffer.getDescription()).isNull();
    assertThat(retrievedSpecialOffer.getValidFrom()).isNull();
    assertThat(retrievedSpecialOffer.getValidUntil()).isNull();
  }
}
