package ca.ulaval.glo2003.application.specialOffer;

import ca.ulaval.glo2003.api.dto.request.SpecialOfferRequestDto;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.SpecialOfferDomainService;
import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOfferFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;

public class SpecialOfferService {

  private final RestaurantRepository restaurantRepository;
  private final SpecialOfferFactory specialOfferFactory;
  private final SpecialOfferRepository specialOfferRepository;
  private final SpecialOfferDomainService specialOfferDomainService;

  @Inject
  public SpecialOfferService(
      RestaurantRepository restaurantRepository,
      SpecialOfferFactory specialOfferFactory,
      SpecialOfferRepository specialOfferRepository,
      SpecialOfferDomainService specialOfferDomainService) {
    this.restaurantRepository = restaurantRepository;
    this.specialOfferFactory = specialOfferFactory;
    this.specialOfferRepository = specialOfferRepository;
    this.specialOfferDomainService = specialOfferDomainService;
  }

  public String createSpecialOffer(
      String ownerId, String restaurantId, SpecialOfferRequestDto request) {

    Restaurant restaurant = restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId);
    if (restaurant == null) {
      throw new NotFoundException();
    }

    String title = request.offer().title();
    String description = request.offer().description();
    LocalDate validFrom = request.offer().validFrom();
    LocalDate validUntil = request.offer().validUntil();

    List<SpecialOffer> existingOffers =
        specialOfferRepository.getSpecialOffersByRestaurantId(restaurantId);

    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            restaurantId, title, description, validFrom, validUntil);

    specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers);

    specialOfferRepository.save(newOffer);

    return newOffer.getId();
  }
}
