package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.restaurant.SpecialOffer;
import java.util.List;

public interface SpecialOfferRepository {
  void save(SpecialOffer specialOffer);

  List<SpecialOffer> getSpecialOffersByRestaurantId(String restaurantId);

  SpecialOffer getSpecialOfferById(String offerId);

  void delete(String offerId);
}
