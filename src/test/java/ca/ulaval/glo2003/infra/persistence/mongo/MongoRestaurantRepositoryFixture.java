package ca.ulaval.glo2003.infra.persistence.mongo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class MongoRestaurantRepositoryFixture {

  private String OWNER_ID = "ownerId";
  private String RESTAURANT_ID = "restaurantId";
  private String NAME = "Test Restaurant";
  private Integer CAPACITY = 50;
  private String OPENING_HOUR = "10:00";
  private String CLOSING_HOUR = "22:00";
  private Integer RESERVATION_DURATION = 60;
  private final Restaurant RESTAURANT = mock(Restaurant.class);

  public Restaurant build() {
    when(RESTAURANT.getId()).thenReturn(RESTAURANT_ID);
    when(RESTAURANT.getOwnerId()).thenReturn(OWNER_ID);
    when(RESTAURANT.getName()).thenReturn(NAME);
    when(RESTAURANT.getCapacity()).thenReturn(CAPACITY);
    when(RESTAURANT.getReservationLength()).thenReturn(RESERVATION_DURATION);
    return RESTAURANT;
  }

  public MongoRestaurantRepositoryFixture withOwnerId(String ownerId) {
    this.OWNER_ID = ownerId;
    return this;
  }

  public MongoRestaurantRepositoryFixture withRestaurantId(String restaurantId) {
    this.RESTAURANT_ID = restaurantId;
    return this;
  }

  public MongoRestaurantRepositoryFixture withName(String name) {
    this.NAME = name;
    return this;
  }

  public MongoRestaurantRepositoryFixture withCapacity(Integer capacity) {
    this.CAPACITY = capacity;
    return this;
  }

  public MongoRestaurantRepositoryFixture withReservationDuration(Integer duration) {
    this.RESERVATION_DURATION = duration;
    return this;
  }
}
