package ca.ulaval.glo2003.application.specialOffer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.common.SpecialOfferDto;
import ca.ulaval.glo2003.api.dto.request.SpecialOfferRequestDto;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.SpecialOfferDomainService;
import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOfferFactory;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialOfferServiceTest {

  private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
  private final SpecialOfferFactory specialOfferFactory = mock(SpecialOfferFactory.class);
  private final SpecialOfferRepository specialOfferRepository = mock(SpecialOfferRepository.class);
  private final SpecialOfferDomainService specialOfferDomainService =
      mock(SpecialOfferDomainService.class);

  private SpecialOfferService specialOfferService;

  private final String OWNER_ID = "owner-123";
  private final String RESTAURANT_ID = "restaurant-123";
  private SpecialOfferRequestDto requestDto;
  private final Restaurant restaurant = mock(Restaurant.class);
  private final SpecialOffer specialOffer = mock(SpecialOffer.class);

  private LocalDate startDate;
  private LocalDate endDate;

  @BeforeEach
  void setUp() {
    specialOfferService =
        new SpecialOfferService(
            restaurantRepository,
            specialOfferFactory,
            specialOfferRepository,
            specialOfferDomainService);

    startDate = LocalDate.now();
    endDate = startDate.plusDays(7);

    SpecialOfferDto offerDto =
        new SpecialOfferDto("Special Pizza", "50% off large pizza", startDate, endDate);
    requestDto = new SpecialOfferRequestDto(offerDto);
  }

  @Test
  void givenValidOwnerAndRestaurant_whenCreateSpecialOffer_thenReturnId() {
    String expectedId = "offer-123";

    when(restaurantRepository.getRestaurantByIdOfOwner(OWNER_ID, RESTAURANT_ID))
        .thenReturn(restaurant);
    when(specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID))
        .thenReturn(Collections.emptyList());
    when(specialOfferFactory.createSpecialOffer(
            eq(RESTAURANT_ID),
            eq("Special Pizza"),
            eq("50% off large pizza"),
            eq(startDate),
            eq(endDate)))
        .thenReturn(specialOffer);
    doNothing()
        .when(specialOfferDomainService)
        .validateNoOverlappingOffers(specialOffer, Collections.emptyList());
    doNothing().when(specialOfferRepository).save(specialOffer);
    when(specialOffer.getId()).thenReturn(expectedId);

    String actualId = specialOfferService.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto);

    assertThat(actualId).isEqualTo(expectedId);
    verify(specialOfferRepository).save(specialOffer);
    verify(specialOfferDomainService)
        .validateNoOverlappingOffers(
            specialOffer, Collections.emptyList()); // Ensure validation is called
    verify(specialOfferFactory)
        .createSpecialOffer(
            eq(RESTAURANT_ID),
            eq("Special Pizza"),
            eq("50% off large pizza"),
            eq(startDate),
            eq(endDate));
  }

  @Test
  void givenNonExistentRestaurant_whenCreateSpecialOffer_thenThrowsNotFoundException() {
    when(restaurantRepository.getRestaurantByIdOfOwner(OWNER_ID, RESTAURANT_ID)).thenReturn(null);

    assertThrows(
        NotFoundException.class,
        () -> specialOfferService.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto));

    verifyNoInteractions(specialOfferFactory, specialOfferRepository, specialOfferDomainService);
  }

  @Test
  void givenOverlappingSpecialOffer_whenCreateSpecialOffer_thenDomainServiceIsCalled() {
    when(restaurantRepository.getRestaurantByIdOfOwner(OWNER_ID, RESTAURANT_ID))
        .thenReturn(restaurant);
    List<SpecialOffer> existingOffers = List.of(mock(SpecialOffer.class));
    when(specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID))
        .thenReturn(existingOffers);
    when(specialOfferFactory.createSpecialOffer(
            eq(RESTAURANT_ID),
            eq("Special Pizza"),
            eq("50% off large pizza"),
            eq(startDate),
            eq(endDate)))
        .thenReturn(specialOffer);

    specialOfferService.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto);

    verify(specialOfferDomainService).validateNoOverlappingOffers(specialOffer, existingOffers);
    verify(specialOfferRepository).save(specialOffer);
  }
}
