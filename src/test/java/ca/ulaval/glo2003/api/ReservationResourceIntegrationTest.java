package ca.ulaval.glo2003.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.api.dto.common.ReservationDto;
import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.api.dto.response.ReservationSearchResponseDto;
import ca.ulaval.glo2003.api.dto.response.RestaurantSearchAvailabilitiesResponseDto;
import ca.ulaval.glo2003.api.ressources.reservation.ReservationResource;
import ca.ulaval.glo2003.application.reservation.ReservationService;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationResourceIntegrationTest extends JerseyTest {

  private static final String RESTAURANT_ID = "restaurant-123";
  private static final String RESERVATION_ID = "reservation-123";

  private static final int HTTP_CREATED = 201;
  private static final int HTTP_BAD_REQUEST = 400;
  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_NO_CONTENT = 204;
  private static final int HTTP_OK = 200;

  private static final String OWNER_ID = "yvick";

  private ReservationService reservationService;
  private ReservationRessourceIntegrationTestFixture reservationResourceIntegrationTestFixture;

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

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    reservationResourceIntegrationTestFixture =
        new ReservationRessourceIntegrationTestFixture(OWNER_ID);
    super.setUp();
  }

  @Test
  void givenValidReservation_whenPost_thenReturns201WithLocation() {
    when(reservationService.createReservation(any(ReservationDto.class), eq(RESTAURANT_ID)))
        .thenReturn(RESERVATION_ID);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .buildPostResponse(target())) {

      assertEquals(HTTP_CREATED, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/reservations/" + RESERVATION_ID));

      verify(reservationService).createReservation(any(ReservationDto.class), eq(RESTAURANT_ID));
    }
  }

  @Test
  void givenEndTimeAfterClosing_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(
            new InvalidParameterException(
                "The restaurant closes before the end of the reservation"));

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("closes before"));
    }
  }

  @Test
  void givenGroupSizeZero_whenPost_thenReturns400() {
    when(reservationService.createReservation(any(ReservationDto.class), any()))
        .thenThrow(new InvalidParameterException("The group size must be positive"));

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withReservationGroupSize(0)
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(
          body.contains("INVALID_PARAMETER")
              || body.contains("group size")
              || body.contains("positive"));
    }
  }

  @Test
  void givenEmptyName_whenPost_thenReturns400() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withReservationCustomerName("")
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("name"));
    }
  }

  @Test
  void givenInvalidEmail_whenPost_thenReturns400() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withReservationCustomerEmail("invalidEmail")
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("email"));
    }
  }

  @Test
  void givenInvalidPhone_whenPost_thenReturns400() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withReservationCustomerPhone("123")
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("phone"));
    }
  }

  @Test
  void givenMissingDate_whenPost_thenReturns400() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeReservationDate(false)
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("date"));
    }
  }

  @Test
  void givenMissingCustomer_whenPost_thenReturns400() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeCustomer(false)
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("customer"));
    }
  }

  @Test
  void givenNonExistentRestaurant_whenPost_thenReturns404() {
    String restaurantId = "invalid";

    when(reservationService.createReservation(any(ReservationDto.class), eq(restaurantId)))
        .thenThrow(new NotFoundException());

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", restaurantId))
            .buildPostResponse(target())) {

      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  void givenMissingBody_whenPost_thenReturns400() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeBody(false)
            .buildPostResponse(target())) {

      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  void givenValidNumber_whenGet_thenReturns200WithFullBody() {
    ReservationResponseDto reservationResponse = mock(ReservationResponseDto.class);
    when(reservationService.getReservationById(RESERVATION_ID)).thenReturn(reservationResponse);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/reservations/%s", RESERVATION_ID))
            .buildGetResponse(target())) {

      assertEquals(HTTP_OK, response.getStatus());
      assertNotNull(response.readEntity(String.class));
      verify(reservationService).getReservationById(RESERVATION_ID);
    }
  }

  @Test
  void givenNonExistentNumber_whenGet_thenReturns404() {
    String reservationId = "invalid";

    when(reservationService.getReservationById(reservationId)).thenThrow(new NotFoundException());

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/reservations/%s", reservationId))
            .buildGetResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithCorrectDateAndCustomerName_thenReturns200WithExpectedReservations() {
    String date = "2024-10-10";
    String customerName = "John";
    List<ReservationSearchResponseDto> expectedReservationList =
        List.of(
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class));

    when(reservationService.getReservationBySearch(OWNER_ID, RESTAURANT_ID, date, customerName))
        .thenReturn(expectedReservationList);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeHeaders(true)
            .withDateQueryParam(date)
            .withCustomerNameQueryParam(customerName)
            .buildGetResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertEquals(expectedReservationList.size(), response.readEntity(List.class).size());
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithCorrectDateAndNoCustomerName_thenReturns200WithExpectedReservations() {
    String date = "2024-10-10";
    String customerName = null;
    List<ReservationSearchResponseDto> expectedReservationList =
        List.of(
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class));

    when(reservationService.getReservationBySearch(OWNER_ID, RESTAURANT_ID, date, customerName))
        .thenReturn(expectedReservationList);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeHeaders(true)
            .withDateQueryParam(date)
            .buildGetResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertEquals(expectedReservationList.size(), response.readEntity(List.class).size());
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithCorrectCustomerNameAndNoDate_thenReturns200WithExpectedReservations() {
    String date = null;
    String customerName = "John";
    List<ReservationSearchResponseDto> expectedReservationList =
        List.of(
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class));

    when(reservationService.getReservationBySearch(OWNER_ID, RESTAURANT_ID, date, customerName))
        .thenReturn(expectedReservationList);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeHeaders(true)
            .withCustomerNameQueryParam(customerName)
            .buildGetResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertEquals(expectedReservationList.size(), response.readEntity(List.class).size());
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithNoQueryParam_thenReturns200WithExpectedReservations() {
    String date = null;
    String customerName = null;
    List<ReservationSearchResponseDto> expectedReservationList =
        List.of(
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class),
            mock(ReservationSearchResponseDto.class));

    when(reservationService.getReservationBySearch(OWNER_ID, RESTAURANT_ID, date, customerName))
        .thenReturn(expectedReservationList);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withIncludeHeaders(true)
            .buildGetResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertEquals(expectedReservationList.size(), response.readEntity(List.class).size());
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithMissingHeader_thenReturns400MissingParameter() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .buildGetResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithNonExistentId_thenReturns404NotFound() {
    String restaurantId = "invalid";
    when(reservationService.getReservationBySearch(OWNER_ID, restaurantId, null, null))
        .thenThrow(new NotFoundException());
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", restaurantId))
            .withIncludeHeaders(true)
            .buildGetResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  void
      givenExistingReservationForRestaurant_whenGetReservationBySearchWithOwnerIdDontOwnRestaurant_thenReturns404NotFound() {
    String ownerId = "invalid";
    when(reservationService.getReservationBySearch(ownerId, RESTAURANT_ID, null, null))
        .thenThrow(new NotFoundException());
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/reservations", RESTAURANT_ID))
            .withOwnerId(ownerId)
            .withIncludeHeaders(true)
            .buildGetResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  void
      givenExistingRestaurant_whenGetRestaurantAvailabilitiesByDate_thenReturns200WithExpectedAvailabilities() {
    String date = "2024-10-10";
    List<RestaurantSearchAvailabilitiesResponseDto> expectedAvailabilitiesList =
        List.of(
            mock(RestaurantSearchAvailabilitiesResponseDto.class),
            mock(RestaurantSearchAvailabilitiesResponseDto.class),
            mock(RestaurantSearchAvailabilitiesResponseDto.class));

    when(reservationService.getRestaurantAvailabilitiesByDate(RESTAURANT_ID, date))
        .thenReturn(expectedAvailabilitiesList);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/availabilities", RESTAURANT_ID))
            .withDateQueryParam(date)
            .buildGetResponse(target())) {
      assertEquals(HTTP_OK, response.getStatus());
      assertEquals(expectedAvailabilitiesList.size(), response.readEntity(List.class).size());
    }
  }

  @Test
  void
      givenExistingRestaurant_whenGetRestaurantAvailabilitiesByDateWithNoDate_thenReturns400MissingParameter() {
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/availabilities", RESTAURANT_ID))
            .buildGetResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER"));
    }
  }

  @Test
  void
      givenExistingRestaurant_whenGetRestaurantAvailabilitiesByDateWithInvalidDate_thenReturns400InvalidParameter() {
    String date = "invalidDate";
    when(reservationService.getRestaurantAvailabilitiesByDate(RESTAURANT_ID, date))
        .thenThrow(new InvalidParameterException("Date invalid"));
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/availabilities", RESTAURANT_ID))
            .withDateQueryParam(date)
            .buildGetResponse(target())) {
      assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }
  }

  @Test
  void
      givenExistingRestaurant_whenGetRestaurantAvailabilitiesByDateWithNotExistentRestaurantId_thenReturns404NotFound() {
    String restaurantId = "invalid";
    String date = "2024-10-10";
    when(reservationService.getRestaurantAvailabilitiesByDate(restaurantId, date))
        .thenThrow(new NotFoundException());
    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/restaurants/%s/availabilities", restaurantId))
            .withDateQueryParam(date)
            .buildGetResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
  }

  @Test
  void givenExistingReservation_whenDeleteReservation_thenReturns204NoContent() {

    doNothing().when(reservationService).deleteReservation(RESERVATION_ID);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/reservations/%s", RESERVATION_ID))
            .buildDeleteResponse(target())) {
      assertEquals(HTTP_NO_CONTENT, response.getStatus());
      verify(reservationService).deleteReservation(RESERVATION_ID);
    }
  }

  @Test
  void
      givenExistingReservation_whenDeleteReservationWithNotExistentReservationId_thenReturns404NotFound() {
    String reservationId = "invalid";
    doThrow(new NotFoundException()).when(reservationService).deleteReservation(reservationId);

    try (Response response =
        reservationResourceIntegrationTestFixture
            .withPath(String.format("/reservations/%s", reservationId))
            .buildDeleteResponse(target())) {
      assertEquals(HTTP_NOT_FOUND, response.getStatus());
      verify(reservationService).deleteReservation(reservationId);
    }
  }

}
