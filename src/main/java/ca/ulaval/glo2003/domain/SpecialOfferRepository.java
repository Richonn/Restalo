package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.util.List;

public interface SpecialOfferRepository {
  void save(SpecialOffer specialOffer);

  List<SpecialOffer> getSpecialOffersByRestaurantId(String restaurantId);
}
