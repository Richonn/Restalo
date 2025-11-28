package ca.ulaval.glo2003.api.ressources.specialOffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.request.SpecialOfferRequestDto;
import ca.ulaval.glo2003.application.specialOffer.SpecialOfferService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialOfferResourceTest {

  private SpecialOfferService specialOfferService;
  private SpecialOfferResource resource;
  private SpecialOfferResourceFixture fixture;

  private static final String OWNER_ID = "owner-123";
  private static final String RESTAURANT_ID = "restaurant-123";

  @BeforeEach
  void setUp() {
    specialOfferService = mock(SpecialOfferService.class);
    resource = new SpecialOfferResource(specialOfferService);
    fixture = new SpecialOfferResourceFixture();
  }

  @Test
  void createSpecialOffer_givenValidRequest_shouldReturn201AndLocationHeader() {
    SpecialOfferRequestDto requestDto = fixture.givenValidSpecialOfferRequest();

    when(specialOfferService.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto))
        .thenReturn("offer-123");

    Response response = resource.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto);

    assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    assertNotNull(response.getHeaderString("Location"));
    assertTrue(response.getHeaderString("Location").contains("offer-123"));

    verify(specialOfferService).createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto);
  }

  @Test
  void createSpecialOffer_givenNonExistentRestaurant_shouldThrowNotFoundException() {
    SpecialOfferRequestDto requestDto = fixture.givenValidSpecialOfferRequest();

    when(specialOfferService.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto))
        .thenThrow(NotFoundException.class);

    assertThrows(
        NotFoundException.class,
        () -> resource.createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto));

    verify(specialOfferService).createSpecialOffer(OWNER_ID, RESTAURANT_ID, requestDto);
  }
}
