package ca.ulaval.glo2003.infra.persistence;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractReservationRepositoryTest {

  private static final int DAYS_IN_FUTURE = 1;
  private static final int START_HOUR = 12;
  private static final int START_MINUTE = 0;
  private static final int END_HOUR = 13;
  private static final int END_MINUTE = 0;
  private static final int DEFAULT_GROUP_SIZE = 4;
  private static final String DEFAULT_CUSTOMER_NAME = "John Doe";
  private static final String DEFAULT_CUSTOMER_EMAIL = "john@example.com";
  private static final String DEFAULT_CUSTOMER_PHONE = "5141234567";
  private static final int EXPECTED_RESERVATION_COUNT = 3;

  protected abstract ReservationRepository getRepository();

  protected abstract void clearRepository();

  @BeforeEach
  void setUp() {
    clearRepository();
  }

  protected Reservation createReservation(String id, String restaurantId) {
    return new Reservation(
        LocalDate.now().plusDays(DAYS_IN_FUTURE),
        new ReservationTime(
            LocalTime.of(START_HOUR, START_MINUTE), LocalTime.of(END_HOUR, END_MINUTE)),
        DEFAULT_GROUP_SIZE,
        new Customer(DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE),
        restaurantId);
  }

  @Test
  void givenValidReservation_whenSaveReservation_thenNoException() {
    Reservation reservation = createReservation("1", "restaurant1");

    assertDoesNotThrow(() -> getRepository().save(reservation));
  }

  @Test
  void
      givenExistingRestaurantId_whenGetReservationListOfRestaurant_thenReturnExpectedReservations() {
    String restaurantId = "restaurantId";
    String[] reservationIdList = {"1", "2", "3"};
    List<Reservation> expectedReservationsList = new ArrayList<>();

    for (String reservationId : reservationIdList) {
      Reservation reservation = createReservation(reservationId, restaurantId);
      getRepository().save(reservation);
      expectedReservationsList.add(reservation);
    }

    List<Reservation> result = getRepository().getReservationListOfRestaurant(restaurantId);

    assertThat(result).hasSize(EXPECTED_RESERVATION_COUNT);
    assertThat(result.stream().map(Reservation::getId).toList())
        .containsExactlyElementsIn(
            expectedReservationsList.stream().map(Reservation::getId).toList());
  }

  @Test
  void givenNonExistentRestaurantId_whenGetReservationListOfRestaurant_thenReturnNoReservations() {
    String actualRestaurantId = "restaurantId";
    String requestedRestaurantId = "nonExistentRestaurantId";
    String[] reservationIdList = {"1", "2", "3"};

    for (String reservationId : reservationIdList) {
      Reservation reservation = createReservation(reservationId, actualRestaurantId);
      getRepository().save(reservation);
    }

    assertThat(getRepository().getReservationListOfRestaurant(requestedRestaurantId)).isEmpty();
  }

  @Test
  void givenExistingReservationId_whenGetReservationById_thenReturnReservation() {
    Reservation reservation = createReservation("1", "restaurant1");
    getRepository().save(reservation);
    String reservationId = reservation.getId();

    Reservation result = getRepository().getReservationById(reservationId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(reservationId);
  }

  @Test
  void givenNonExistentReservationId_whenGetReservationById_thenReturnNoReservation() {
    String actualReservationId = "reservationId";
    String requestedReservationId = "nonExistentReservationId";

    Reservation reservation = createReservation(actualReservationId, "restaurant1");
    getRepository().save(reservation);

    assertThat(getRepository().getReservationById(requestedReservationId)).isNull();
  }

  @Test
  void givenExistingReservation_whenDeleteReservation_thenReservationIsDeleted() {
    String reservationId = "reservationId";
    Reservation reservation = createReservation(reservationId, "restaurant1");
    getRepository().save(reservation);

    getRepository().delete(reservationId);

    assertThat(getRepository().getReservationById(reservationId)).isNull();
  }
}
