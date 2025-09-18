package ca.ulaval.glo2003.Restaurant;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.security.InvalidParameterException;
import java.time.LocalTime;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
    private static final RestaurantRepository repository = new RestaurantRepository();

    @POST
    public Response createRestaurant(RestaurantRequest request) {
        try {
            request.validate();

            Restaurant restaurant = new Restaurant(
                    request.name,
                    request.capacity,
                    LocalTime.parse(request.openingHour),
                    LocalTime.parse(request.closingHour)
            );
            repository.save(restaurant);

            return Response.status(Response.Status.CREATED)
                    .header("Location", String.format("http://localhost:8080/restaurants/%s", restaurant.getId()))
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
}
