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

  private static final String NAME = "Le Bistro";
  private static final int CAPACITY = 50;
  private static final LocalTime OPENING_HOURS = LocalTime.of(10, 0);
  private static final LocalTime CLOSING_HOURS = LocalTime.of(22, 0);
  private static final Integer RESERVATION_DURATION = 60;
  private static final String OWNER_ID = "owner123";
  private static final String RESTAURANT_ID = "restaurant123";
  private static final String PARSING_ERROR_MESSAGE = "could not be parsed";

  @Test
  void givenNullRestaurant_whenToDocument_thenReturnNull() {
    RestaurantDocument result = RestaurantDocumentMapper.toDocument(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidRestaurant_whenToDocument_thenReturnExpectedDocument() {
    Hours hours = new Hours(OPENING_HOURS, CLOSING_HOURS, RESERVATION_DURATION);
    Restaurant restaurant = new Restaurant(NAME, CAPACITY, hours, OWNER_ID);

    RestaurantDocument document = RestaurantDocumentMapper.toDocument(restaurant);

    assertThat(document).isNotNull();
    assertThat(document.getId()).isEqualTo(restaurant.getId());
    assertThat(document.getName()).isEqualTo(NAME);
    assertThat(document.getCapacity()).isEqualTo(CAPACITY);
    assertThat(document.getOpeningHour()).isEqualTo("10:00");
    assertThat(document.getClosingHour()).isEqualTo("22:00");
    assertThat(document.getReservationDuration()).isEqualTo(RESERVATION_DURATION);
    assertThat(document.getOwnerId()).isEqualTo(OWNER_ID);
  }

  @Test
  void givenRestaurantWithNullHours_whenToDocument_thenHandleGracefully() {
    Hours hours = new Hours(null, null, 60);
    Restaurant restaurant = new Restaurant(NAME, CAPACITY, hours, OWNER_ID);

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
    RestaurantDocument document =
        new RestaurantDocument(
            RESTAURANT_ID,
            NAME,
            CAPACITY,
            OPENING_HOURS,
            CLOSING_HOURS,
            RESERVATION_DURATION,
            OWNER_ID);

    Restaurant restaurant = RestaurantDocumentMapper.toDomain(document);

    assertThat(restaurant).isNotNull();
    assertThat(restaurant.getName()).isEqualTo(NAME);
    assertThat(restaurant.getCapacity()).isEqualTo(CAPACITY);
    assertThat(restaurant.getOpeningHour()).isEqualTo(OPENING_HOURS);
    assertThat(restaurant.getClosingHour()).isEqualTo(CLOSING_HOURS);
    assertThat(restaurant.getReservationLength()).isEqualTo(RESERVATION_DURATION);
    assertThat(restaurant.getOwnerId()).isEqualTo(OWNER_ID);
  }

  @Test
  void givenDocumentWithNullHours_whenToDomain_thenHandleGracefully() {
    RestaurantDocument document =
        new RestaurantDocument(
            RESTAURANT_ID, NAME, CAPACITY, null, null, RESERVATION_DURATION, OWNER_ID);

    Restaurant restaurant = RestaurantDocumentMapper.toDomain(document);

    assertThat(restaurant).isNotNull();
    assertThat(restaurant.getOpeningHour()).isNull();
    assertThat(restaurant.getClosingHour()).isNull();
  }

  @Test
  void givenRestaurant_whenToDocumentAndBackToDomain_thenPreservesData() {
    Hours hours = new Hours(null, null, 60);
    Restaurant originalRestaurant = new Restaurant(NAME, CAPACITY, hours, OWNER_ID);

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

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithInvalidClosingHourFormat_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document =
        new MongoRestaurantDocumentFixture().withClosingHour("25:99").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithEmptyTimeStrings_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document = new MongoRestaurantDocumentFixture().withOpeningHour("").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithMalformedTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    RestaurantDocument document =
        new MongoRestaurantDocumentFixture().withOpeningHour("10-30").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> RestaurantDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }
}
