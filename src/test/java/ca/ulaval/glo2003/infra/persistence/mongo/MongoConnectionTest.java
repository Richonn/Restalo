package ca.ulaval.glo2003.infra.persistence.mongo;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;
import org.junit.jupiter.api.Test;

public class MongoConnectionTest {

  @Test
  void givenMongoConfig_whenCreatedWithoutEnvironmentVariables_thenThrowsIllegalStateException() {
    try {
      MongoConfig config = new MongoConfig();
      assertThat(config).isNotNull();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("MONGO_CLUSTER_URL");
    }
  }

  @Test
  void givenRestaurantDocument_whenCheckingAnnotations_thenHasCorrectEntityAnnotation() {
    assertThat(RestaurantDocument.class.isAnnotationPresent(dev.morphia.annotations.Entity.class))
        .isTrue();

    dev.morphia.annotations.Entity annotation =
        RestaurantDocument.class.getAnnotation(dev.morphia.annotations.Entity.class);
    assertThat(annotation.value()).isEqualTo("restaurants");
  }

  @Test
  void givenReservationDocument_whenCheckingAnnotations_thenHasCorrectEntityAnnotation() {
    assertThat(ReservationDocument.class.isAnnotationPresent(dev.morphia.annotations.Entity.class))
        .isTrue();

    dev.morphia.annotations.Entity annotation =
        ReservationDocument.class.getAnnotation(dev.morphia.annotations.Entity.class);
    assertThat(annotation.value()).isEqualTo("reservations");
  }
}
