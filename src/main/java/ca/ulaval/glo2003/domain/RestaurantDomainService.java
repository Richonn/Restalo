package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.restaurant.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.restaurant.OpenedHours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantDomainService {

  public void validateHeaders(String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      throw new MissingParameterException("Missing Owner header");
    }
  }

  public void validateRestaurant(Restaurant restaurant) {
    restaurant.validateFields();
  }

  public void isTimeRangeValid(OpenedHours openedHours) {
    openedHours.isTimeRangeValid();
  }
}
