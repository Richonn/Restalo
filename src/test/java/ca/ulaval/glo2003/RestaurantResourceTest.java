package ca.ulaval.glo2003;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.ulaval.glo2003.Restaurant.RestaurantRequest;
import ca.ulaval.glo2003.Restaurant.RestaurantResource;
import ca.ulaval.glo2003.Restaurant.RestaurantResponse;
import jakarta.ws.rs.core.Response;
import org.junit.Test;

public class RestaurantResourceTest {

  private final RestaurantResource resource = new RestaurantResource();

  @Test
  public void givenValidRequest_whenPostRestaurant_thenReturns201() {
    RestaurantRequest request = new RestaurantRequest();
    request.setName("Chez Mario");
    request.setCapacity(10);
    request.setOpeningHour("10:00:00");
    request.setClosingHour("22:00:00");

    String ownerId = "user123";
    Response response = resource.createRestaurant(ownerId, request);

    assertEquals(201, response.getStatus());

    RestaurantResponse body = (RestaurantResponse) response.getEntity();
    assertNotNull(body);
    assertEquals("Chez Mario", body.getName());
    assertEquals(Integer.valueOf(10), body.getCapacity());
    assertEquals("10:00", body.getHours().getOpen());
    assertEquals("22:00", body.getHours().getClose());

    assertNotNull(body.getId());
  }
}
