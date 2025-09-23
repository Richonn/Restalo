package ca.ulaval.glo2003.api.restaurant;

import ca.ulaval.glo2003.API.Restaurant.Exceptions.MissingParameterException;
import ca.ulaval.glo2003.Application.Restaurant.RestaurantService;
import ca.ulaval.glo2003.DTO.RestaurantDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
  private static final RestaurantService restaurantService = new RestaurantService();
  private static final String OWNER_HEADER = "Owner";

  private static final String BASE_URL = "http://localhost:8080";
  private static final String RESTAURANTS_BASE = BASE_URL + "/restaurants";
  private static final String RESTAURANT_BY_ID = RESTAURANTS_BASE + "/%s";

  private void validateHeaders(String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      throw new MissingParameterException("Missing Owner header");
    }
  }

  @POST
  public Response createRestaurant(
      @HeaderParam(OWNER_HEADER) String ownerId, RestaurantDTO restaurantDTO) {

    validateHeaders(ownerId);

    String restaurantGeneratedId = restaurantService.createRestaurant(ownerId, restaurantDTO);

    return Response.status(Response.Status.CREATED)
        .header("Location", String.format(RESTAURANT_BY_ID, restaurantGeneratedId))
        .build();
  }

  @GET
  public Response getRestaurantListOfOwner(@HeaderParam(OWNER_HEADER) String ownerId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", RESTAURANTS_BASE + "/")
        .entity(restaurantService.getRestaurantListOfOwner(ownerId))
        .build();
  }

  @GET
  @Path("/{id}")
  public Response getRestaurantByIdOfOwner(
      @HeaderParam(OWNER_HEADER) String ownerId, @PathParam("id") String restaurantId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", String.format(RESTAURANT_BY_ID, restaurantId))
        .entity(restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId))
        .build();
  }
}
