package ca.ulaval.glo2003.infra.persistence.mongo.mappers;

import ca.ulaval.glo2003.domain.restaurant.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;
import java.time.LocalTime;

public class RestaurantDocumentMapper {

  public static RestaurantDocument toDocument(Restaurant restaurant) {
    if (restaurant == null) {
      return null;
    }

    return new RestaurantDocument(
        restaurant.getId(),
        restaurant.getName(),
        restaurant.getCapacity(),
        restaurant.getOpeningHour(),
        restaurant.getClosingHour(),
        restaurant.getReservationLength(),
        restaurant.getOwnerId());
  }

  public static Restaurant toDomain(RestaurantDocument document) {
    if (document == null) {
      return null;
    }

    LocalTime openingHour =
        document.getOpeningHour() != null ? LocalTime.parse(document.getOpeningHour()) : null;
    LocalTime closingHour =
        document.getClosingHour() != null ? LocalTime.parse(document.getClosingHour()) : null;

    Hours hours = new Hours(openingHour, closingHour, document.getReservationDuration());

    return new Restaurant(
        document.getId(), document.getName(), document.getCapacity(), hours, document.getOwnerId());
  }
}
