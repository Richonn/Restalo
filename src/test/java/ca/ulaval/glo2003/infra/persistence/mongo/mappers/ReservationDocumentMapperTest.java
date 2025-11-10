package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

public class ReservationDocumentMapperTest {

  @Test
  void givenNullReservation_whenToDocument_thenReturnNull() {
    ReservationDocument result = ReservationDocumentMapper.toDocument(null);

    assertThat(result).isNull();
  }

  @Test
  void givenValidReservation_whenToDocument_thenReturnExpectedDocument() {
    LocalDate date = LocalDate.of(2025, 12, 25);
    LocalTime startTime = LocalTime.of(18, 30);
    LocalTime endTime = LocalTime.of(20, 30);
    Integer groupSize = 4;
    String customerName = "John Doe";
    String customerEmail = "john.doe@example.com";
    String customerPhone = "1234567890";
    String restaurantId = "restaurant123";

    Customer customer = new Customer(customerName, customerEmail, customerPhone);
    ReservationTime reservationTime = new ReservationTime(startTime, endTime);
    Reservation reservation =
        new Reservation(date, reservationTime, groupSize, customer, restaurantId);

    ReservationDocument document = ReservationDocumentMapper.toDocument(reservation);

    assertThat(document).isNotNull();
    assertThat(document.getId()).isEqualTo(reservation.getId());
    assertThat(document.getDate()).isEqualTo("2025-12-25");
    assertThat(document.getStartTime()).isEqualTo("18:30");
    assertThat(document.getEndTime()).isEqualTo("20:30");
    assertThat(document.getGroupSize()).isEqualTo(groupSize);
    assertThat(document.getRestaurantId()).isEqualTo(restaurantId);
    assertThat(document.getCustomer()).isNotNull();
    assertThat(document.getCustomer().getName()).isEqualTo(customerName);
    assertThat(document.getCustomer().getEmail()).isEqualTo(customerEmail);
    assertThat(document.getCustomer().getPhoneNumber()).isEqualTo(customerPhone);
  }

  @Test
  void givenReservationWithNullEndTime_whenToDocument_thenHandleGracefully() {
    LocalDate date = LocalDate.of(2025, 12, 25);
    LocalTime startTime = LocalTime.of(18, 30);
    Integer groupSize = 4;
    String customerName = "John Doe";
    String customerEmail = "john.doe@example.com";
    String customerPhone = "1234567890";
    String restaurantId = "restaurant123";

    Customer customer = new Customer(customerName, customerEmail, customerPhone);
    ReservationTime reservationTime = new ReservationTime(startTime, null);
    Reservation reservation =
        new Reservation(date, reservationTime, groupSize, customer, restaurantId);

    ReservationDocument document = ReservationDocumentMapper.toDocument(reservation);

    assertThat(document).isNotNull();
    assertThat(document.getStartTime()).isEqualTo("18:30");
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
    LocalDate date = LocalDate.of(2025, 12, 25);
    LocalTime startTime = LocalTime.of(18, 30);
    LocalTime endTime = LocalTime.of(20, 30);
    Integer groupSize = 4;
    String restaurantId = "restaurant123";

    ReservationDocument document =
        new ReservationDocument(
            id,
            date,
            startTime,
            endTime,
            groupSize,
            new ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument(
                "John Doe", "john.doe@example.com", "1234567890"),
            restaurantId);

    Reservation reservation = ReservationDocumentMapper.toDomain(document);

    assertThat(reservation).isNotNull();
    assertThat(reservation.getDate()).isEqualTo(date);
    assertThat(reservation.getStartTime()).isEqualTo(startTime);
    assertThat(reservation.getEndTime()).isEqualTo(endTime);
    assertThat(reservation.getGroupSize()).isEqualTo(groupSize);
    assertThat(reservation.getRestaurantId()).isEqualTo(restaurantId);
    assertThat(reservation.getCustomer()).isNotNull();
    assertThat(reservation.getCustomer().getName()).isEqualTo("John Doe");
    assertThat(reservation.getCustomer().getEmail()).isEqualTo("john.doe@example.com");
    assertThat(reservation.getCustomer().getPhoneNumber()).isEqualTo("1234567890");
  }

  @Test
  void givenDocumentWithNullTimes_whenToDomain_thenHandleGracefully() {
    String id = "reservation123";
    LocalDate date = LocalDate.of(2025, 12, 25);
    Integer groupSize = 4;
    String restaurantId = "restaurant123";

    ReservationDocument document =
        new ReservationDocument(
            id,
            date,
            null,
            null,
            groupSize,
            new ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument(
                "John Doe", "john.doe@example.com", "1234567890"),
            restaurantId);

    Reservation reservation = ReservationDocumentMapper.toDomain(document);

    assertThat(reservation).isNotNull();
    assertThat(reservation.getStartTime()).isNull();
    assertThat(reservation.getEndTime()).isNull();
  }

  @Test
  void givenReservation_whenToDocumentAndBackToDomain_thenPreservesData() {
    LocalDate date = LocalDate.of(2025, 12, 25);
    LocalTime startTime = LocalTime.of(18, 30);
    LocalTime endTime = LocalTime.of(20, 30);
    Integer groupSize = 4;
    String customerName = "John Doe";
    String customerEmail = "john.doe@example.com";
    String customerPhone = "1234567890";
    String restaurantId = "restaurant123";

    Customer customer = new Customer(customerName, customerEmail, customerPhone);
    ReservationTime reservationTime = new ReservationTime(startTime, endTime);
    Reservation originalReservation =
        new Reservation(date, reservationTime, groupSize, customer, restaurantId);

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
    ReservationDocument document = new ReservationDocument();
    document.setId("reservation123");
    document.setDate("invalid-date");
    document.setStartTime("18:30");
    document.setEndTime("20:30");
    document.setGroupSize(4);
    document.setCustomer(new CustomerDocument("John Doe", "john@example.com", "1234567890"));
    document.setRestaurantId("restaurant123");

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithInvalidStartTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document = new ReservationDocument();
    document.setId("reservation123");
    document.setDate("2025-12-25");
    document.setStartTime("25:99");
    document.setEndTime("20:30");
    document.setGroupSize(4);
    document.setCustomer(new CustomerDocument("John Doe", "john@example.com", "1234567890"));
    document.setRestaurantId("restaurant123");

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithInvalidEndTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document = new ReservationDocument();
    document.setId("reservation123");
    document.setDate("2025-12-25");
    document.setStartTime("18:30");
    document.setEndTime("not-a-time");
    document.setGroupSize(4);
    document.setCustomer(new CustomerDocument("John Doe", "john@example.com", "1234567890"));
    document.setRestaurantId("restaurant123");

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithEmptyDateString_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document = new ReservationDocument();
    document.setId("reservation123");
    document.setDate("");
    document.setStartTime("18:30");
    document.setEndTime("20:30");
    document.setGroupSize(4);
    document.setCustomer(new CustomerDocument("John Doe", "john@example.com", "1234567890"));
    document.setRestaurantId("restaurant123");

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithMalformedDateFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document = new ReservationDocument();
    document.setId("reservation123");
    document.setDate("12/25/2025");
    document.setStartTime("18:30");
    document.setEndTime("20:30");
    document.setGroupSize(4);
    document.setCustomer(new CustomerDocument("John Doe", "john@example.com", "1234567890"));
    document.setRestaurantId("restaurant123");

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }

  @Test
  void givenDocumentWithMalformedTimeFormat_whenToDomain_thenThrowsDateTimeParseException() {
    ReservationDocument document = new ReservationDocument();
    document.setId("reservation123");
    document.setDate("2025-12-25");
    document.setStartTime("18-30");
    document.setEndTime("20:30");
    document.setGroupSize(4);
    document.setCustomer(new CustomerDocument("John Doe", "john@example.com", "1234567890"));
    document.setRestaurantId("restaurant123");

    DateTimeParseException exception =
        assertThrows(
            DateTimeParseException.class, () -> ReservationDocumentMapper.toDomain(document));

    assertThat(exception.getMessage()).contains("could not be parsed");
  }
}
