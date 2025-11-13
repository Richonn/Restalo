package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryRestaurantRepositoryTest extends AbstractRestaurantRepositoryTest {

  private RestaurantRepository restaurantRepository;

  @BeforeEach
  public void setUp() {
    restaurantRepository = new InMemoryRestaurantRepository();
  }

  @Override
  protected RestaurantRepository getRepository() {
    return restaurantRepository;
  }

  @Override
  protected void clearRepository() {
    restaurantRepository = new InMemoryRestaurantRepository();
  }
}
