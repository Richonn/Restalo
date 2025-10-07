package ca.ulaval.glo2003.application.reservation;

import ca.ulaval.glo2003.api.dto.reservation.ReservationDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final RestaurantRepository restaurantRepository;
  private final ReservationFactory reservationFactory;

  @Inject
  public ReservationService(
      ReservationRepository reservationRepository,
      RestaurantRepository restaurantRepository,
      ReservationFactory reservationFactory) {
    this.reservationRepository = reservationRepository;
    this.restaurantRepository = restaurantRepository;
    this.reservationFactory = reservationFactory;
  }

  public String createReservation(ReservationDto reservationDto, String restaurantId) {

    if (reservationDto == null) {
      throw new MissingParameterException("Missing body");
    }

    Reservation reservation =
        reservationFactory.createReservation(
            reservationDto.date(),
            reservationDto.startTime(),
            reservationDto.groupSize(),
            reservationDto.customer().name(),
            reservationDto.customer().email(),
            reservationDto.customer().phoneNumber(),
            restaurantRepository.getRestaurantById(restaurantId));

    if (reservation != null) {
      reservationRepository.save(reservation);
      return reservation.getId();
    }
    throw new NotFoundException();
  }
}
