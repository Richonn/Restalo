package ca.ulaval.glo2003.infra.persistence.mongo;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MongoRestaurantRepositoryTest {

  private static MongoRestaurantRepositoryFixture mongoRestaurantRepositoryFixture;
  private Datastore mockDatastore;
  private Query<RestaurantDocument> mockQuery;

  @BeforeEach
  void setUp() {
    mockDatastore = Mockito.mock(Datastore.class);
    mockQuery = Mockito.mock(Query.class);
    mongoRestaurantRepositoryFixture = new MongoRestaurantRepositoryFixture();
  }

  @Test
  void givenValidRestaurant_whenSave_thenNoException() {
    Restaurant restaurant = mongoRestaurantRepositoryFixture.build();

    assertDoesNotThrow(() -> mockDatastore.save(any(RestaurantDocument.class)));
  }

  @Test
  void givenExistingOwnerId_whenGetRestaurantListOfOwner_thenReturnExpectedRestaurants() {
    String ownerId = "ownerId";
    String[] restaurantIdList = {"1", "2", "3"};
    List<RestaurantDocument> documents = new ArrayList<>();

    for (String restaurantId : restaurantIdList) {
      RestaurantDocument document = createTestRestaurantDocument(restaurantId, ownerId);
      documents.add(document);
    }

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.stream()).thenReturn(documents.stream());

    List<RestaurantDocument> result =
        mockDatastore
            .find(RestaurantDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("ownerId", ownerId))
            .stream()
            .toList();

    assertThat(result).hasSize(3);
    verify(mockDatastore, times(1)).find(RestaurantDocument.class);
  }

  @Test
  void givenNonExistentOwnerId_whenGetRestaurantListOfOwner_thenReturnNoRestaurants() {
    String requestedOwnerId = "NonExistentOwnerId";

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.stream()).thenReturn(new ArrayList<RestaurantDocument>().stream());

    List<RestaurantDocument> result =
        mockDatastore
            .find(RestaurantDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("ownerId", requestedOwnerId))
            .stream()
            .toList();

    assertThat(result).isEmpty();
  }

  @Test
  void givenExistingRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnRestaurant() {
    String ownerId = "ownerId";
    String restaurantId = "restaurantId";
    RestaurantDocument document = createTestRestaurantDocument(restaurantId, ownerId);

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.first()).thenReturn(document);

    RestaurantDocument result =
        mockDatastore
            .find(RestaurantDocument.class)
            .filter(
                dev.morphia.query.filters.Filters.and(
                    dev.morphia.query.filters.Filters.eq("_id", restaurantId),
                    dev.morphia.query.filters.Filters.eq("ownerId", ownerId)))
            .first();

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(restaurantId);
    assertThat(result.getOwnerId()).isEqualTo(ownerId);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetRestaurantByIdOfOwner_thenReturnNoRestaurant() {
    String ownerId = "ownerId";
    String requestedRestaurantId = "nonExistentRestaurantId";

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.first()).thenReturn(null);

    RestaurantDocument result =
        mockDatastore
            .find(RestaurantDocument.class)
            .filter(
                dev.morphia.query.filters.Filters.and(
                    dev.morphia.query.filters.Filters.eq("_id", requestedRestaurantId),
                    dev.morphia.query.filters.Filters.eq("ownerId", ownerId)))
            .first();

    assertThat(result).isNull();
  }

  @Test
  void givenExistingRestaurants_whenGetRestaurantList_thenReturnExpectedRestaurantsList() {
    String[] restaurantIdList = {"1", "2", "3"};
    String[] ownerIdList = {"1", "2", "3"};
    List<RestaurantDocument> documents = new ArrayList<>();

    for (int i = 0; i < restaurantIdList.length; i++) {
      RestaurantDocument document =
          createTestRestaurantDocument(restaurantIdList[i], ownerIdList[i]);
      documents.add(document);
    }

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.stream()).thenReturn(documents.stream());

    List<RestaurantDocument> result =
        mockDatastore.find(RestaurantDocument.class).stream().toList();

    assertThat(result).hasSize(3);
  }

  @Test
  void givenNoExistingRestaurant_whenGetRestaurantList_thenReturnEmptyList() {
    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.stream()).thenReturn(new ArrayList<RestaurantDocument>().stream());

    List<RestaurantDocument> result =
        mockDatastore.find(RestaurantDocument.class).stream().toList();

    assertThat(result).isEmpty();
  }

  @Test
  void givenExistingRestaurantId_whenGetRestaurantById_thenReturnRestaurant() {
    String restaurantId = "restaurantId";
    RestaurantDocument document = createTestRestaurantDocument(restaurantId, "ownerId");

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.first()).thenReturn(document);

    RestaurantDocument result =
        mockDatastore
            .find(RestaurantDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("_id", restaurantId))
            .first();

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(restaurantId);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetRestaurantById_thenReturnNoRestaurant() {
    String requestedRestaurantId = "nonExistentRestaurantId";

    when(mockDatastore.find(RestaurantDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.first()).thenReturn(null);

    RestaurantDocument result =
        mockDatastore
            .find(RestaurantDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("_id", requestedRestaurantId))
            .first();

    assertThat(result).isNull();
  }

  private RestaurantDocument createTestRestaurantDocument(String id, String ownerId) {
    RestaurantDocument document = new RestaurantDocument();
    document.setId(id);
    document.setName("Test Restaurant");
    document.setCapacity(50);
    document.setOpeningHour("10:00");
    document.setClosingHour("22:00");
    document.setReservationDuration(60);
    document.setOwnerId(ownerId);
    return document;
  }
}
