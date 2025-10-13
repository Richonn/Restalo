package ca.ulaval.glo2003.application.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.reservation.CustomerDto;
import ca.ulaval.glo2003.api.dto.reservation.ReservationDto;
import ca.ulaval.glo2003.api.dto.reservation.ReservationResponseDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationTime;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationServiceTest {

  private static ReservationService reservationService;

  private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
  private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
  private final ReservationFactory reservationFactory = mock(ReservationFactory.class);
  private final Reservation reservation = mock(Reservation.class);
  private final ReservationDto reservationDto = mock(ReservationDto.class);
  private final CustomerDto customerDto = mock(CustomerDto.class);

  @BeforeEach
  public void setUp() {
    reservationService =
        new ReservationService(reservationRepository, restaurantRepository, reservationFactory);

    when(reservationDto.date()).thenReturn(LocalDate.now());
    when(reservationDto.startTime()).thenReturn(LocalTime.NOON);
    when(reservationDto.groupSize()).thenReturn(4);
    when(reservationDto.customer()).thenReturn(customerDto);
    when(customerDto.name()).thenReturn("John Deer");
    when(customerDto.email()).thenReturn("john.deer@gmail.com");
    when(customerDto.phoneNumber()).thenReturn("1234567890");
    when(restaurantRepository.getRestaurantById(null)).thenReturn(null);

    doNothing().when(reservationRepository).save(reservation);
  }

  @Test
  void givenValidReservationDTOAndRestaurantId_whenCreateReservation_thenReturnExpectedId() {

    String expectedId = "456";

    when(reservationFactory.createReservation(
            any(LocalDate.class),
            any(LocalTime.class),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            any(Restaurant.class),
            anyInt()))
        .thenReturn(reservation);
    when(reservation.getId()).thenReturn(expectedId);

    assertThat(reservationService.createReservation(reservationDto, "123")).isEqualTo(expectedId);
  }

  @Test
  void
      givenNullReservationDTOAndRestaurantId_whenCreateReservation_thenThrowsMissingParameterException() {

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> reservationService.createReservation(null, "123"));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing body");
  }

  @Test
  void
      givenValidReservationDTOAndNonExistantRestaurantId_whenCreateReservationReturnNull_thenThrowsNotFoundException() {

    String restaurantId = "this restaurant dosent exist";

    when(reservationFactory.createReservation(
            any(LocalDate.class),
            any(LocalTime.class),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            any(Restaurant.class),
            anyInt()))
        .thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> reservationService.createReservation(reservationDto, restaurantId));
    assertThat(exception).isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenValidNumber_whenGetReservation_thenReturnsDto() {
    String reservationId = "reservation-123";
    String restaurantId = "restaurant-123";

    Reservation mockReservation =
        new Reservation(
            LocalDate.of(2025, 10, 30),
            new ReservationTime(LocalTime.of(18, 0), LocalTime.of(19, 0)),
            4,
            new Customer("John Doe", "john@example.com", "1234567890"),
            restaurantId);

    Restaurant mockRestaurant =
        new Restaurant(
            "Pizza Delamama", 50, LocalTime.of(11, 0), LocalTime.of(22, 0), "owner-123", 80);

    when(reservationRepository.getReservationById(reservationId)).thenReturn(mockReservation);
    when(restaurantRepository.getRestaurantById(restaurantId)).thenReturn(mockRestaurant);

    ReservationResponseDto result = reservationService.getReservationById(reservationId);

    assertNotNull(result);
    assertNotNull(result.number());
    assertNotNull(result.restaurant());
  }

  @Test
  void givenNonExistentNumber_whenGetReservation_thenThrows404() {
    String reservationId = "nonexistent";

    when(reservationRepository.getReservationById(reservationId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class, () -> reservationService.getReservationById(reservationId));

    assertThat(exception).isInstanceOf(NotFoundException.class);
  }

  @Test
  void givenReservationWithNonExistentRestaurant_whenGetReservation_thenThrows404() {
    String reservationId = "reservation-123";
    String restaurantId = "restaurant-123";

    Reservation mockReservation =
        new Reservation(
            LocalDate.of(2025, 10, 30),
            new ReservationTime(LocalTime.of(18, 0), LocalTime.of(19, 0)),
            4,
            new Customer("John Doe", "john@example.com", "1234567890"),
            restaurantId);

    when(reservationRepository.getReservationById(reservationId)).thenReturn(mockReservation);
    when(restaurantRepository.getRestaurantById(restaurantId)).thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class, () -> reservationService.getReservationById(reservationId));

    assertThat(exception).isInstanceOf(NotFoundException.class);
  }
}
