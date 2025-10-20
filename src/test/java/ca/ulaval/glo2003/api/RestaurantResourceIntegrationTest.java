package ca.ulaval.glo2003.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.request.SearchRestaurantsRequestDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchResponseDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.api.ressources.restaurant.RestaurantResource;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantResourceIntegrationTest extends JerseyTest {

  private RestaurantService restaurantService;

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
    super.setUp();
  }

  @Test
  public void givenValidRestaurant_whenPost_thenReturns201WithLocation() {
    when(restaurantService.createRestaurant(any(), any())).thenReturn("restaurant-123");

    String restaurantJson =
        """
        {
          "name": "Pizza Delamama",
          "capacity": 50,
          "hours": {
            "open": "11:00:00",
            "close": "22:00:00"
          }
        }
        """;

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .post(Entity.json(restaurantJson))) {

      assertEquals(201, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/restaurants/restaurant-123"));

      verify(restaurantService).createRestaurant(eq("yvick"), any(RestaurantDto.class));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenPost_thenReturns400() {
    when(restaurantService.createRestaurant(isNull(), any()))
        .thenThrow(new MissingParameterException("Owner header is required"));

    String restaurantJson =
        """
        {
          "name": "Pizza Delamama",
          "capacity": 50,
          "hours": {
            "open": "11:00:00",
            "close": "22:00:00"
          }
        }
        """;

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(restaurantJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenMissingBody_whenPost_thenReturns400() {
    when(restaurantService.createRestaurant(any(), isNull()))
        .thenThrow(new MissingParameterException("Missing body"));

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .post(Entity.json(null))) {

      assertEquals(400, response.getStatus());
    }
  }

  @Test
  public void givenMissingHours_whenPost_thenReturns400() {
    when(restaurantService.createRestaurant(any(), any()))
        .thenThrow(new MissingParameterException("Missing hours body"));

    String restaurantJson =
        """
        {
          "name": "Pizza Delamama",
          "capacity": 50
        }
        """;

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .post(Entity.json(restaurantJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenInvalidName_whenPost_thenReturns400() {
    when(restaurantService.createRestaurant(any(), any()))
        .thenThrow(new InvalidParameterException("The name of the restaurant cannot be blank"));

    String restaurantJson =
        """
        {
          "name": "",
          "capacity": 50,
          "hours": {
            "open": "11:00:00",
            "close": "22:00:00"
          }
        }
        """;

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .post(Entity.json(restaurantJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("name"));
    }
  }

  @Test
  public void givenInvalidCapacity_whenPost_thenReturns400() {
    when(restaurantService.createRestaurant(any(), any()))
        .thenThrow(
            new InvalidParameterException("The capacity of the restaurant must be positive"));

    String restaurantJson =
        """
        {
          "name": "Pizza Delamama",
          "capacity": 0,
          "hours": {
            "open": "11:00:00",
            "close": "22:00:00"
          }
        }
        """;

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .post(Entity.json(restaurantJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("capacity"));
    }
  }

  @Test
  public void givenValidOwner_whenGetList_thenReturns200WithList() {
    when(restaurantService.getRestaurantListOfOwner(any())).thenReturn(Collections.emptyList());

    try (Response response =
        target("/restaurants").request(MediaType.APPLICATION_JSON).header("Owner", "yvick").get()) {

      assertEquals(200, response.getStatus());
      assertNotNull(response.getEntity());

      verify(restaurantService).getRestaurantListOfOwner(eq("yvick"));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenGetList_thenReturns400() {
    when(restaurantService.getRestaurantListOfOwner(isNull()))
        .thenThrow(new MissingParameterException("Owner header is required"));

    try (Response response = target("/restaurants").request(MediaType.APPLICATION_JSON).get()) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenValidOwnerAndId_whenGet_thenReturns200WithRestaurant() {
    RestaurantDto mockDto = mock(RestaurantDto.class);
    when(restaurantService.getRestaurantByIdOfOwner(any(), any())).thenReturn(mockDto);

    try (Response response =
        target("/restaurants/restaurant-123")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .get()) {

      assertEquals(200, response.getStatus());
      assertNotNull(response.getEntity());

      verify(restaurantService).getRestaurantByIdOfOwner(eq("yvick"), eq("restaurant-123"));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenGet_thenReturns400() {
    when(restaurantService.getRestaurantByIdOfOwner(isNull(), any()))
        .thenThrow(new MissingParameterException("Owner header is required"));

    try (Response response =
        target("/restaurants/restaurant-123").request(MediaType.APPLICATION_JSON).get()) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  public void givenNonExistentRestaurant_whenGet_thenReturns404() {
    when(restaurantService.getRestaurantByIdOfOwner(any(), any()))
        .thenThrow(new NotFoundException());

    try (Response response =
        target("/restaurants/nonexistent")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", "yvick")
            .get()) {

      assertEquals(404, response.getStatus());
    }
  }

  @Test
  public void givenValidSearchRequest_whenSearchRestaurants_thenReturns200WithList() {
    List<RestaurantSearchResponseDto> mockRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(mockRestaurants);

    String searchJson =
        """
                {
                  "name": "Pizza",
                  "opened": {
                    "from": "11:00:00",
                    "to": "14:00:00"
                  }
                }
                """;

    try (Response response =
        target("/search/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(searchJson))) {

      assertEquals(200, response.getStatus());
      assertNotNull(response.getEntity());
      verify(restaurantService).getRestaurantBySearch(any(SearchRestaurantsRequestDto.class));
    }
  }

  @Test
  public void givenSearchRequestWithNullName_whenSearchRestaurants_thenReturns200() {
    List<RestaurantSearchResponseDto> mockRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(mockRestaurants);

    String searchJson =
        """
                {
                  "opened": {
                    "from": "11:00:00",
                    "to": "14:00:00"
                  }
                }
                """;

    try (Response response =
        target("/search/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(searchJson))) {

      assertEquals(200, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  public void givenInvalidTimeRange_whenSearchRestaurants_thenReturns400() {
    when(restaurantService.getRestaurantBySearch(any()))
        .thenThrow(new InvalidParameterException("Closing hour cannot be before opening hours"));

    String searchJson =
        """
                {
                  "name": "Pizza",
                  "opened": {
                    "from": "22:00:00",
                    "to": "11:00:00"
                  }
                }
                """;

    try (Response response =
        target("/search/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(searchJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER"));
    }
  }

  @Test
  public void givenEmptySearchRequest_whenSearchRestaurants_thenReturns200() {
    List<RestaurantSearchResponseDto> mockRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(mockRestaurants);

    String searchJson = "{}";

    try (Response response =
        target("/search/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(searchJson))) {

      assertEquals(200, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  public void givenNullBody_whenSearchRestaurants_thenReturns400() {
    when(restaurantService.getRestaurantBySearch(isNull()))
        .thenThrow(new MissingParameterException("Missing body"));

    try (Response response =
        target("/search/restaurants").request(MediaType.APPLICATION_JSON).post(Entity.json(null))) {

      assertEquals(400, response.getStatus());
    }
  }

  @Test
  public void
      givenSearchRequestWithMultipleResults_whenSearchRestaurants_thenReturns200WithResults() {
    RestaurantSearchResponseDto restaurant1 = mock(RestaurantSearchResponseDto.class);
    RestaurantSearchResponseDto restaurant2 = mock(RestaurantSearchResponseDto.class);
    List<RestaurantSearchResponseDto> mockRestaurants = List.of(restaurant1, restaurant2);

    when(restaurantService.getRestaurantBySearch(any())).thenReturn(mockRestaurants);

    String searchJson =
        """
                {
                  "name": "Pizza",
                  "opened": {
                    "from": "11:00:00",
                    "to": "22:00:00"
                  }
                }
                """;

    try (Response response =
        target("/search/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(searchJson))) {

      assertEquals(200, response.getStatus());
      assertNotNull(response.getEntity());
      verify(restaurantService).getRestaurantBySearch(any(SearchRestaurantsRequestDto.class));
    }
  }
}
