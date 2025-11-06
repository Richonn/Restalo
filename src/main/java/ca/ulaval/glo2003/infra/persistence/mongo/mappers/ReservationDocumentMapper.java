package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDocumentMapper {

  public static ReservationDocument toDocument(Reservation reservation) {
    if (reservation == null) {
      return null;
    }

    CustomerDocument customerDoc =
        new CustomerDocument(
            reservation.getCustomer().getName(),
            reservation.getCustomer().getEmail(),
            reservation.getCustomer().getPhoneNumber());

    return new ReservationDocument(
        reservation.getId(),
        reservation.getDate(),
        reservation.getStartTime(),
        reservation.getEndTime(),
        reservation.getGroupSize(),
        customerDoc,
        reservation.getRestaurantId());
  }

  public static Reservation toDomain(ReservationDocument document) {
    if (document == null) {
      return null;
    }

    LocalDate date = document.getDate() != null ? LocalDate.parse(document.getDate()) : null;
    LocalTime startTime =
        document.getStartTime() != null ? LocalTime.parse(document.getStartTime()) : null;
    LocalTime endTime =
        document.getEndTime() != null ? LocalTime.parse(document.getEndTime()) : null;

    ReservationTime reservationTime = new ReservationTime(startTime, endTime);

    CustomerDocument customerDoc = document.getCustomer();
    Customer customer =
        new Customer(customerDoc.getName(), customerDoc.getEmail(), customerDoc.getPhoneNumber());

    return new Reservation(
        date, reservationTime, document.getGroupSize(), customer, document.getRestaurantId());
  }
}
