package ca.ulaval.glo2003.API.Restaurant;

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
        .header(
            "Location",
            String.format("http://localhost:8080/restaurants/%s", restaurantGeneratedId))
        .build();
  }

  @GET
  public Response getRestaurantListOfOwner(@HeaderParam(OWNER_HEADER) String ownerId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", "http://localhost:8080/restaurants/")
        .entity(restaurantService.getRestaurantListOfOwner(ownerId))
        .build();
  }

  @GET
  @Path("/{id}")
  public Response getRestaurantByIdOfOwner(
      @HeaderParam(OWNER_HEADER) String ownerId, @PathParam("id") String restaurantId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", String.format("http://localhost:8080/restaurants/%s", restaurantId))
        .entity(restaurantService.getRestaurantByIdOfOwner(ownerId, restaurantId))
        .build();
  }
}
