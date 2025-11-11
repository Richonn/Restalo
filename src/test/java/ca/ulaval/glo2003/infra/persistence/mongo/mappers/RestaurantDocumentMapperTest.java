package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoRestaurantDocumentFixture;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

public class RestaurantDocumentMapperTest {

  @Test
  void givenNullRestaurant_whenToDocument_thenReturnNull() {
    RestaurantDocument result = RestaurantDocumentMapper.toDocument(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidRestaurant_whenToDocument_thenReturnExpectedDocument() {
    String name = "Le Bistro";
    Integer capacity = 50;
    LocalTime openingHour = LocalTime.of(10, 0);
    LocalTime closingHour = LocalTime.of(22, 0);
    Integer reservationDuration = 60;
    String ownerId = "owner123";

    Hours hours = new Hours(openingHour, closingHour, reservationDuration);
    Restaurant restaurant = new Restaurant(name, capacity, hours, ownerId);

    RestaurantDocument document = RestaurantDocumentMapper.toDocument(restaurant);

    assertThat(document).isNotNull();
    assertThat(document.getId()).isEqualTo(restaurant.getId());
    assertThat(document.getName()).isEqualTo(name);
    assertThat(document.getCapacity()).isEqualTo(capacity);
    assertThat(document.getOpeningHour()).isEqualTo("10:00");
    assertThat(document.getClosingHour()).isEqualTo("22:00");
    assertThat(document.getReservationDuration()).isEqualTo(reservationDuration);
    assertThat(document.getOwnerId()).isEqualTo(ownerId);
  }

  @Test
  void givenRestaurantWithNullHours_whenToDocument_thenHandleGracefully() {
    String name = "Le Bistro";
    Integer capacity = 50;
    String ownerId = "owner123";

    Hours hours = new Hours(null, null, 60);
    Restaurant restaurant = new Restaurant(name, capacity, hours, ownerId);

    RestaurantDocument document = RestaurantDocumentMapper.toDocument(restaurant);

    assertThat(document).isNotNull();
    assertThat(document.getOpeningHour()).isNull();
    assertThat(document.getClosingHour()).isNull();
  }

  @Test
  void givenNullDocument_whenToDomain_thenReturnNull() {
    Restaurant result = RestaurantDocumentMapper.toDomain(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidDocument_whenToDomain_thenReturnExpectedRestaurant() {
    String id = "restaurant123";
    String name = "Le Bistro";
    Integer capacity = 50;
    LocalTime openingHour = LocalTime.of(10, 0);
    LocalTime closingHour = LocalTime.of(22, 0);
    Integer reservationDuration = 60;
    String ownerId = "owner123";

    RestaurantDocument document =
        new RestaurantDocument(
            id, name, capacity, openingHour, closingHour, reservationDuration, ownerId);

    Restaurant restaurant = RestaurantDocumentMapper.toDomain(document);

    assertThat(restaurant).isNotNull();
    assertThat(restaurant.getName()).isEqualTo(name);
    assertThat(restaurant.getCapacity()).isEqualTo(capacity);
    assertThat(restaurant.getOpeningHour()).isEqualTo(openingHour);
    assertThat(restaurant.getClosingHour()).isEqualTo(closingHour);
    assertThat(restaurant.getReservationLength()).isEqualTo(reservationDuration);
    assertThat(restaurant.getOwnerId()).isEqualTo(ownerId);
  }

  @Test
  void givenDocumentWithNullHours_whenToDomain_thenHandleGracefully() {
    String id = "restaurant123";
    String name = "Le Bistro";
    Integer capacity = 50;
    Integer reservationDuration = 60;
    String ownerId = "owner123";

    RestaurantDocument document =
        new RestaurantDocument(id, name, capacity, null, null, reservationDuration, ownerId);

    Restaurant restaurant = RestaurantDocumentMapper.toDomain(document);

    assertThat(restaurant).isNotNull();
    assertThat(restaurant.getOpeningHour()).isNull();
    assertThat(restaurant.getClosingHour()).isNull();
  }

  @Test
  void givenRestaurant_whenToDocumentAndBackToDomain_thenPreservesData() {
    String name = "Le Bistro";
    Integer capacity = 50;
    LocalTime openingHour = LocalTime.of(10, 30);
    LocalTime closingHour = LocalTime.of(22, 45);
    Integer reservationDuration = 90;
    String ownerId = "owner123";

    Hours hours = new Hours(openingHour, closingHour, reservationDuration);
    Restaurant originalRestaurant = new Restaurant(name, capacity, hours, ownerId);

    RestaurantDocument document = RestaurantDocumentMapper.toDocument(originalRestaurant);
    Restaurant restoredRestaurant = RestaurantDocumentMapper.toDomain(document);

    assertThat(restoredRestaurant).isNotNull();
    assertThat(restoredRestaurant.getName()).isEqualTo(originalRestaurant.getName());
    assertThat(restoredRestaurant.getCapacity()).isEqualTo(originalRestaurant.getCapacity());
    assertThat(restoredRestaurant.getOpeningHour()).isEqualTo(originalRestaurant.getOpeningHour());
    assertThat(restoredRestaurant.getClosingHour()).isEqualTo(originalRestaurant.getClosingHour());
    assertThat(restoredRestaurant.getReservationLength())
        .isEqualTo(originalRestaurant.getReservationLength());
    assertThat(restoredRestaurant.getOwnerId()).isEqualTo(originalRestaurant.getOwnerId());
  }

  @Test
  void givenDocumentWithInvalidOpeningHourFormat_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document =
        new MongoRestaurantDocumentFixture().withOpeningHour("invalid-time").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithInvalidClosingHourFormat_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document =
        new MongoRestaurantDocumentFixture().withClosingHour("25:99").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithEmptyTimeStrings_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document = new MongoRestaurantDocumentFixture().withOpeningHour("").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithMalformedTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document =
        new MongoRestaurantDocumentFixture().withOpeningHour("10-30").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }
}
