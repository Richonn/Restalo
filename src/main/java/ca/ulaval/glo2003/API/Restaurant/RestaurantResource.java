package ca.ulaval.glo2003.API.Restaurant;

import ca.ulaval.glo2003.API.Restaurant.DTO.RestaurantDTO;
import ca.ulaval.glo2003.API.Restaurant.Exceptions.ErrorResponse;
import ca.ulaval.glo2003.API.Restaurant.Exceptions.MissingParameterException;
import ca.ulaval.glo2003.Domain.Restaurant;
import ca.ulaval.glo2003.Domain.RestaurantRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.List;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
  private static final RestaurantRepository repository = new RestaurantRepository();

  @POST
  public Response createRestaurant(
      @HeaderParam("Owner") String ownerId, RestaurantRequest request) {

    try {

      if (ownerId == null || ownerId.isBlank()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("MISSING_PARAMETER", "Missing Owner header"))
            .build();
      }

      request.validate();

      Restaurant restaurant =
          new Restaurant(
              request.getName(),
              request.getCapacity(),
              LocalTime.parse(request.getOpeningHour()),
              LocalTime.parse(request.getClosingHour()),
              ownerId);

      repository.save(restaurant);

      return Response.status(Response.Status.CREATED)
          .header(
              "Location", String.format("http://localhost:8080/restaurants/%s", restaurant.getId()))
          .entity(new RestaurantResponse(restaurant))
          .build();

    } catch (MissingParameterException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse("MISSING_PARAMETER", e.getMessage()))
          .build();

    } catch (InvalidParameterException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse("INVALID_PARAMETER", e.getMessage()))
          .build();
    }
  }

  @GET
  public Response listRestaurants(@HeaderParam("Owner") String ownerId) {
    if (ownerId == null || ownerId.isBlank()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse("MISSING_PARAMETER", "Missing Owner header"))
          .build();
    }

    try {
      List<RestaurantDTO> restaurantListOfOwner = repository.findRestaurantByOwnerId(ownerId);

      return Response.status(Response.Status.OK)
          .header("Location", "http://localhost:8080/restaurants/")
          .entity(restaurantListOfOwner)
          .build();

    } catch (InvalidParameterException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse("INVALID_PARAMETER", e.getMessage()))
          .build();
    }
  }
}
