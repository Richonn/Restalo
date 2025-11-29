package ca.ulaval.glo2003.api.ressources.specialOffer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.request.SpecialOfferRequestDto;
import ca.ulaval.glo2003.application.specialOffer.SpecialOfferService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialOfferResourceIntegrationTest extends JerseyTest {

  private static final String OWNER_ID = "ownerId";
  private static final String SPECIAL_OFFER_ID = "specialOfferId";
  private static final String RESTAURANT_ID = "restaurantId";
  private static final String CREATE_SPECIAL_OFFER_PATH = "restaurants/%s/special-offers";
  private static final String GET_SPECIAL_OFFER_PATH = "restaurants/%s/%s";

  private static final int HTTP_OK = 200;
  private static final int HTTP_CREATED = 201;
  private static final int HTTP_BAD_REQUEST = 400;
  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_NO_CONTENT = 204;

  private static final String MISSING_PARAMETER_ERROR = "MISSING_PARAMETER";
  private static final String INVALID_PARAMETER_ERROR = "INVALID_PARAMETER";

  private SpecialOfferService specialOfferService;
  private SpecialOfferResourceIntegrationTestFixture specialOfferResourceIntegrationTestFixture;

  @Override
  protected Application configure() {
    specialOfferService = mock(SpecialOfferService.class);

    return new ResourceConfig()
        .register(
            new AbstractBinder() {
              @Override
              protected void configure() {
                bind(specialOfferService).to(SpecialOfferService.class);
              }
            })
        .register(SpecialOfferResource.class)
        .packages("ca.ulaval.glo2003.api.exceptions");
  }

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    specialOfferResourceIntegrationTestFixture =
        new SpecialOfferResourceIntegrationTestFixture(OWNER_ID);
    super.setUp();
  }

  @Test
  void givenValidSpecialOffer_whenSpecialOffer_thenReturns201WithLocation() {
    when(specialOfferService.createSpecialOffer(any(), any(), any())).thenReturn(SPECIAL_OFFER_ID);

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .buildPostSpecialOfferResponse(target())) {
      assertThat(response.getStatus()).isEqualTo(HTTP_CREATED);
      String location = response.getHeaderString("Location");
      assertThat(location).isNotNull();
      assertTrue(
          location.contains(
              String.format(GET_SPECIAL_OFFER_PATH, RESTAURANT_ID, SPECIAL_OFFER_ID)));
      verify(specialOfferService)
          .createSpecialOffer(eq(OWNER_ID), eq(RESTAURANT_ID), any(SpecialOfferRequestDto.class));
    }
  }

  @Test
  public void givenMissingBody_whenCreateSpecialOffer_thenReturns400() {
    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withIncludeBody(false)
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  public void givenMissingOfferBody_whenCreateSpecialOffer_thenReturns400() {
    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withIncludeOfferBody(false)
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  public void givenNoHeaders_whenCreateSpecialOffer_thenReturns400() {

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withIncludeHeaders(false)
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains(MISSING_PARAMETER_ERROR) && body.contains("header"));
    }
  }

  @Test
  public void givenOwnerHeaderOfOwnerWithNoRestaurant_whenCreateSpecialOffer_thenReturns404() {
    String invalidOwnerId = "invalid";

    when(specialOfferService.createSpecialOffer(anyString(), anyString(), any()))
        .thenThrow(new NotFoundException());

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withOwnerId(invalidOwnerId)
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
      verify(specialOfferService)
          .createSpecialOffer(
              eq(invalidOwnerId), eq(RESTAURANT_ID), any(SpecialOfferRequestDto.class));
    }
  }

  @Test
  public void givenRestaurantIdDoesntExist_whenCreateSpecialOffer_thenReturns404() {
    String invalidRestaurantId = "invalid";

    when(specialOfferService.createSpecialOffer(anyString(), anyString(), any()))
        .thenThrow(new NotFoundException());

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, invalidRestaurantId))
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
      verify(specialOfferService)
          .createSpecialOffer(
              eq(OWNER_ID), eq(invalidRestaurantId), any(SpecialOfferRequestDto.class));
    }
  }

  @Test
  public void givenBlankTitle_whenCreateSpecialOffer_thenReturns400() {

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withTitle("")
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains(MISSING_PARAMETER_ERROR) && body.contains("title"));
    }
  }

  @Test
  public void givenBlankValidFrom_whenCreateSpecialOffer_thenReturns400() {

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withValidFrom("")
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains(INVALID_PARAMETER_ERROR) && body.contains("invalid"));
    }
  }

  @Test
  public void givenInvalidDateFormat_whenCreateSpecialOffer_thenReturns400() {

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withValidUntil("invalid")
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains(INVALID_PARAMETER_ERROR) && body.contains("invalid"));
    }
  }

  @Test
  public void givenBlankDescription_whenCreateSpecialOffer_thenReturns400() {

    try (Response response =
        specialOfferResourceIntegrationTestFixture
            .withPath(String.format(CREATE_SPECIAL_OFFER_PATH, RESTAURANT_ID))
            .withDescription("")
            .buildPostSpecialOfferResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains(MISSING_PARAMETER_ERROR) && body.contains("description"));
    }
  }
}
