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

  private static final String TEST_RESTAURANT_ID = "restaurant-123";
  private static final String NONEXISTENT_ID = "nonexistent";
  private static final String TEST_OWNER_ID = "yvick";
  private static final String TEST_RESTAURANT_NAME = "Pizza Delamama";
  private static final String TEST_SEARCH_NAME = "Pizza";
  private static final int TEST_CAPACITY = 50;
  private static final int ZERO_CAPACITY = 0;
  private static final String OPENING_TIME = "11:00:00";
  private static final String CLOSING_TIME = "22:00:00";
  private static final String LUNCH_START = "11:00:00";
  private static final String LUNCH_END = "14:00:00";
  private static final String LATE_OPENING = "22:00:00";
  private static final String EARLY_CLOSING = "11:00:00";
  private static final int HTTP_OK = 200;
  private static final int HTTP_CREATED = 201;
  private static final int HTTP_BAD_REQUEST = 400;
  private static final int HTTP_NOT_FOUND = 404;

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
    when(restaurantService.createRestaurant(any(), any())).thenReturn(TEST_RESTAURANT_ID);

    String restaurantJson =
        createRestaurantJson(TEST_RESTAURANT_NAME, TEST_CAPACITY, OPENING_TIME, CLOSING_TIME);

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", TEST_OWNER_ID)
            .post(Entity.json(restaurantJson))) {

      assertEquals(HTTP_CREATED, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/restaurants/restaurant-123"));

      verify(restaurantService).createRestaurant(eq(TEST_OWNER_ID), any(RestaurantDto.class));
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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
            .header("Owner", TEST_OWNER_ID)
            .post(Entity.json(null))) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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
            .header("Owner", TEST_OWNER_ID)
            .post(Entity.json(restaurantJson))) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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
            .header("Owner", TEST_OWNER_ID)
            .post(Entity.json(restaurantJson))) {

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
            .header("Owner", TEST_OWNER_ID)
            .post(Entity.json(restaurantJson))) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("capacity"));
    }
  }

  @Test
  public void givenValidOwner_whenGetList_thenReturns200WithList() {
    when(restaurantService.getRestaurantListOfOwner(any())).thenReturn(Collections.emptyList());

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", TEST_OWNER_ID)
            .get()) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());

      verify(restaurantService).getRestaurantListOfOwner(eq(TEST_OWNER_ID));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenGetList_thenReturns400() {
    when(restaurantService.getRestaurantListOfOwner(isNull()))
        .thenThrow(new MissingParameterException("Owner header is required"));

    try (Response response = target("/restaurants").request(MediaType.APPLICATION_JSON).get()) {

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
        target("/restaurants/restaurant-123")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", TEST_OWNER_ID)
            .get()) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());

      verify(restaurantService).getRestaurantByIdOfOwner(eq(TEST_OWNER_ID), eq(TEST_RESTAURANT_ID));
    }
  }

  @Test
  public void givenMissingOwnerHeader_whenGet_thenReturns400() {
    when(restaurantService.getRestaurantByIdOfOwner(isNull(), any()))
        .thenThrow(new MissingParameterException("Owner header is required"));

    try (Response response =
        target("/restaurants/restaurant-123").request(MediaType.APPLICATION_JSON).get()) {

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
        target("/restaurants/nonexistent")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", TEST_OWNER_ID)
            .get()) {

      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  public void givenValidSearchRequest_whenSearchRestaurants_thenReturns200WithList() {
    List<RestaurantSearchResponseDto> emptyRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(emptyRestaurants);

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

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
      verify(restaurantService).getRestaurantBySearch(any(SearchRestaurantsRequestDto.class));
    }
  }

  @Test
  public void givenSearchRequestWithNullName_whenSearchRestaurants_thenReturns200() {
    List<RestaurantSearchResponseDto> emptyRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(emptyRestaurants);

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

      assertEquals(HTTP_OK, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER"));
    }
  }

  @Test
  public void givenEmptySearchRequest_whenSearchRestaurants_thenReturns200() {
    List<RestaurantSearchResponseDto> emptyRestaurants = Collections.emptyList();
    when(restaurantService.getRestaurantBySearch(any())).thenReturn(emptyRestaurants);

    String searchJson = "{}";

    try (Response response =
        target("/search/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(searchJson))) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  public void givenNullBody_whenSearchRestaurants_thenReturns400() {
    when(restaurantService.getRestaurantBySearch(isNull()))
        .thenThrow(new MissingParameterException("Missing body"));

    try (Response response =
        target("/search/restaurants").request(MediaType.APPLICATION_JSON).post(Entity.json(null))) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  public void
      givenSearchRequestWithMultipleResults_whenSearchRestaurants_thenReturns200WithResults() {
    RestaurantSearchResponseDto restaurant1 = mock(RestaurantSearchResponseDto.class);
    RestaurantSearchResponseDto restaurant2 = mock(RestaurantSearchResponseDto.class);
    List<RestaurantSearchResponseDto> restaurants = List.of(restaurant1, restaurant2);

    when(restaurantService.getRestaurantBySearch(any())).thenReturn(restaurants);

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

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.getEntity());
      verify(restaurantService).getRestaurantBySearch(any(SearchRestaurantsRequestDto.class));
    }
  }

  private String createRestaurantJson(String name, int capacity, String open, String close) {
    return String.format(
        """
        {
          "name": "%s",
          "capacity": %d,
          "hours": {
            "open": "%s",
            "close": "%s"
          }
        }
        """,
        name, capacity, open, close);
  }

  private String createRestaurantJsonWithoutHours(String name, int capacity) {
    return String.format(
        """
        {
          "name": "%s",
          "capacity": %d
        }
        """,
        name, capacity);
  }

  private String createSearchJson(String name, String from, String to) {
    return String.format(
        """
        {
          "name": "%s",
          "opened": {
            "from": "%s",
            "to": "%s"
          }
        }
        """,
        name, from, to);
  }

  private String createSearchJsonWithoutName(String from, String to) {
    return String.format(
        """
        {
          "opened": {
            "from": "%s",
            "to": "%s"
          }
        }
        """,
        from, to);
  }
}
