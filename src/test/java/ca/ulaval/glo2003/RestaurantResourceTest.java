package ca.ulaval.glo2003;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.assembler.RestaurantAssembler;
import ca.ulaval.glo2003.api.dto.common.RestaurantDto;
import ca.ulaval.glo2003.api.ressources.restaurant.RestaurantResource;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.application.restaurant.TransactionManager;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryTransactionManager;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.time.LocalTime;
import org.junit.Test;

public class RestaurantResourceTest {

  private final RestaurantRepository restaurantRepository = new InMemoryRestaurantRepository();
  private final ReservationRepository reservationRepository = new InMemoryReservationRepository();
  private final RestaurantDomainService restaurantDomainService =
      new RestaurantDomainService(restaurantRepository, reservationRepository);
  private final TransactionManager transactionManager = new InMemoryTransactionManager();
  private final RestaurantService restaurantService =
      new RestaurantService(
          restaurantRepository, reservationRepository, restaurantDomainService, transactionManager);
  private final RestaurantResource resource = new RestaurantResource(restaurantService);
  private String restaurantId;

  @Test
  public void givenValidRequest_whenPostRestaurant_thenReturns201() {
    String ownerId = "george";
    String nom = "Chez Mario";
    int capacity = 10;
    LocalTime open = LocalTime.parse("10:00:00");
    LocalTime close = LocalTime.parse("22:00:00");
    int reservationDuration = 60;

    Restaurant restaurant =
        new Restaurant(nom, capacity, open, close, ownerId, reservationDuration);
    RestaurantDto restaurantDto = RestaurantAssembler.toRestaurantDto(restaurant);
    Response response = resource.createRestaurant(ownerId, restaurantDto);

    String[] segmentsList = response.getLocation().getPath().split("/");
    restaurantId = segmentsList[segmentsList.length - 1];

    assertEquals(201, response.getStatus());
    assertNotNull(restaurantId);
  }

  @Test
  public void givenValidRequest_whenGetRestaurantList_thenReturns200() {
    String ownerId = "george";

    Response response = resource.getRestaurantListOfOwner(ownerId);

    assertEquals(200, response.getStatus());
    assertNotNull(response.getEntity());
  }

  @Test
  public void givenValidRequest_whenGetRestaurantById_thenReturns200() {
    givenValidRequest_whenPostRestaurant_thenReturns201();

    String ownerId = "george";

    Response response = resource.getRestaurantByIdOfOwner(ownerId, restaurantId);

    assertEquals(200, response.getStatus());
    assertNotNull(response.getEntity());
  }

  @Test
  public void givenInvalidRequest_whenGetRestaurantById_thenReturns404() {
    String ownerId = "george";
    String nonExistentRestaurantId = "123";

    assertThrows(
        NotFoundException.class,
        () -> resource.getRestaurantByIdOfOwner(ownerId, nonExistentRestaurantId));
  }
}
