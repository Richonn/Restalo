package ca.ulaval.glo2003.Restaurant;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalTime;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantRessource {
  private static final RestaurantRepository repository = new RestaurantRepository();

  @POST
  public Response createRestaurant(RestaurantRequest request) {
    try {
      request.validate();
      Restaurant restaurant =
          new Restaurant(
              request.name,
              request.capacity,
              LocalTime.parse(request.openingHour),
              LocalTime.parse(request.closingHour));
      repository.save(restaurant);
      return Response.status(Response.Status.CREATED).entity(restaurant).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }
}
