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

  private static final String RESTAURANT_ID = "restaurant-1";
  private static final String WINTER_SPECIAL_TITLE = "Winter Special";
  private static final String WINTER_SPECIAL_DESCRIPTION = "Winter discount menu";
  private static final String SUMMER_SPECIAL_TITLE = "Summer Special";
  private static final String SUMMER_SPECIAL_DESCRIPTION = "Summer discount menu";
  private static final String AUTUMN_SPECIAL_TITLE = "Autumn Special";
  private static final String AUTUMN_SPECIAL_DESCRIPTION = "Autumn menu";
  private static final String OVERLAP_ERROR_MESSAGE =
      "A special offer already exists for this period.";
  private static final String INVALID_RESTAURANT_ID = "nonexistent-restaurant";

  private static final LocalDate DATE_START = LocalDate.of(2024, 12, 1);
  private static final LocalDate DATE_END = LocalDate.of(2024, 12, 31);
  private static final LocalDate DATE_OVERLAP_START = LocalDate.of(2024, 12, 5);
  private static final LocalDate DATE_OVERLAP_END = LocalDate.of(2024, 12, 15);

  @Mock private SpecialOfferRepository specialOfferRepository;

  @InjectMocks private SpecialOfferDomainService specialOfferDomainService;

  private SpecialOfferFactory specialOfferFactory;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    specialOfferFactory = new SpecialOfferFactory();
  }

  @Test
  void givenValidOffer_whenValidateNoOverlappingOffers_thenNoException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID, WINTER_SPECIAL_TITLE, WINTER_SPECIAL_DESCRIPTION, DATE_START, DATE_END);

    List<SpecialOffer> existingOffers = new ArrayList<>();
    existingOffers.add(
        new SpecialOffer(
            RESTAURANT_ID,
            SUMMER_SPECIAL_TITLE,
            SUMMER_SPECIAL_DESCRIPTION,
            LocalDate.of(2024, 6, 1),
            LocalDate.of(2024, 6, 30)));

    when(specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID))
        .thenReturn(existingOffers);

    assertDoesNotThrow(
        () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));
  }

  @Test
  void givenOverlappingOffer_whenValidateNoOverlappingOffers_thenThrowsInvalidParameterException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            WINTER_SPECIAL_TITLE,
            WINTER_SPECIAL_DESCRIPTION,
            LocalDate.of(2024, 12, 10),
            LocalDate.of(2024, 12, 20));

    List<SpecialOffer> existingOffers = new ArrayList<>();
    existingOffers.add(
        new SpecialOffer(
            RESTAURANT_ID,
            AUTUMN_SPECIAL_TITLE,
            AUTUMN_SPECIAL_DESCRIPTION,
            DATE_OVERLAP_START,
            DATE_OVERLAP_END));

    when(specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID))
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
            RESTAURANT_ID, WINTER_SPECIAL_TITLE, WINTER_SPECIAL_DESCRIPTION, DATE_START, DATE_END);

    List<SpecialOffer> existingOffers = new ArrayList<>();
    existingOffers.add(
        new SpecialOffer(
            RESTAURANT_ID, WINTER_SPECIAL_TITLE, WINTER_SPECIAL_DESCRIPTION, DATE_START, DATE_END));

    when(specialOfferRepository.getSpecialOffersByRestaurantId(RESTAURANT_ID))
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
                RESTAURANT_ID, "", WINTER_SPECIAL_DESCRIPTION, DATE_START, DATE_END));
  }

  @Test
  void givenInvalidDates_whenCreateSpecialOffer_thenThrowsInvalidParameterException() {

    assertThrows(
        InvalidParameterException.class,
        () ->
            specialOfferFactory.createSpecialOffer(
                RESTAURANT_ID,
                WINTER_SPECIAL_TITLE,
                WINTER_SPECIAL_DESCRIPTION,
                DATE_END,
                DATE_START));
  }

  @Test
  void givenNonExistentRestaurant_whenCreateSpecialOffer_thenNoException() {
    String nonExistentRestaurantId = INVALID_RESTAURANT_ID;
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            nonExistentRestaurantId,
            WINTER_SPECIAL_TITLE,
            WINTER_SPECIAL_DESCRIPTION,
            DATE_START,
            DATE_END);

    when(specialOfferRepository.getSpecialOffersByRestaurantId(nonExistentRestaurantId))
        .thenReturn(new ArrayList<>());

    assertDoesNotThrow(
        () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, new ArrayList<>()));
  }

  @Test
  void givenOfferStartingOnExistingOfferEnd_thenThrowsException() {
    SpecialOffer existingOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            SUMMER_SPECIAL_TITLE,
            "desc",
            LocalDate.of(2024, 6, 1),
            LocalDate.of(2024, 6, 30));
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "July Special",
            "desc",
            LocalDate.of(2024, 6, 30),
            LocalDate.of(2024, 7, 10));
    List<SpecialOffer> existingOffers = List.of(existingOffer);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferEndingOnExistingOfferStart_thenThrowsException() {
    SpecialOffer existingOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "July Special",
            "desc",
            LocalDate.of(2024, 7, 1),
            LocalDate.of(2024, 7, 15));
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "June Special",
            "desc",
            LocalDate.of(2024, 6, 20),
            LocalDate.of(2024, 7, 1));
    List<SpecialOffer> existingOffers = List.of(existingOffer);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferCompletelyInsideExistingOffer_thenThrowsException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "Inside Offer",
            "Description",
            LocalDate.of(2024, 12, 10),
            DATE_OVERLAP_END);
    List<SpecialOffer> existingOffers =
        List.of(
            specialOfferFactory.createSpecialOffer(
                RESTAURANT_ID, "Winter Offer", "Description", DATE_START, DATE_END));

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferCompletelyCoversExistingOffer_thenThrowsException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID, "Big Offer", "Description", DATE_START, DATE_END);
    List<SpecialOffer> existingOffers =
        List.of(
            specialOfferFactory.createSpecialOffer(
                RESTAURANT_ID,
                "Short Offer",
                "Description",
                LocalDate.of(2024, 12, 10),
                DATE_OVERLAP_END));

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenOfferOverlappingWithMultipleExistingOffers_thenThrowsException() {
    SpecialOffer offer1 =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "Spring Special",
            "desc",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31));
    SpecialOffer offer2 =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            SUMMER_SPECIAL_TITLE,
            "desc",
            LocalDate.of(2024, 6, 1),
            LocalDate.of(2024, 6, 30));
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "Summer Overlap",
            "desc",
            LocalDate.of(2024, 6, 15),
            LocalDate.of(2024, 7, 15));
    List<SpecialOffer> existingOffers = List.of(offer1, offer2);

    InvalidParameterException exception =
        assertThrows(
            InvalidParameterException.class,
            () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));

    assertThat(exception.getMessage()).isEqualTo(OVERLAP_ERROR_MESSAGE);
  }

  @Test
  void givenMultipleNonOverlappingExistingOffers_thenNoException() {
    SpecialOffer newOffer =
        specialOfferFactory.createSpecialOffer(
            RESTAURANT_ID,
            "New Offer",
            "desc",
            LocalDate.of(2024, 8, 1),
            LocalDate.of(2024, 8, 15));
    List<SpecialOffer> existingOffers =
        List.of(
            specialOfferFactory.createSpecialOffer(
                RESTAURANT_ID,
                "Offer 1",
                "desc",
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 15)),
            specialOfferFactory.createSpecialOffer(
                RESTAURANT_ID,
                "Offer 2",
                "desc",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 15)));

    assertDoesNotThrow(
        () -> specialOfferDomainService.validateNoOverlappingOffers(newOffer, existingOffers));
  }
}
