package ca.ulaval.glo2003.infra.persistence.mongo;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.CustomerDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MongoReservationRepositoryTest {

  private static MongoReservationRepositoryFixture mongoReservationRepositoryFixture;
  private Datastore mockDatastore;
  private Query<ReservationDocument> mockQuery;

  @BeforeEach
  void setUp() {
    mockDatastore = Mockito.mock(Datastore.class);
    mockQuery = Mockito.mock(Query.class);
    mongoReservationRepositoryFixture = new MongoReservationRepositoryFixture();
  }

  @Test
  void givenValidReservation_whenSave_thenNoException() {
    Reservation reservation = mongoReservationRepositoryFixture.build();

    assertDoesNotThrow(() -> mockDatastore.save(any(ReservationDocument.class)));
  }

  @Test
  void
      givenExistingRestaurantId_whenGetReservationListOfRestaurant_thenReturnExpectedReservations() {
    String restaurantId = "restaurantId";
    String[] reservationIdList = {"1", "2", "3"};
    List<ReservationDocument> documents = new ArrayList<>();

    for (String reservationId : reservationIdList) {
      ReservationDocument document = createTestReservationDocument(reservationId, restaurantId);
      documents.add(document);
    }

    when(mockDatastore.find(ReservationDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.stream()).thenReturn(documents.stream());

    List<ReservationDocument> result =
        mockDatastore
            .find(ReservationDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("restaurantId", restaurantId))
            .stream()
            .toList();

    assertThat(result).hasSize(3);
    verify(mockDatastore, times(1)).find(ReservationDocument.class);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetReservationListOfRestaurant_thenReturnNoReservations() {
    String requestedRestaurantId = "NonExistentRestaurantId";

    when(mockDatastore.find(ReservationDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.stream()).thenReturn(new ArrayList<ReservationDocument>().stream());

    List<ReservationDocument> result =
        mockDatastore
            .find(ReservationDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("restaurantId", requestedRestaurantId))
            .stream()
            .toList();

    assertThat(result).isEmpty();
  }

  @Test
  void givenExistingReservationId_whenGetReservationById_thenReturnReservation() {
    String reservationId = "reservationId";
    ReservationDocument document = createTestReservationDocument(reservationId, "restaurantId");

    when(mockDatastore.find(ReservationDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.first()).thenReturn(document);

    ReservationDocument result =
        mockDatastore
            .find(ReservationDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("_id", reservationId))
            .first();

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(reservationId);
  }

  @Test
  void givenNonExistentReservationId_whenGetReservationById_thenReturnNoReservation() {
    String requestedReservationId = "nonExistentReservationId";

    when(mockDatastore.find(ReservationDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.first()).thenReturn(null);

    ReservationDocument result =
        mockDatastore
            .find(ReservationDocument.class)
            .filter(dev.morphia.query.filters.Filters.eq("_id", requestedReservationId))
            .first();

    assertThat(result).isNull();
  }

  @Test
  void givenExistingReservationId_whenDelete_thenDeleteOperationExecuted() {
    String reservationId = "reservationId";

    when(mockDatastore.find(ReservationDocument.class)).thenReturn(mockQuery);
    when(mockQuery.filter(any(Filter.class))).thenReturn(mockQuery);
    when(mockQuery.delete()).thenReturn(null);

    assertDoesNotThrow(
        () ->
            mockDatastore
                .find(ReservationDocument.class)
                .filter(dev.morphia.query.filters.Filters.eq("_id", reservationId))
                .delete());

    verify(mockDatastore, times(1)).find(ReservationDocument.class);
  }

  private ReservationDocument createTestReservationDocument(String id, String restaurantId) {
    ReservationDocument document = new ReservationDocument();
    document.setId(id);
    document.setDate("2025-12-25");
    document.setStartTime("18:30");
    document.setEndTime("20:30");
    document.setGroupSize(4);
    document.setRestaurantId(restaurantId);

    CustomerDocument customerDoc = new CustomerDocument();
    customerDoc.setName("John Doe");
    customerDoc.setEmail("john.doe@example.com");
    customerDoc.setPhoneNumber("1234567890");
    document.setCustomer(customerDoc);

    return document;
  }
}
