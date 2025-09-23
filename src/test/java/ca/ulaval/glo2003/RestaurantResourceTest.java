package ca.ulaval.glo2003;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.ulaval.glo2003.api.restaurant.RestaurantResource;
import ca.ulaval.glo2003.domain.Restaurant;
import jakarta.ws.rs.core.Response;
import java.time.LocalTime;
import org.junit.Test;

public class RestaurantResourceTest {

  private final RestaurantResource resource = new RestaurantResource();

  @Test
  public void givenValidRequest_whenPostRestaurant_thenReturns201() {
    String ownerId = "george";
    String nom = "Chez Mario";
    int capacity = 10;
    LocalTime open = LocalTime.parse("10:00:00");
    LocalTime close = LocalTime.parse("22:00:00");

    Restaurant restaurant = new Restaurant(nom, capacity, open, close, ownerId);
    RestaurantDTO restaurantDTO = RestaurantDTOAssembler.toRestaurantDTO(restaurant);
    Response response = resource.createRestaurant(ownerId, restaurantDTO);

    assertEquals(201, response.getStatus());
    assertNotNull(response.getLocation());
  }
}
