package ca.ulaval.glo2003.api.ressources.restaurant;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestaurantRessourceIntegrationTestFixture {

  private String RESTAURANT_NAME = "Pizza Delamama";
  private int RESTAURANT_CAPACITY = 50;
  private String RESTAURANT_OPEN = "11:00:00";
  private String RESTAURANT_CLOSE = "22:00:00";

  private String SEARCH_NAME = "Pizza";
  private String SEARCH_FROM = "11:00:00";
  private String SEARCH_TO = "14:00:00";
  private Boolean INCLUDE_SEARCH_NAME = true;

  private Boolean INCLUDE_HOURS = true;
  private Boolean INCLUDE_HEADER = true;
  private Boolean INCLUDE_BODY = true;
  private String PATH = "";
  private String OWNER_ID;

  public RestaurantRessourceIntegrationTestFixture(String ownerId) {
    OWNER_ID = ownerId;
  }

  public Response buildPostRestaurantResponse(WebTarget target) {
    return buildPostResponse(target, buildRestaurantJson());
  }

  public Response buildGetResponse(WebTarget target) {
    Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);
    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }
    return requestBuilder.get();
  }

  public Response buildDeleteResponse(WebTarget target) {
    Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);
    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }
    return requestBuilder.delete();
  }

  public Response buildPostSearchResponse(WebTarget target) {
    return buildPostResponse(target, buildSearchJson());
  }

  public RestaurantRessourceIntegrationTestFixture withRestaurantName(String restaurantName) {
    this.RESTAURANT_NAME = restaurantName;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withRestaurantCapacity(int restaurantCapacity) {
    this.RESTAURANT_CAPACITY = restaurantCapacity;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withRestaurantOpen(String restaurantOpen) {
    this.RESTAURANT_OPEN = restaurantOpen;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withRestaurantClose(String restaurantClose) {
    this.RESTAURANT_CLOSE = restaurantClose;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withIncludeHours(boolean includeHours) {
    this.INCLUDE_HOURS = includeHours;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withPath(String path) {
    this.PATH = path;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withOwnerId(String ownerId) {
    this.OWNER_ID = ownerId;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withIncludeHeaders(boolean includeHeaders) {
    this.INCLUDE_HEADER = includeHeaders;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withIncludeBody(boolean includeBody) {
    this.INCLUDE_BODY = includeBody;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withSearchName(String searchName) {
    this.SEARCH_NAME = searchName;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withSearchFrom(String searchFrom) {
    this.SEARCH_FROM = searchFrom;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withSearchTo(String searchTo) {
    this.SEARCH_TO = searchTo;
    return this;
  }

  public RestaurantRessourceIntegrationTestFixture withIncludeSearchName(
      boolean includeSearchName) {
    this.INCLUDE_SEARCH_NAME = includeSearchName;
    return this;
  }

  private Response buildPostResponse(WebTarget target, String json) {
    Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);

    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }

    if (INCLUDE_BODY) {
      return requestBuilder.post(Entity.json(json));
    }

    return requestBuilder.post(Entity.entity("null", MediaType.APPLICATION_JSON_TYPE));
  }

  private String buildSearchJson() {
    String nameJson = "";
    if (INCLUDE_SEARCH_NAME) {
      nameJson =
          """
                    "name": "%s"
            """
              .formatted(SEARCH_NAME);
    }

    String openedHoursJson = "";
    if (INCLUDE_HOURS) {
      if (INCLUDE_SEARCH_NAME) {
        openedHoursJson = ",\n";
      }
      openedHoursJson +=
          """
              "opened": {
                "from": "%s",
                "to": "%s"
              }"""
              .formatted(SEARCH_FROM, SEARCH_TO);
    }
    return """
            {
              %s%s
            }
            """
        .formatted(nameJson, openedHoursJson);
  }

  private String buildRestaurantJson() {
    String hoursJson = "";
    if (INCLUDE_HOURS) {
      hoursJson =
          """
              ,
              "hours": {
                "open": "%s",
                "close": "%s"
              }"""
              .formatted(RESTAURANT_OPEN, RESTAURANT_CLOSE);
    }
    return """
            {
              "name": "%s",
              "capacity": %d%s
            }
            """
        .formatted(RESTAURANT_NAME, RESTAURANT_CAPACITY, hoursJson);
  }
}
