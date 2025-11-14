package ca.ulaval.glo2003.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.api.dto.common.HoursDto;
import ca.ulaval.glo2003.api.dto.common.ReservationDto;
import ca.ulaval.glo2003.api.dto.common.ReservationTimeDto;
import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantReservationResponseDto;
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

  private static final String TEST_RESTAURANT_ID = "restaurant-123";
  private static final String TEST_RESERVATION_ID = "reservation-123";
  private static final String NONEXISTENT_ID = "nonexistent";
  private static final String TEST_DATE = "2025-10-30";
  private static final String TEST_START_TIME = "18:00:00";
  private static final String LATE_START_TIME = "18:30:00";
  private static final String TEST_CUSTOMER_NAME = "John Doe";
  private static final String TEST_EMAIL = "john@example.com";
  private static final String TEST_PHONE = "1234567890";
  private static final String INVALID_EMAIL = "invalid-email";
  private static final String INVALID_PHONE = "123";
  private static final String SHORT_PHONE = "5555555555";
  private static final int TEST_GROUP_SIZE = 4;
  private static final int ZERO_GROUP_SIZE = 0;
  private static final int SMALL_GROUP_SIZE = 2;
  private static final int HTTP_CREATED = 201;
  private static final int HTTP_BAD_REQUEST = 400;
  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_NO_CONTENT = 204;
  private static final int HTTP_OK = 200;

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
    when(reservationService.createReservation(any(ReservationDto.class), eq(TEST_RESTAURANT_ID)))
        .thenReturn(TEST_RESERVATION_ID);

    String reservationJson =
        createReservationJson(
            TEST_DATE,
            TEST_START_TIME,
            TEST_GROUP_SIZE,
            TEST_CUSTOMER_NAME,
            TEST_EMAIL,
            TEST_PHONE);

    try (Response response =
        target("/restaurants/" + TEST_RESTAURANT_ID + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(HTTP_CREATED, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/reservations/" + TEST_RESERVATION_ID));

      verify(reservationService)
          .createReservation(any(ReservationDto.class), eq(TEST_RESTAURANT_ID));
    }
  }

  @Test
  public void givenEndTimeAfterClosing_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(
            new InvalidParameterException(
                "The restaurant closes before the end of the reservation"));

    String reservationJson =
        createReservationJson(
            TEST_DATE,
            LATE_START_TIME,
            SMALL_GROUP_SIZE,
            TEST_CUSTOMER_NAME,
            TEST_EMAIL,
            SHORT_PHONE);

    try (Response response =
        target("/restaurants/" + TEST_RESTAURANT_ID + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("customer"));
    }
  }

  @Test
  public void givenNonExistentRestaurant_whenPost_thenReturns404() {
    when(reservationService.createReservation(any(ReservationDto.class), eq(NONEXISTENT_ID)))
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

      assertEquals(HTTP_NOT_FOUND, response.getStatus());
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

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  public void givenValidNumber_whenGet_thenReturns200WithFullBody() {
    ReservationResponseDto mockDto = createMockReservationResponseDto();
    when(reservationService.getReservationById(TEST_RESERVATION_ID)).thenReturn(mockDto);

    try (Response response =
        target("/reservations/reservation-123").request(MediaType.APPLICATION_JSON).get()) {

      assertEquals(HTTP_OK, response.getStatus());

      String body = response.readEntity(String.class);
      assertTrue(body.contains("number"));
      assertTrue(body.contains("date"));
      assertTrue(body.contains("time"));
      assertTrue(body.contains("start"));
      assertTrue(body.contains("end"));
      assertTrue(body.contains("groupSize"));
      assertTrue(body.contains("customer"));
      assertTrue(body.contains("restaurant"));

      verify(reservationService).getReservationById(TEST_RESERVATION_ID);
    }
  }

  @Test
  public void givenNonExistentNumber_whenGet_thenReturns404() {
    when(reservationService.getReservationById(NONEXISTENT_ID)).thenThrow(new NotFoundException());

    try (Response response =
        target("/reservations/nonexistent").request(MediaType.APPLICATION_JSON).get()) {

      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  private String createReservationJson(
      String date, String startTime, int groupSize, String name, String email, String phone) {
    return String.format(
        """
        {
          "date": "%s",
          "startTime": "%s",
          "groupSize": %d,
          "customer": {
            "name": "%s",
            "email": "%s",
            "phoneNumber": "%s"
          }
        }
        """,
        date, startTime, groupSize, name, email, phone);
  }

  private String createReservationJsonWithoutDate(
      String startTime, int groupSize, String name, String email, String phone) {
    return String.format(
        """
        {
          "startTime": "%s",
          "groupSize": %d,
          "customer": {
            "name": "%s",
            "email": "%s",
            "phoneNumber": "%s"
          }
        }
        """,
        startTime, groupSize, name, email, phone);
  }

  private String createReservationJsonWithoutCustomer(
      String date, String startTime, int groupSize) {
    return String.format(
        """
        {
          "date": "%s",
          "startTime": "%s",
          "groupSize": %d
        }
        """,
        date, startTime, groupSize);
  }

  private ReservationResponseDto createMockReservationResponseDto() {
    return new ReservationResponseDto(
        TEST_RESERVATION_ID,
        LocalDate.of(2025, 10, 30),
        new ReservationTimeDto(LocalTime.of(18, 0), LocalTime.of(19, 0)),
        TEST_GROUP_SIZE,
        new CustomerDto(TEST_CUSTOMER_NAME, TEST_EMAIL, TEST_PHONE),
        new RestaurantReservationResponseDto(
            TEST_RESTAURANT_ID,
            "Pizza Delamama",
            50,
            new HoursDto(LocalTime.of(11, 0), LocalTime.of(22, 0))));
  }

  @Test
  public void givenExistingReservation_whenDelete_thenReturns204() {
    String reservationNumber = TEST_RESERVATION_ID;
    doNothing().when(reservationService).deleteReservation(reservationNumber);

    try (Response response = target("/reservations/" + reservationNumber).request().delete()) {

      assertEquals(HTTP_NO_CONTENT, response.getStatus());
      verify(reservationService).deleteReservation(reservationNumber);
    }
  }
}
