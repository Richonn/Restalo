package ca.ulaval.glo2003.API.Restaurant;

import ca.ulaval.glo2003.API.Application.Restaurant.RestaurantService;
import ca.ulaval.glo2003.API.Restaurant.Exceptions.MissingParameterException;
import ca.ulaval.glo2003.DTO.RestaurantDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
  private static final RestaurantService restaurantService = new RestaurantService();

  private void validateHeaders(String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      throw new MissingParameterException("Missing Owner header");
    }
  }

  @POST
  public Response createRestaurant(
      @HeaderParam("Owner") String ownerId, RestaurantDTO restaurantDTO) {

    validateHeaders(ownerId);

    String restaurantGeneratedId = restaurantService.createRestaurant(ownerId, restaurantDTO);

    return Response.status(Response.Status.CREATED)
        .header(
            "Location",
            String.format("http://localhost:8080/restaurants/%s", restaurantGeneratedId))
        .build();
  }

  @GET
  public Response listRestaurants(@HeaderParam("Owner") String ownerId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", "http://localhost:8080/restaurants/")
        .entity(restaurantService.findAllRestaurantsByOwnerId(ownerId))
        .build();
  }
}
