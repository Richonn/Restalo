package ca.ulaval.glo2003.api.ressources.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import ca.ulaval.glo2003.api.assembler.RestaurantAssembler;
import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.time.LocalTime;
import org.junit.Test;

public class RestaurantResourceTest {

  private final RestaurantRepository restaurantRepository = new InMemoryRestaurantRepository();
  private final ReservationRepository reservationRepository = new InMemoryReservationRepository();
  private final RestaurantDomainService restaurantDomainService = new RestaurantDomainService();
  private final RestaurantFactory restaurantFactory = new RestaurantFactory();
  private final RestaurantService restaurantService =
      new RestaurantService(
          restaurantRepository, reservationRepository, restaurantDomainService, restaurantFactory);
  private final RestaurantResource resource = new RestaurantResource(restaurantService);
  private String restaurantId;

  private static final String OWNER_ID = "george";
  private static final String NAME = "Chez Mario";
  private static final int CAPACITY = 10;
  private static final LocalTime OPEN_TIME = LocalTime.parse("10:00:00");
  private static final LocalTime CLOSE_TIME = LocalTime.parse("22:00:00");
  private static final int RESERVATION_DURATION = 60;
  private static final String INVALID_RESTAURANT_ID = "123";

  @Test
  public void givenValidRequest_whenPostRestaurant_thenReturns201() {
    Restaurant restaurant =
        new Restaurant(NAME, CAPACITY, OPEN_TIME, CLOSE_TIME, OWNER_ID, RESERVATION_DURATION);
    RestaurantDto restaurantDto = RestaurantAssembler.toRestaurantDto(restaurant);
    Response response = resource.createRestaurant(OWNER_ID, restaurantDto);

    String[] segmentsList = response.getLocation().getPath().split("/");
    restaurantId = segmentsList[segmentsList.length - 1];

    assertEquals(201, response.getStatus());
    assertNotNull(restaurantId);
  }

  @Test
  public void givenValidRequest_whenGetRestaurantList_thenReturns200() {
    Response response = resource.getRestaurantListOfOwner(OWNER_ID);

    assertEquals(200, response.getStatus());
    assertNotNull(response.getEntity());
  }

  @Test
  public void givenValidRequest_whenGetRestaurantById_thenReturns200() {
    givenValidRequest_whenPostRestaurant_thenReturns201();

    Response response = resource.getRestaurantByIdOfOwner(OWNER_ID, restaurantId);

    assertEquals(200, response.getStatus());
    assertNotNull(response.getEntity());
  }

  @Test
  public void givenInvalidRequest_whenGetRestaurantById_thenReturns404() {

    assertThrows(
        NotFoundException.class,
        () -> resource.getRestaurantByIdOfOwner(OWNER_ID, INVALID_RESTAURANT_ID));
  }
}
