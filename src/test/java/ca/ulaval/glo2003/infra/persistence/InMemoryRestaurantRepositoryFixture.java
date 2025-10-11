package ca.ulaval.glo2003.infra.persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class InMemoryRestaurantRepositoryFixture {

  private String ownerId = "ownerId";
  private String restaurantId = "restaurantId";
  private final Restaurant restaurant = mock(Restaurant.class);

  public Restaurant build() {
    when(restaurant.getId()).thenReturn(restaurantId);
    when(restaurant.getOwnerId()).thenReturn(ownerId);
    return restaurant;
  }

  public InMemoryRestaurantRepositoryFixture withOwnerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  public InMemoryRestaurantRepositoryFixture withRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }
}
