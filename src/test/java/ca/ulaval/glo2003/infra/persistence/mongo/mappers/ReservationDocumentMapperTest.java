package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.reservation.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoReservationDocumentFixture;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

public class ReservationDocumentMapperTest {

  private static final LocalDate DATE = LocalDate.of(2025, 12, 25);
  private static final LocalTime START_TIME = LocalTime.of(18, 30);
  private static final LocalTime END_TIME = LocalTime.of(20, 30);
  private static final int GROUP_SIZE = 4;
  private static final String CUSTOMER_NAME = "John Doe";
  private static final String CUSTOMER_EMAIL = "john.doe@example.com";
  private static final String CUSTOMER_PHONE = "1234567890";
  private static final String RESTAURANT_ID = "restaurant123";
  private static final String PARSING_ERROR_MESSAGE = "could not be parsed";

  @Test
  void givenNullReservation_whenToDocument_thenReturnNull() {
    ReservationDocument result = ReservationDocumentMapper.toDocument(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidReservation_whenToDocument_thenReturnExpectedDocument() {
    Customer customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE);
    ReservationTime reservationTime = new ReservationTime(START_TIME, END_TIME);
    Reservation reservation =
        new Reservation(DATE, reservationTime, GROUP_SIZE, customer, RESTAURANT_ID);

    ReservationDocument document = ReservationDocumentMapper.toDocument(reservation);

    assertThat(document).isNotNull();
    assertThat(document.getId()).isEqualTo(reservation.getId());
    assertThat(document.getDate()).isEqualTo("2025-12-25");
    assertThat(document.getStartTime()).isEqualTo("18:30");
    assertThat(document.getEndTime()).isEqualTo("20:30");
    assertThat(document.getGroupSize()).isEqualTo(GROUP_SIZE);
    assertThat(document.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(document.getCustomer()).isNotNull();
    assertThat(document.getCustomer().getName()).isEqualTo(CUSTOMER_NAME);
    assertThat(document.getCustomer().getEmail()).isEqualTo(CUSTOMER_EMAIL);
    assertThat(document.getCustomer().getPhoneNumber()).isEqualTo(CUSTOMER_PHONE);
  }

  @Test
  void givenReservationWithNullEndTime_whenToDocument_thenHandleGracefully() {
    Customer customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE);
    ReservationTime reservationTime = new ReservationTime(START_TIME, null);
    Reservation reservation =
        new Reservation(DATE, reservationTime, GROUP_SIZE, customer, RESTAURANT_ID);

    ReservationDocument document = ReservationDocumentMapper.toDocument(reservation);

    assertThat(document).isNotNull();
    assertThat(document.getStartTime()).isEqualTo(START_TIME.toString());
    assertThat(document.getEndTime()).isNull();
  }

  @Test
  void givenNullDocument_whenToDomain_thenReturnNull() {
    Reservation result = ReservationDocumentMapper.toDomain(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidDocument_whenToDomain_thenReturnExpectedReservation() {
    String id = "reservation123";

    ReservationDocument document =
        new ReservationDocument(
            id,
            DATE,
            START_TIME,
            END_TIME,
            GROUP_SIZE,
            new ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument(
                CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE),
            RESTAURANT_ID);

    Reservation reservation = ReservationDocumentMapper.toDomain(document);

    assertThat(reservation).isNotNull();
    assertThat(reservation.getDate()).isEqualTo(DATE);
    assertThat(reservation.getStartTime()).isEqualTo(START_TIME);
    assertThat(reservation.getEndTime()).isEqualTo(END_TIME);
    assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
    assertThat(reservation.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    assertThat(reservation.getCustomer()).isNotNull();
    assertThat(reservation.getCustomer().getName()).isEqualTo(CUSTOMER_NAME);
    assertThat(reservation.getCustomer().getEmail()).isEqualTo(CUSTOMER_EMAIL);
    assertThat(reservation.getCustomer().getPhoneNumber()).isEqualTo(CUSTOMER_PHONE);
  }

  @Test
  void givenDocumentWithNullTimes_whenToDomain_thenHandleGracefully() {
    String id = "reservation123";

    ReservationDocument document =
        new ReservationDocument(
            id,
            DATE,
            null,
            null,
            GROUP_SIZE,
            new ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument(
                CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE),
            RESTAURANT_ID);

    Reservation reservation = ReservationDocumentMapper.toDomain(document);

    assertThat(reservation).isNotNull();
    assertThat(reservation.getStartTime()).isNull();
    assertThat(reservation.getEndTime()).isNull();
  }

  @Test
  void givenReservation_whenToDocumentAndBackToDomain_thenPreservesData() {

    Customer customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE);
    ReservationTime reservationTime = new ReservationTime(START_TIME, null);
    Reservation originalReservation =
        new Reservation(DATE, reservationTime, GROUP_SIZE, customer, RESTAURANT_ID);

    ReservationDocument document = ReservationDocumentMapper.toDocument(originalReservation);
    Reservation restoredReservation = ReservationDocumentMapper.toDomain(document);

    assertThat(restoredReservation).isNotNull();
    assertThat(restoredReservation.getDate()).isEqualTo(originalReservation.getDate());
    assertThat(restoredReservation.getStartTime()).isEqualTo(originalReservation.getStartTime());
    assertThat(restoredReservation.getEndTime()).isEqualTo(originalReservation.getEndTime());
    assertThat(restoredReservation.getGroupSize()).isEqualTo(originalReservation.getGroupSize());
    assertThat(restoredReservation.getRestaurantId())
        .isEqualTo(originalReservation.getRestaurantId());
    assertThat(restoredReservation.getCustomer().getName())
        .isEqualTo(originalReservation.getCustomer().getName());
    assertThat(restoredReservation.getCustomer().getEmail())
        .isEqualTo(originalReservation.getCustomer().getEmail());
    assertThat(restoredReservation.getCustomer().getPhoneNumber())
        .isEqualTo(originalReservation.getCustomer().getPhoneNumber());
  }

  @Test
  void givenDocumentWithInvalidDateFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document =
        new MongoReservationDocumentFixture().withDate("invalid-date").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithInvalidStartTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document =
        new MongoReservationDocumentFixture().withStartTime("25:99").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithInvalidEndTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document =
        new MongoReservationDocumentFixture().withEndTime("not-a-time").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithEmptyDateString_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document = new MongoReservationDocumentFixture().withDate("").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithMalformedDateFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document =
        new MongoReservationDocumentFixture().withDate("12/25/2025").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }

  @Test
  void givenDocumentWithMalformedTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document =
        new MongoReservationDocumentFixture().withStartTime("18-30").build();

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains(PARSING_ERROR_MESSAGE);
  }
}
