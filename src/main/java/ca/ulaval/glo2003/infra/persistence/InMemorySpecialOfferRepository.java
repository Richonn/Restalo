package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.util.*;
import java.util.stream.Collectors;

public class InMemorySpecialOfferRepository implements SpecialOfferRepository {

  private final Map<String, SpecialOffer> specialOffers = new HashMap<>();

  @Override
  public void save(SpecialOffer specialOffer) {
    specialOffers.put(specialOffer.getId(), specialOffer);
  }

  @Override
  public List<SpecialOffer> getSpecialOffersByRestaurantId(String restaurantId) {
    return specialOffers.values().stream()
        .filter(offer -> offer.getRestaurantId().equals(restaurantId))
        .collect(Collectors.toList());
  }
}
