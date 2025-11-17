package ca.ulaval.glo2003.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.request.SearchRestaurantsRequestDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.api.ressources.restaurant.RestaurantResource;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantResourceIntegrationTest extends JerseyTest {

  private static final String TEST_RESTAURANT_ID = "restaurant-123";
  private static final String TEST_OWNER_ID = "yvick";

  private static final int HTTP_OK = 200;
  private static final int HTTP_CREATED = 201;
  private static final int HTTP_BAD_REQUEST = 400;
  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_NO_CONTENT = 204;

  private RestaurantService restaurantService;
  private RestaurantRessourceIntegrationTestFixture restaurantRessourceIntegrationTestFixture;

  @Override
  protected Application configure() {
    restaurantService = mock(RestaurantService.class);

    return new ResourceConfig()
        .register(
            new AbstractBinder() {
              @Override
              protected void configure() {
                bind(restaurantService).to(RestaurantService.class);
              }
            })
        .register(RestaurantResource.class)
        .packages("ca.ulaval.glo2003.api.exceptions");
  }

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    restaurantRessourceIntegrationTestFixture =
        new RestaurantRessourceIntegrationTestFixture(TEST_OWNER_ID);
    super.setUp();
  }

  @Test
  public void givenValidRestaurant_whenPost_thenReturns201WithLocation() {
    when(restaurantService.createRestaurant(any(), any())).thenReturn(TEST_RESTAURANT_ID);

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .buildPostRestaurantResponse(target())) {
      assertEquals(HTTP_CREATED, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/restaurants/restaurant-123"));
      verify(restaurantService).createRestaurant(eq(TEST_OWNER_ID), any(RestaurantDto.class));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenPost_thenReturns400() {

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .withIncludeHeaders(false)
            .buildPostRestaurantResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenMissingBody_whenPost_thenReturns400() {
    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .withIncludeBody(false)
            .buildPostRestaurantResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  public void givenMissingHours_whenPost_thenReturns400() {

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .withIncludeHours(false)
            .buildPostRestaurantResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenInvalidName_whenPost_thenReturns400() {

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .withRestaurantName("")
            .buildPostRestaurantResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("name"));
    }
  }

  @Test
  public void givenInvalidCapacity_whenPost_thenReturns400() {
    when(restaurantService.createRestaurant(any(), any()))
        .thenThrow(
            new InvalidParameterException("The capacity of the restaurant must be positive"));

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .withRestaurantCapacity(0)
            .buildPostRestaurantResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("capacity"));
    }
  }

  @Test
  public void givenValidOwner_whenGetList_thenReturns200WithList() {
    when(restaurantService.getRestaurantListOfOwner(any())).thenReturn(Collections.emptyList());

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .buildGetResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());

      verify(restaurantService).getRestaurantListOfOwner(eq(TEST_OWNER_ID));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenGetList_thenReturns400() {
    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants")
            .withIncludeHeaders(false)
            .buildGetResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenValidOwnerAndId_whenGet_thenReturns200WithRestaurant() {
    RestaurantDto restaurantDto = mock(RestaurantDto.class);
    when(restaurantService.getRestaurantByIdOfOwner(any(), any())).thenReturn(restaurantDto);

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants/restaurant-123")
            .buildGetResponse(target())) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());

      verify(restaurantService).getRestaurantByIdOfOwner(eq(TEST_OWNER_ID), eq(TEST_RESTAURANT_ID));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenGet_thenReturns400() {
    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants/restaurant-123")
            .withIncludeHeaders(false)
            .buildGetResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenNonExistentRestaurant_whenGet_thenReturns404() {
    when(restaurantService.getRestaurantByIdOfOwner(any(), any()))
        .thenThrow(new NotFoundException());

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants/nonexistent")
            .buildGetResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  public void givenValidSearchRequest_whenSearchRestaurants_thenReturns200WithList() {
    List<RestaurantSearchResponseDto> emptyRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(emptyRestaurants);

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/search/restaurants")
            .buildPostSearchResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
      verify(restaurantService).getRestaurantBySearch(any(SearchRestaurantsRequestDto.class));
    }
  }

  @Test
  public void givenSearchRequestWithNullName_whenSearchRestaurants_thenReturns200() {
    List<RestaurantSearchResponseDto> emptyRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(emptyRestaurants);

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/search/restaurants")
            .withIncludeSearchName(false)
            .buildPostSearchResponse(target())) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  public void givenInvalidTimeRange_whenSearchRestaurants_thenReturns400() {
    when(restaurantService.getRestaurantBySearch(any()))
        .thenThrow(new InvalidParameterException("Closing hour cannot be before opening hours"));

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/search/restaurants")
            .withSearchFrom("22:00:00")
            .withSearchTo("11:00:00")
            .buildPostSearchResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER"));
    }
  }

  @Test
  public void givenEmptySearchRequest_whenSearchRestaurants_thenReturns200() {
    List<RestaurantSearchResponseDto> emptyRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(emptyRestaurants);

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/search/restaurants")
            .withIncludeSearchName(false)
            .withIncludeHours(false)
            .buildPostSearchResponse(target())) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  public void givenNullBody_whenSearchRestaurants_thenReturns400() {
    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/search/restaurants")
            .withIncludeBody(false)
            .buildPostSearchResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void
      givenSearchRequestWithMultipleResults_whenSearchRestaurants_thenReturns200WithResults() {
    RestaurantSearchResponseDto restaurant1 = mock(RestaurantSearchResponseDto.class);
    RestaurantSearchResponseDto restaurant2 = mock(RestaurantSearchResponseDto.class);
    List<RestaurantSearchResponseDto> restaurants = List.of(restaurant1, restaurant2);

    when(restaurantService.getRestaurantBySearch(any())).thenReturn(restaurants);

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/search/restaurants")
            .buildPostSearchResponse(target())) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
      verify(restaurantService).getRestaurantBySearch(any(SearchRestaurantsRequestDto.class));
    }
  }

  @Test
  public void givenValidOwnerAndRestaurantId_whenDeleteRestaurant_thenReturns204NoContent() {
    doNothing().when(restaurantService).deleteRestaurant(any(), any());

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants/restaurant-123")
            .buildDeleteResponse(target())) {

      assertEquals(HTTP_NO_CONTENT, response.getStatus());
      verify(restaurantService).deleteRestaurant(eq(TEST_OWNER_ID), eq(TEST_RESTAURANT_ID));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenDeleteRestaurant_thenReturns400() {
    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants/restaurant-123")
            .withIncludeHeaders(false)
            .buildDeleteResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenNonExistentRestaurant_whenDeleteRestaurant_thenReturns404() {
    doThrow(new NotFoundException()).when(restaurantService).deleteRestaurant(any(), any());

    try (Response response =
        restaurantRessourceIntegrationTestFixture
            .withPath("/restaurants/nonexistent")
            .buildDeleteResponse(target())) {

      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }
}
