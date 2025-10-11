package ca.ulaval.glo2003.infra.persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class InMemoryRestaurantRepositoryFixture {

  private String OWNER_ID = "ownerId";
  private String RESTAURANT_ID = "restaurantId";
  private final Restaurant RESTAURANT = mock(Restaurant.class);

  public Restaurant build() {
    when(RESTAURANT.getId()).thenReturn(RESTAURANT_ID);
    when(RESTAURANT.getOwnerId()).thenReturn(OWNER_ID);
    return RESTAURANT;
  }

  public InMemoryRestaurantRepositoryFixture withOwnerId(String ownerId) {
    this.OWNER_ID = ownerId;
    return this;
  }

  public InMemoryRestaurantRepositoryFixture withRestaurantId(String restaurantId) {
    this.RESTAURANT_ID = restaurantId;
    return this;
  }
}
