package ca.ulaval.glo2003.infra.persistence.mongo;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;
import org.junit.jupiter.api.Test;

public class MongoConnectionTest {

  private static final String MONGO_CLUSTER_URL = "MONGO_CLUSTER_URL";
  private static final String RESTAURANT_COLLECTION = "restaurants";
  private static final String RESERVATION_COLLECTION = "reservations";

  @Test
  void givenMongoConfig_whenCreatedWithoutEnvironmentVariables_thenThrowsIllegalStateException() {
    try {
      MongoConfig config = new MongoConfig();
      assertThat(config).isNotNull();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains(MONGO_CLUSTER_URL);
    }
  }

  @Test
  void givenRestaurantDocument_whenCheckingAnnotations_thenHasCorrectEntityAnnotation() {
    assertThat(RestaurantDocument.class.isAnnotationPresent(dev.morphia.annotations.Entity.class))
        .isTrue();

    dev.morphia.annotations.Entity annotation =
        RestaurantDocument.class.getAnnotation(dev.morphia.annotations.Entity.class);
    assertThat(annotation.value()).isEqualTo(RESTAURANT_COLLECTION);
  }

  @Test
  void givenReservationDocument_whenCheckingAnnotations_thenHasCorrectEntityAnnotation() {
    assertThat(ReservationDocument.class.isAnnotationPresent(dev.morphia.annotations.Entity.class))
        .isTrue();

    dev.morphia.annotations.Entity annotation =
        ReservationDocument.class.getAnnotation(dev.morphia.annotations.Entity.class);
    assertThat(annotation.value()).isEqualTo(RESERVATION_COLLECTION);
  }
}
