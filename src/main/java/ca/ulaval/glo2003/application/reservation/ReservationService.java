package ca.ulaval.glo2003.application.reservation;

import ca.ulaval.glo2003.api.assembler.ReservationAssembler;
import ca.ulaval.glo2003.api.assembler.ReservationSearchResponseAssembler;
import ca.ulaval.glo2003.api.assembler.RestaurantSearchAvailabilitiesResponseAssembler;
import ca.ulaval.glo2003.api.dto.common.ReservationDto;
import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.api.dto.response.ReservationSearchResponseDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchAvailabilitiesResponseDto;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    validateRestaurantCapacity(restaurant, reservation);

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

  public void deleteReservation(String reservationId) {
    Reservation reservation = reservationRepository.getReservationById(reservationId);

    if (reservation == null) {
      throw new NotFoundException("Reservation not found");
    }

    reservationRepository.delete(reservationId);
  }

  public List<RestaurantSearchAvailabilitiesResponseDto> getRestaurantAvailabilitiesByDate(
      String restaurantId, String dateString) {

    LocalDate date = restaurantDomainService.convertDateStringToLocalDate(dateString);
    Restaurant restaurant = restaurantRepository.getRestaurantById(restaurantId);

    if (restaurant == null) {
      throw new NotFoundException();
    }

    List<Reservation> reservations =
        reservationRepository.getReservationListOfRestaurant(restaurantId).stream()
            .filter(reservation -> reservation.getDate().equals(date))
            .toList();
    Map<LocalTime, Integer> restaurantAvailabilities =
        restaurantDomainService.getRestaurantAvailability(restaurant, reservations);
    List<RestaurantSearchAvailabilitiesResponseDto> restaurantAvailabilitiesDto = new ArrayList<>();

    for (Map.Entry<LocalTime, Integer> entry : restaurantAvailabilities.entrySet()) {
      restaurantAvailabilitiesDto.add(
          RestaurantSearchAvailabilitiesResponseAssembler.toRestaurantSearchAvailabilitiesResponse(
              LocalDateTime.of(date, entry.getKey()), entry.getValue()));
    }
    return restaurantAvailabilitiesDto;
  }

  private void validateRestaurantCapacity(Restaurant restaurant, Reservation newReservation) {
    List<Reservation> existingReservations =
        reservationRepository.getReservationListOfRestaurant(restaurant.getId()).stream()
            .filter(r -> r.getDate().equals(newReservation.getDate()))
            .sorted(Comparator.comparing(Reservation::getStartTime))
            .toList();

    Map<LocalTime, Integer> availability =
        restaurantDomainService.getRestaurantAvailability(restaurant, existingReservations);

    List<LocalTime> reservationSlots = newReservation.generateAllTimeSlotsInterval();

    for (LocalTime slot : reservationSlots) {
      Integer remainingCapacity = availability.get(slot);

      if (remainingCapacity == null || remainingCapacity - newReservation.getGroupSize() < 0) {
        throw new InvalidParameterException(
            "The restaurant does not have enough capacity for this reservation");
      }
    }
  }
}
