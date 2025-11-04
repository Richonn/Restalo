package ca.ulaval.glo2003.application.reservation;

import ca.ulaval.glo2003.api.assembler.ReservationAssembler;
import ca.ulaval.glo2003.api.assembler.ReservationSearchResponseAssembler;
import ca.ulaval.glo2003.api.dto.common.ReservationDto;
import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.api.dto.response.ReservationSearchResponseDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final RestaurantRepository restaurantRepository;
  private final ReservationFactory reservationFactory;
  private final RestaurantDomainService restaurantDomainService;

  @Inject
  public ReservationService(
      ReservationRepository reservationRepository,
      RestaurantRepository restaurantRepository,
      ReservationFactory reservationFactory,
      RestaurantDomainService restaurantDomainService) {
    this.reservationRepository = reservationRepository;
    this.restaurantRepository = restaurantRepository;
    this.reservationFactory = reservationFactory;
    this.restaurantDomainService = restaurantDomainService;
  }

  public String createReservation(ReservationDto reservationDto, String restaurantId) {

    if (reservationDto == null) {
      throw new MissingParameterException("Missing body");
    }

    Restaurant restaurant = restaurantRepository.getRestaurantById(restaurantId);

    if (restaurant == null) {
      throw new NotFoundException();
    }

    Reservation reservation =
        reservationFactory.createReservation(
            reservationDto.date(),
            reservationDto.startTime(),
            reservationDto.groupSize(),
            reservationDto.customer().name(),
            reservationDto.customer().email(),
            reservationDto.customer().phoneNumber(),
            restaurant);

    if (reservation == null) {
      throw new NotFoundException();
    }

    reservationRepository.save(reservation);

    return reservation.getId();
  }

  public ReservationResponseDto getReservationById(String reservationId) {
    Reservation reservation = reservationRepository.getReservationById(reservationId);

    if (reservation == null) {
      throw new NotFoundException();
    }

    Restaurant restaurant = restaurantRepository.getRestaurantById(reservation.getRestaurantId());

    if (restaurant == null) {
      throw new NotFoundException();
    }

    return ReservationAssembler.toReservationResponseDto(reservation, restaurant);
  }

  public List<ReservationSearchResponseDto> getReservationBySearch(
      String ownerId, String restaurantId, String dateString, String customerName) {

    restaurantDomainService.validateHeaders(ownerId);

    if (restaurantRepository.getRestaurantByIdOfOwner(ownerId, restaurantId) == null) {
      throw new NotFoundException();
    }

    List<Reservation> listAllReservationOfRestaurant =
        reservationRepository.getReservationListOfRestaurant(restaurantId);

    return listAllReservationOfRestaurant.stream()
        .filter(reservation -> reservation.matchesDate(dateString))
        .filter(reservation -> reservation.matchesCustomerName(customerName))
        .map(ReservationSearchResponseAssembler::toReservationSearchResponseDto)
        .toList();
  }
}
