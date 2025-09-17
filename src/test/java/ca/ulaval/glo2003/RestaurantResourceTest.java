package ca.ulaval.glo2003;

import ca.ulaval.glo2003.Restaurant.RestaurantRequest;
import ca.ulaval.glo2003.Restaurant.RestaurantResource;
import jakarta.ws.rs.core.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestaurantResourceTest {

    private final RestaurantResource resource = new RestaurantResource();

    @Test
    public void givenValidRequest_whenPostRestaurant_thenReturns201() {
        RestaurantRequest request = new RestaurantRequest();
        request.name = "Chez Mario";
        request.capacity = 10;
        request.openingHour = "10:00:00";
        request.closingHour = "22:00:00";

        Response response = resource.createRestaurant(request);

        assertEquals(201, response.getStatus());
    }
}
