package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public abstract class AbstractRestaurantRepositoryTest {

  private static final int DEFAULT_CAPACITY = 50;
  private static final int OPENING_HOUR = 10;
  private static final int OPENING_MINUTE = 0;
  private static final int CLOSING_HOUR = 22;
  private static final int CLOSING_MINUTE = 0;
  private static final int RESERVATION_DURATION_MINUTES = 60;
  private static final int EXPECTED_RESTAURANT_COUNT = 3;

  protected abstract RestaurantRepository getRepository();

  protected abstract void clearRepository();

  protected Restaurant createRestaurant(String id, String ownerId) {
    return new Restaurant(
        "Test Restaurant " + id,
        DEFAULT_CAPACITY,
        LocalTime.of(OPENING_HOUR, OPENING_MINUTE),
        LocalTime.of(CLOSING_HOUR, CLOSING_MINUTE),
        ownerId,
        RESERVATION_DURATION_MINUTES);
  }

  @Test
  void givenValidRestaurant_whenSaveRestaurant_thenNoException() {
    Restaurant restaurant = createRestaurant("1", "owner1");

    assertDoesNotThrow(() -> getRepository().save(restaurant));
  }

  @Test
  void givenExistingOwnerId_whenGetRestaurantListOfOwner_thenReturnExpectedRestaurants() {
    clearRepository();

    String ownerId = "ownerId";
    String[] restaurantIdList = {"1", "2", "3"};
    List<Restaurant> expectedRestaurantList = new ArrayList<>();

    for (String restaurantId : restaurantIdList) {
      Restaurant restaurant = createRestaurant(restaurantId, ownerId);
      getRepository().save(restaurant);
      expectedRestaurantList.add(restaurant);
    }

    List<Restaurant> result = getRepository().getRestaurantListOfOwner(ownerId);

    assertThat(result).hasSize(EXPECTED_RESTAURANT_COUNT);
    assertThat(result.stream().map(Restaurant::getId).toList())
        .containsExactlyElementsIn(expectedRestaurantList.stream().map(Restaurant::getId).toList());
  }

  @Test
  void givenNonExistentOwnerId_whenGetRestaurantListOfOwner_thenReturnNoRestaurants() {
    clearRepository();

    String actualOwnerId = "ownerId";
    String requestedOwnerId = "NonExistentOwnerId";
    String[] restaurantIdList = {"1", "2", "3"};

    for (String restaurantId : restaurantIdList) {
      Restaurant restaurant = createRestaurant(restaurantId, actualOwnerId);
      getRepository().save(restaurant);
    }

    assertThat(getRepository().getRestaurantListOfOwner(requestedOwnerId)).isEmpty();
  }

  @Test
  void givenExistingRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnRestaurant() {
    clearRepository();

    String ownerId = "ownerId";
    Restaurant restaurant = createRestaurant("1", ownerId);
    getRepository().save(restaurant);
    String restaurantId = restaurant.getId();

    Restaurant result = getRepository().getRestaurantByIdOfOwner(ownerId, restaurantId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(restaurantId);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnNoRestaurant() {
    clearRepository();

    String ownerId = "ownerId";
    String actualRestaurantId = "restaurantId";
    String requestedRestaurantId = "nonExistentRestaurantId";

    Restaurant restaurant = createRestaurant(actualRestaurantId, ownerId);
    getRepository().save(restaurant);

    assertThat(getRepository().getRestaurantByIdOfOwner(ownerId, requestedRestaurantId)).isNull();
  }

  @Test
  void givenExistingRestaurant_whenGetRestaurantList_thenReturnExpectedRestaurantsList() {
    clearRepository();

    String[] restaurantIdList = {"1", "2", "3"};
    String[] ownerIdList = {"1", "2", "3"};
    List<Restaurant> expectedRestaurantList = new ArrayList<>();

    for (int i = 0; i < restaurantIdList.length; i++) {
      Restaurant restaurant = createRestaurant(restaurantIdList[i], ownerIdList[i]);
      getRepository().save(restaurant);
      expectedRestaurantList.add(restaurant);
    }

    List<Restaurant> result = getRepository().getRestaurantList();

    assertThat(result).hasSize(EXPECTED_RESTAURANT_COUNT);
    assertThat(result.stream().map(Restaurant::getId).toList())
        .containsExactlyElementsIn(expectedRestaurantList.stream().map(Restaurant::getId).toList());
  }

  @Test
  void givenNoExistingRestaurant_whenGetRestaurantList_thenReturnEmptyList() {
    clearRepository();

    assertThat(getRepository().getRestaurantList()).isEmpty();
  }

  @Test
  void givenExistingRestaurantId_whenGetRestaurantById_thenReturnRestaurant() {
    clearRepository();

    Restaurant restaurant = createRestaurant("1", "owner1");
    getRepository().save(restaurant);
    String restaurantId = restaurant.getId();

    Restaurant result = getRepository().getRestaurantById(restaurantId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(restaurantId);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetRestaurantById_thenReturnNoRestaurant() {
    clearRepository();

    String actualRestaurantId = "restaurantId";
    String requestedRestaurantId = "nonExistentRestaurantId";

    Restaurant restaurant = createRestaurant(actualRestaurantId, "owner1");
    getRepository().save(restaurant);

    assertThat(getRepository().getRestaurantById(requestedRestaurantId)).isNull();
  }
}
