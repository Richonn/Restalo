package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryReservationRepository implements ReservationRepository {
  private final Map<String, Reservation> reservations = new HashMap<>();

  public InMemoryReservationRepository() {}

  @Override
  public void save(Reservation reservation) {
    reservations.put(reservation.getId(), reservation);
  }

  @Override
  public List<Reservation> getReservationListOfRestaurant(String restaurantId) {
    return reservations.values().stream()
        .filter(reservation -> reservation.getRestaurantId().equals(restaurantId))
        .collect(Collectors.toList());
  }

  @Override
  public Reservation getReservationById(String reservationId) {
    return reservations.get(reservationId);
  }

  @Override
  public void delete(String reservationId) {
    reservations.remove(reservationId);
  }

  @Override
  public void deleteAllReservationOfRestaurant(String restaurantId) {
    reservations
        .values()
        .removeIf(reservation -> reservation.getRestaurantId().equals(restaurantId));
  }
}
