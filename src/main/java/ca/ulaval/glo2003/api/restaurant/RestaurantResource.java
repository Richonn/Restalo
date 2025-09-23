package ca.ulaval.glo2003.API.Restaurant;

import ca.ulaval.glo2003.API.Application.Restaurant.RestaurantService;
import ca.ulaval.glo2003.API.Restaurant.Exceptions.MissingParameterException;
import ca.ulaval.glo2003.Dto.RestaurantDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/** Restaurant routes. */
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

  /**
   * Route POST /restaurant.
   *
   * @param ownerId The id of the owner of the restaurant
   * @param restaurantDto The restaurant to be created
   * @return The response of the request
   */
  @POST
  public Response createRestaurant(
      @HeaderParam("Owner") String ownerId, RestaurantDto restaurantDto) {

    validateHeaders(ownerId);

    String restaurantGeneratedId = restaurantService.createRestaurant(ownerId, restaurantDto);

    return Response.status(Response.Status.CREATED)
        .header(
            "Location",
            String.format("http://localhost:8080/restaurants/%s", restaurantGeneratedId))
        .build();
  }

  /**
   * Route GET /restaurant.
   *
   * @param ownerId The id of the owner of the restaurant
   * @return The response of the request
   */
  @GET
  public Response listRestaurants(@HeaderParam("Owner") String ownerId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", "http://localhost:8080/restaurants/")
        .entity(restaurantService.findAllRestaurantsByOwnerId(ownerId))
        .build();
  }

  /**
   * Route GET /restaurant/{id}.
   *
   * @param ownerId The id of the owner of the restaurant
   * @param restaurantId The id of the restaurant
   * @return The response of the request
   */
  @GET
  @Path("/{id}")
  public Response listRestaurants(
      @HeaderParam("Owner") String ownerId, @PathParam("id") String restaurantId) {

    validateHeaders(ownerId);

    return Response.status(Response.Status.OK)
        .header("Location", String.format("http://localhost:8080/restaurants/%s", restaurantId))
        .entity(restaurantService.findRestaurantByOwnerIdAndRestaurantId(ownerId, restaurantId))
        .build();
  }
}
