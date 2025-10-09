package ca.ulaval.glo2003.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.reservation.CustomerDto;
import ca.ulaval.glo2003.api.dto.reservation.ReservationDto;
import ca.ulaval.glo2003.api.dto.reservation.ReservationResponseDto;
import ca.ulaval.glo2003.api.dto.reservation.TimeDto;
import ca.ulaval.glo2003.api.dto.restaurant.HoursDto;
import ca.ulaval.glo2003.api.dto.restaurant.RestaurantDto;
import ca.ulaval.glo2003.api.exceptions.MissingParameterException;
import ca.ulaval.glo2003.api.ressources.reservation.ReservationResource;
import ca.ulaval.glo2003.application.reservation.ReservationService;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

public class ReservationResourceIntegrationTest extends JerseyTest {

  private ReservationService reservationService;

  @Override
  protected Application configure() {
    reservationService = mock(ReservationService.class);

    return new ResourceConfig()
        .register(
            new AbstractBinder() {
              @Override
              protected void configure() {
                bind(reservationService).to(ReservationService.class);
              }
            })
        .register(ReservationResource.class)
        .packages("ca.ulaval.glo2003.api.exceptions");
  }

  @Test
  public void givenValidReservation_whenPost_thenReturns201WithLocation() {
    when(reservationService.createReservation(any(ReservationDto.class), eq("restaurant-123")))
        .thenReturn("reservation-123");

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(201, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/reservations/reservation-123"));

      verify(reservationService).createReservation(any(ReservationDto.class), eq("restaurant-123"));
    }
  }

  @Test
  public void givenEndTimeAfterClosing_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(
            new InvalidParameterException(
                "The restaurant closes before the end of the reservation"));

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:30:00",
          "groupSize": 2,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "5555555555"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("closes before"));
    }
  }

  @Test
  public void givenGroupSizeZero_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new InvalidParameterException("The group size must be positive"));

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 0,
          "customer": {
            "name": "John doe",
            "email": "john@example.com",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(
          body.contains("INVALID_PARAMETER")
              || body.contains("group size")
              || body.contains("positive"));
    }
  }

  @Test
  public void givenEmptyName_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new InvalidParameterException("The name of the customer cannot be blank"));

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "",
            "email": "john@example.com",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("name"));
    }
  }

  @Test
  public void givenInvalidEmail_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new InvalidParameterException("The email address of the customer is not valid"));

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "John Doe",
            "email": "invalid-email",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("email"));
    }
  }

  @Test
  public void givenInvalidPhone_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new InvalidParameterException("The phone number of the customer is not valid"));

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "123"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("phone"));
    }
  }

  @Test
  public void givenMissingDate_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new MissingParameterException("The date of the reservation is required"));

    String reservationJson =
        """
        {
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("date"));
    }
  }

  @Test
  public void givenMissingCustomer_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new MissingParameterException("The customer of the reservation is required"));

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4
        }
        """;

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("customer"));
    }
  }

  @Test
  public void givenNonExistentRestaurant_whenPost_thenReturns404() {
    when(reservationService.createReservation(any(ReservationDto.class), eq("nonexistent")))
        .thenThrow(new NotFoundException());

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response =
        target("/restaurants/nonexistent/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(404, response.getStatus());
    }
  }

  @Test
  public void givenMissingBody_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(), any()))
        .thenThrow(new MissingParameterException("Missing body"));

    try (Response response =
        target("/restaurants/restaurant-123/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null))) {

      assertEquals(400, response.getStatus());
    }
  }

  @Test
  public void givenValidNumber_whenGet_thenReturns200WithFullBody() {
    ReservationResponseDto mockDto = createMockReservationResponseDto();
    when(reservationService.getReservationById("reservation-123")).thenReturn(mockDto);

    try (Response response =
        target("/reservations/reservation-123").request(MediaType.APPLICATION_JSON).get()) {

      assertEquals(200, response.getStatus());

      String body = response.readEntity(String.class);
      assertTrue(body.contains("number"));
      assertTrue(body.contains("date"));
      assertTrue(body.contains("time"));
      assertTrue(body.contains("start"));
      assertTrue(body.contains("end"));
      assertTrue(body.contains("groupSize"));
      assertTrue(body.contains("customer"));
      assertTrue(body.contains("restaurant"));

      verify(reservationService).getReservationById("reservation-123");
    }
  }

  @Test
  public void givenNonExistentNumber_whenGet_thenReturns404() {
    when(reservationService.getReservationById("nonexistent")).thenThrow(new NotFoundException());

    try (Response response =
        target("/reservations/nonexistent").request(MediaType.APPLICATION_JSON).get()) {

      assertEquals(404, response.getStatus());
    }
  }

  private ReservationResponseDto createMockReservationResponseDto() {
    return new ReservationResponseDto(
        "reservation-123",
        LocalDate.of(2025, 10, 30),
        new TimeDto(LocalTime.of(18, 0), LocalTime.of(19, 0)),
        4,
        new CustomerDto("John Doe", "john@example.com", "1234567890"),
        new RestaurantDto(
            "restaurant-123",
            "Pizza Delamama",
            50,
            new HoursDto(LocalTime.of(11, 0), LocalTime.of(22, 0))));
  }
}
