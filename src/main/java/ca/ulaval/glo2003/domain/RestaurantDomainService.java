package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.restaurant.dto.SearchRestaurantsDto;
import ca.ulaval.glo2003.api.restaurant.exceptions.MissingParameterException;
import java.security.InvalidParameterException;
import java.time.LocalTime;

public class RestaurantDomainService {

  public void validateHeaders(String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      throw new MissingParameterException("Missing Owner header");
    }
  }

  public void validateNameAndCapacityFields(String name, Integer capacity) {
    if (name == null || name.isBlank()) {
      throw new MissingParameterException(
          "The name of the restaurant is required and cannot be blank");
    }
    if (capacity == null) {
      throw new MissingParameterException("The capacity of the restaurant is required");
    }
    if (capacity <= 0) {
      throw new InvalidParameterException("The capacity of the restaurant must be positive");
    }
  }

  public void validateHoursFields(LocalTime open, LocalTime close) {
    if (open == null) {
      throw new MissingParameterException("The opening hour of the restaurant is required");
    }
    if (close == null) {
      throw new MissingParameterException("The closing hour of the restaurant is required");
    }
    if (close.isBefore(open)) {
      throw new InvalidParameterException("Closing hour cannot be before opening hour");
    }
    if (open.plusHours(1).isAfter(close)) {
      throw new InvalidParameterException("The restaurant must be open for at least one hour");
    }
  }

  public boolean matchesSearch(Restaurant restaurant, SearchRestaurantsDto searchRequest) {
    LocalTime from = searchRequest.opened() != null ? searchRequest.opened().from() : null;
    LocalTime to = searchRequest.opened() != null ? searchRequest.opened().to() : null;

    return matchesName(restaurant, searchRequest.name()) && matchesOpenHours(restaurant, from, to);
  }

  public boolean matchesName(Restaurant restaurant, String nameQuery) {
    if (nameQuery == null || nameQuery.isBlank()) return true;

    String normalizedQuery = nameQuery.replaceAll("\\s+", "").toLowerCase();
    String normalizedName = restaurant.getName().replaceAll("\\s+", "").toLowerCase();
    return normalizedName.contains(normalizedQuery);
  }

  public boolean matchesOpenHours(Restaurant restaurant, LocalTime from, LocalTime to) {
    LocalTime openingHour = restaurant.getOpeningHour();
    LocalTime closingHour = restaurant.getClosingHour();

    if (from != null && to != null && from.isAfter(to)) {
      return false;
    } else if (from != null && to != null) {
      return !openingHour.isAfter(from) && !closingHour.isBefore(to);
    } else if (from != null) {
      return !openingHour.isAfter(from) && closingHour.isAfter(from);
    } else if (to != null) {
      return openingHour.isBefore(to) && !closingHour.isBefore(to);
    }
    return true;
  }
}
