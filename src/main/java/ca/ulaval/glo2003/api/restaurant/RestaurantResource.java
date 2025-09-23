package ca.ulaval.glo2003.api.restaurant;

import ca.ulaval.glo2003.api.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.dto.RestaurantDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
  private static final RestaurantService restaurantService = new RestaurantService();

  @POST
  public Response createRestaurant(
      @HeaderParam("Owner") String ownerId, RestaurantDto restaurantDto) {

    String restaurantGeneratedId = restaurantService.createRestaurant(ownerId, restaurantDto);

    return Response.status(Response.Status.CREATED)
        .header(
            "Location",
            String.format("http://localhost:8080/restaurants/%s", restaurantGeneratedId))
        .build();
  }

  @GET
  public Response listRestaurants(@HeaderParam("Owner") String ownerId) {

    return Response.status(Response.Status.OK)
        .header("Location", "http://localhost:8080/restaurants/")
        .entity(restaurantService.findAllRestaurantsByOwnerId(ownerId))
        .build();
  }

  @GET
  @Path("/{id}")
  public Response listRestaurants(
      @HeaderParam("Owner") String ownerId, @PathParam("id") String restaurantId) {

    return Response.status(Response.Status.OK)
        .header("Location", String.format("http://localhost:8080/restaurants/%s", restaurantId))
        .entity(restaurantService.findRestaurantByOwnerIdAndRestaurantId(ownerId, restaurantId))
        .build();
  }
}
