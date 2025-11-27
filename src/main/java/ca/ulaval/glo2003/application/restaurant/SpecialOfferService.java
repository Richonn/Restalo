package ca.ulaval.glo2003.application.restaurant;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.SpecialOffer;
import ca.ulaval.glo2003.domain.restaurant.SpecialOfferFactory;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class SpecialOfferService {

  private final RestaurantRepository restaurantRepository;
  private final SpecialOfferFactory specialOfferFactory;
  private final SpecialOfferRepository specialOfferRepository;

  @Inject
  public SpecialOfferService(
      RestaurantRepository restaurantRepository,
      SpecialOfferFactory specialOfferFactory,
      SpecialOfferRepository specialOfferRepository) {
    this.restaurantRepository = restaurantRepository;
    this.specialOfferFactory = specialOfferFactory;
    this.specialOfferRepository = specialOfferRepository;
  }

  public String createSpecialOffer(
      String ownerId,
      String restaurantId,
      String title,
      String description,
      LocalDate validFrom,
      LocalDate validUntil) {

    Restaurant restaurant = restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId);
    if (restaurant == null) {
      throw new InvalidParameterException("Restaurant not found or you are not the owner");
    }

    if (validUntil.isBefore(validFrom)) {
      throw new InvalidParameterException(
          "The end date of the offer cannot be before the start date");
    }

    List<SpecialOffer> existingOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(restaurantId);

    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            restaurantId, title, description, validFrom, validUntil);

    for (SpecialOffer offer : existingOffers) {
      if (newOffer.overlapsWith(offer)) {
        throw new InvalidParameterException("A special offer already exists for this period.");
      }
    }

    specialOfferRepository.save(newOffer);

    return newOffer.getId();
  }
}
