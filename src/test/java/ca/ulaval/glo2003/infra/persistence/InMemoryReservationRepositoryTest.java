package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryReservationRepositoryTest {

  private static ReservationRepository reservationRepository;
  private static InMemoryReservationRepositoryFixture inMemoryReservationRepositoryFixture;

  @BeforeEach
  public void setUp() {
    reservationRepository = new InMemoryReservationRepository();
    inMemoryReservationRepositoryFixture = new InMemoryReservationRepositoryFixture();
  }

  @Test
  void givenValidReservation_whenSaveReservation_thenNoException() {
    Reservation reservation = inMemoryReservationRepositoryFixture.build();

    assertDoesNotThrow(() -> reservationRepository.save(reservation));
  }

  @Test
  void
      givenExistingRestaurantId_whenGetReservationListOfRestaurant_thenReturnExpectedReservations() {
    String restaurantId = "restaurantId";
    String[] reservationIdList = {"1", "2", "3"};
    List<Reservation> expectedReservationsList = new ArrayList<>();

    for (String reservationId : reservationIdList) {
      Reservation reservation =
          inMemoryReservationRepositoryFixture.withReservationId(reservationId).build();
      reservationRepository.save(reservation);
      expectedReservationsList.add(reservation);
    }

    assertThat(reservationRepository.getReservationListOfRestaurant(restaurantId))
        .isEqualTo(expectedReservationsList);
  }

  @Test
  void givenNonExistentRestaurantId_whenGetReservationListOfRestaurant_thenReturnNoReservations() {
    String actualRestaurantId = "restaurantId";
    String requestedRestaurantId = "nonExistentRestaurantId";
    String[] reservationIdList = {"1", "2", "3"};

    for (String reservationId : reservationIdList) {
      Reservation reservation =
          inMemoryReservationRepositoryFixture
              .withRestaurantId(actualRestaurantId)
              .withReservationId(reservationId)
              .build();
      reservationRepository.save(reservation);
    }

    assertThat(reservationRepository.getReservationListOfRestaurant(requestedRestaurantId))
        .isEmpty();
  }

  @Test
  void givenExistingReservationId_whenGetReservationById_thenReturnReservation() {
    String reservationId = "reservationId";

    Reservation reservation =
        inMemoryReservationRepositoryFixture.withReservationId(reservationId).build();
    reservationRepository.save(reservation);

    assertThat(reservationRepository.getReservationById(reservationId)).isEqualTo(reservation);
  }

  @Test
  void givenNonExistentReservationId_whenGetReservationById_thenReturnNoReservation() {
    String actualReservationId = "reservationId";
    String requestedReservationId = "nonExistentReservationId";

    Reservation reservation =
        inMemoryReservationRepositoryFixture.withReservationId(actualReservationId).build();
    reservationRepository.save(reservation);

    assertThat(reservationRepository.getReservationById(requestedReservationId)).isNull();
  }
}
