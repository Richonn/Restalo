package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOfferFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SpecialOfferDomainServiceTest {

  @Mock private SpecialOfferRepository specialOfferRepository;

  @InjectMocks private SpecialOfferDomainService specialOfferDomainService;

  private SpecialOfferFactory specialOfferFactory;

  private static final String OVERLAP_ERROR_MESSAGE =
      "A special offer already exists for this period.";

  private static final String INVALID_PARAMETER_ERROR_MESSAGE =
      "Un des paramètres n'est pas valide";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    specialOfferFactory = new SpecialOfferFactory();
  }

  @Test
  void givenValidOffer_whenValidateNoOverlappingOffers_thenNoException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            "restaurant-1",
            "Winter Special",
            "Winter discount menu",
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 31));

    List<SpecialOffer> existingOffers = new ArrayList<>();
    existingOffers.add(
        new SpecialOffer(
            "restaurant-1",
            "Summer Special",
            "Summer discount menu",
            LocalDate.of(2024, 6, 1),
            LocalDate.of(2024, 6, 30)));

    when(specialOfferRepository.getSpecialOffersByRestaurantId("restaurant-1"))
        .thenReturn(existingOffers);

    assertDoesNotThrow(
        () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));
  }

  @Test
  void givenOverlappingOffer_whenValidateNoOverlappingOffers_thenThrowsInvalidParameterException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            "restaurant-1",
            "Winter Special",
            "Winter discount menu",
            LocalDate.of(2024, 12, 10),
            LocalDate.of(2024, 12, 20));

    List<SpecialOffer> existingOffers = new ArrayList<>();
    existingOffers.add(
        new SpecialOffer(
            "restaurant-1",
            "Autumn Special",
            "Autumn menu",
            LocalDate.of(2024, 12, 5),
            LocalDate.of(2024, 12, 15)));

    when(specialOfferRepository.getSpecialOffersByRestaurantId("restaurant-1"))
        .thenReturn(existingOffers);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenIdenticalOffer_whenValidateNoOverlappingOffers_thenThrowsInvalidParameterException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            "restaurant-1",
            "Winter Special",
            "Winter discount menu",
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 31));

    List<SpecialOffer> existingOffers = new ArrayList<>();
    existingOffers.add(
        new SpecialOffer(
            "restaurant-1",
            "Winter Special",
            "Winter discount menu",
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 31)));

    when(specialOfferRepository.getSpecialOffersByRestaurantId("restaurant-1"))
        .thenReturn(existingOffers);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenMissingTitle_whenCreateSpecialOffer_thenNoException() {
    assertDoesNotThrow(
        () ->
            specialOfferFactory.createSpecialOffer(
                "restaurant-1",
                "",
                "Winter discount menu",
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 31)));
  }

  @Test
  void givenInvalidDates_whenCreateSpecialOffer_thenThrowsInvalidParameterException() {

    assertThrows(
        InvalidParameterException.class,
        () ->
            specialOfferFactory.createSpecialOffer(
                "restaurant-1",
                "Winter Special",
                "Winter discount menu",
                LocalDate.of(2024, 12, 31),
                LocalDate.of(2024, 12, 1)));
  }

  @Test
  void givenNonExistentRestaurant_whenCreateSpecialOffer_thenNoException() {
    String nonExistentRestaurantId = "nonexistent-restaurant";
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            nonExistentRestaurantId,
            "Winter Special",
            "Winter discount menu",
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 31));

    when(specialOfferRepository.getSpecialOffersByRestaurantId(nonExistentRestaurantId))
        .thenReturn(new ArrayList<>());

    assertDoesNotThrow(
        () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, new ArrayList<>()));
  }

  @Test
  void givenOfferStartingOnExistingOfferEnd_thenThrowsException() {
    SpecialOffer existingOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Summer Special", "desc",
                    LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
    SpecialOffer newOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "July Special", "desc",
                    LocalDate.of(2024, 6, 30), LocalDate.of(2024, 7, 10));
    List<SpecialOffer> existingOffers = List.of(existingOffer);

    InvalidParameterException exception = assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferEndingOnExistingOfferStart_thenThrowsException() {
    SpecialOffer existingOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "July Special", "desc",
                    LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 15));
    SpecialOffer newOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "June Special", "desc",
                    LocalDate.of(2024, 6, 20), LocalDate.of(2024, 7, 1));
    List<SpecialOffer> existingOffers = List.of(existingOffer);

    InvalidParameterException exception = assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferCompletelyInsideExistingOffer_thenThrowsException() {
    SpecialOffer newOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Inside Offer", "Description",
                    LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 15));
    List<SpecialOffer> existingOffers = List.of(
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Winter Offer", "Description",
                    LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31)));

    InvalidParameterException exception = assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferCompletelyCoversExistingOffer_thenThrowsException() {
    SpecialOffer newOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Big Offer", "Description",
                    LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31));
    List<SpecialOffer> existingOffers = List.of(
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Short Offer", "Description",
                    LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 15)));

    InvalidParameterException exception = assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferOverlappingWithMultipleExistingOffers_thenThrowsException() {
    SpecialOffer offer1 =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Spring Special", "desc",
                    LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31));
    SpecialOffer offer2 =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Summer Special", "desc",
                    LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
    SpecialOffer newOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Summer Overlap", "desc",
                    LocalDate.of(2024, 6, 15), LocalDate.of(2024, 7, 15));
    List<SpecialOffer> existingOffers = List.of(offer1, offer2);

    InvalidParameterException exception = assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenMultipleNonOverlappingExistingOffers_thenNoException() {
    SpecialOffer newOffer =
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "New Offer", "desc",
                    LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 15));
    List<SpecialOffer> existingOffers = List.of(
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Offer 1", "desc",
                    LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 15)),
            specialOfferFactory.createSpecialOffer(
                    "restaurant-1", "Offer 2", "desc",
                    LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 15))
    );

    assertDoesNotThrow(
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));
  }
}
