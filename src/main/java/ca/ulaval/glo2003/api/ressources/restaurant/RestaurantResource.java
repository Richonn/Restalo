package ca.ulaval.glo2003.api.ressources.restaurant;

import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.dto.request.SearchRestaurantsRequestDto;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
  private final RestaurantService restaurantService;
  private static final String OWNER_HEADER = "Owner";

  private static final String BASE_URL = "http://0.0.0.0:8080";
  private static final String RESTAURANTS_BASE = BASE_URL + "/restaurants";
  private static final String RESTAURANT_BY_ID = RESTAURANTS_BASE + "/%s";

  @Inject
  public RestaurantResource(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @POST
  @Path("restaurants")
  public Response createRestaurant(
      @HeaderParam(OWNER_HEADER) String ownerId, RestaurantDto restaurantDto) {

    String restaurantGeneratedId = restaurantService.createRestaurant(ownerId, restaurantDto);

    return Response.status(Response.Status.CREATED)
        .header("Location", String.format(RESTAURANT_BY_ID, restaurantGeneratedId))
        .build();
  }

  @GET
  @Path("restaurants")
  public Response getRestaurantListOfOwner(@HeaderParam(OWNER_HEADER) String ownerId) {

    return Response.status(Response.Status.OK)
        .header("Location", RESTAURANTS_BASE + "/")
        .entity(restaurantService.getRestaurantListOfOwner(ownerId))
        .build();
  }

  @GET
  @Path("restaurants/{id}")
  public Response getRestaurantByIdOfOwner(
      @HeaderParam(OWNER_HEADER) String ownerId, @PathParam("id") String restaurantId) {

    return Response.status(Response.Status.OK)
        .header("Location", String.format(RESTAURANT_BY_ID, restaurantId))
        .entity(restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId))
        .build();
  }

  @POST
  @Path("/search/restaurants")
  public Response searchRestaurants(SearchRestaurantsRequestDto searchRequest) {
    return Response.status(Response.Status.OK)
        .entity(restaurantService.getRestaurantBySearch(searchRequest))
        .build();
  }
}
