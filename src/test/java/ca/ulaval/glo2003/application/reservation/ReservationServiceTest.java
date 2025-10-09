package ca.ulaval.glo2003.application.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.reservation.CustomerDto;
import ca.ulaval.glo2003.api.dto.reservation.ReservationDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.Reservation;
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

    when((reservationFactory).createReservation(any(), any(), any(), any(), any(), any(), any()))
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

    when((reservationFactory).createReservation(any(), any(), any(), any(), any(), any(), any()))
        .thenReturn(null);

    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> reservationService.createReservation(reservationDto, restaurantId));
    assertThat(exception).isInstanceOf(NotFoundException.class);
  }
}
