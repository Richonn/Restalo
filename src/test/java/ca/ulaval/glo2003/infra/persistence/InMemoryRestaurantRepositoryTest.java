package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryRestaurantRepositoryTest {

  private static RestaurantRepository restaurantRepository;
  private static InMemoryRestaurantRepositoryFixture inMemoryRestaurantRepositoryFixture;

  @BeforeEach
  public void setUp() {
    restaurantRepository = new InMemoryRestaurantRepository();
    inMemoryRestaurantRepositoryFixture = new InMemoryRestaurantRepositoryFixture();
  }

  @Test
  void givenValidRestaurant_whenSaveRestaurant_thenNoException() {
    Restaurant restaurant = inMemoryRestaurantRepositoryFixture.build();

    assertDoesNotThrow(() -> restaurantRepository.save(restaurant));
  }

  @Test
  void givenExistingOwnerId_whenGetRestaurantListOfOwner_thenReturnExpectedRestaurants() {

    String ownerId = "ownerId";
    String[] restaurantIdList = {"1", "2", "3"};
    List<Restaurant> expectedRestaurantList = new ArrayList<>();

    for (String restaurantId : restaurantIdList) {
      Restaurant restaurant =
          inMemoryRestaurantRepositoryFixture
              .withRestaurantId(restaurantId)
              .withOwnerId(ownerId)
              .build();
      restaurantRepository.save(restaurant);
      expectedRestaurantList.add(restaurant);
    }

    assertThat(restaurantRepository.getRestaurantListOfOwner(ownerId))
        .isEqualTo(expectedRestaurantList);
  }

  @Test
  void givenNonExistentOwnerId_whenGetRestaurantListOfOwner_thenReturnNoRestaurants() {

    String actualOwnerId = "ownerId";
    String requestedOwnerId = "NonExistentOwnerId";
    String[] restaurantIdList = {"1", "2", "3"};

    for (String restaurantId : restaurantIdList) {
      Restaurant restaurant =
          inMemoryRestaurantRepositoryFixture
              .withRestaurantId(restaurantId)
              .withOwnerId(actualOwnerId)
              .build();
      restaurantRepository.save(restaurant);
    }

    assertThat(restaurantRepository.getRestaurantListOfOwner(requestedOwnerId)).isEmpty();
  }

  @Test
  void givenExistingRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnRestaurant() {

    String ownerId = "ownerId";
    String restaurantId = "restaurantId";
    Restaurant restaurant =
        inMemoryRestaurantRepositoryFixture
            .withRestaurantId(restaurantId)
            .withOwnerId(ownerId)
            .build();
    restaurantRepository.save(restaurant);

    assertThat(restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId))
        .isEqualTo(restaurant);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnNoRestaurant() {

    String ownerId = "ownerId";
    String actualRestaurantId = "restaurantId";
    String requestedRestaurantId = "nonExistentRestaurantId";

    Restaurant restaurant =
        inMemoryRestaurantRepositoryFixture
            .withRestaurantId(actualRestaurantId)
            .withOwnerId(ownerId)
            .build();
    restaurantRepository.save(restaurant);

    assertThat(restaurantRepository.getRestaurantByIdOfOwner(ownerId, requestedRestaurantId))
        .isNull();
  }

  @Test
  void givenExistingRestaurant_whenGetRestaurantList_thenReturnExpectedRestaurantsList() {
    String[] restaurantIdList = {"1", "2", "3"};
    String[] ownerIdList = {"1", "2", "3"};
    List<Restaurant> expectedRestaurantList = new ArrayList<>();

    for (int i = 0; i < restaurantIdList.length; i++) {
      Restaurant restaurant =
          inMemoryRestaurantRepositoryFixture
              .withOwnerId(ownerIdList[i])
              .withRestaurantId(restaurantIdList[i])
              .build();
      restaurantRepository.save(restaurant);
      expectedRestaurantList.add(restaurant);
    }

    assertThat(restaurantRepository.getRestaurantList()).isEqualTo(expectedRestaurantList);
  }

  @Test
  void givenNoExistingRestaurant_whenGetRestaurantList_thenReturnEmptyList() {
    assertThat(restaurantRepository.getRestaurantList()).isEmpty();
  }

  @Test
  void givenExistingRestaurantId_whenGetRestaurantById_thenReturnRestaurant() {
    String restaurantId = "restaurantId";
    Restaurant restaurant =
        inMemoryRestaurantRepositoryFixture.withRestaurantId(restaurantId).build();
    restaurantRepository.save(restaurant);

    assertThat(restaurantRepository.getRestaurantById(restaurantId)).isEqualTo(restaurant);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetRestaurantById_thenReturnNoRestaurant() {

    String actualRestaurantId = "restaurantId";
    String requestedRestaurantId = "nonExistentRestaurantId";

    Restaurant restaurant =
        inMemoryRestaurantRepositoryFixture.withRestaurantId(actualRestaurantId).build();
    restaurantRepository.save(restaurant);

    assertThat(restaurantRepository.getRestaurantById(requestedRestaurantId)).isNull();
  }
}
