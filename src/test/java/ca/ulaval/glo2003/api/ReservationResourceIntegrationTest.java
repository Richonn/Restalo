package ca.ulaval.glo2003.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo2003.ApplicationBinder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationResourceIntegrationTest extends JerseyTest {

  private static final String OWNER_ID = "yvick";
  private String restaurantId;

  @Override
  protected Application configure() {
    return new ResourceConfig().packages("ca.ulaval.glo2003").register(new ApplicationBinder());
  }

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    super.setUp();
    restaurantId = createTestRestaurant();
  }

  @Test
  public void givenValidReservation_whenPost_thenReturns201WithLocation() {
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
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(201, response.getStatus());
      String location = response.getHeaderString("Location");
      assertNotNull(location);
      assertTrue(location.contains("/reservations/"));
    }
  }

  @Test
  public void givenStartTime21h04_whenPost_thenAdjustedTo21h15() {
    String lateRestaurantId = createRestaurantWithHours("11:00:00", "23:00:00");

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "21:04:00",
          "groupSize": 2,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "5551234567"
          }
        }
        """;

    try (Response response =
        target("/restaurants/" + lateRestaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(201, response.getStatus());
    }
  }

  @Test
  public void givenStartTime21h15_whenPost_thenNoAdjustment() {
    String lateRestaurantId = createRestaurantWithHours("11:00:00", "23:00:00");

    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "21:15:00",
          "groupSize": 2,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "5559876543"
          }
        }
        """;

    try (Response response =
        target("/restaurants/" + lateRestaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(201, response.getStatus());
    }
  }

  @Test
  public void givenSameCustomer_whenCreateMultipleReservations_thenBothAccepted() {
    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4,
          "customer": {
            "name": "John Doe",
            "email": "john.doe@example.com",
            "phoneNumber": "1234567890"
          }
        }
        """;

    try (Response response1 =
            target("/restaurants/" + restaurantId + "/reservations")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(reservationJson));
        Response response2 =
            target("/restaurants/" + restaurantId + "/reservations")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(reservationJson))) {

      assertEquals(201, response1.getStatus());
      assertEquals(201, response2.getStatus());

      String location1 = response1.getHeaderString("Location");
      String location2 = response2.getHeaderString("Location");

      assertNotNull(location1);
      assertNotNull(location2);
      assertNotEquals(location1, location2);
    }
  }

  @Test
  public void givenMultipleReservations_whenPost_thenUniqueNumbers() {
    String reservation1Json =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 2,
          "customer": {
            "name": "John Doe",
            "email": "john@example.com",
            "phoneNumber": "1111111111"
          }
        }
        """;

    String reservation2Json =
        """
        {
          "date": "2025-10-30",
          "startTime": "17:00:00",
          "groupSize": 3,
          "customer": {
            "name": "John",
            "email": "john@example.com",
            "phoneNumber": "2222222222"
          }
        }
        """;

    try (Response response1 =
            target("/restaurants/" + restaurantId + "/reservations")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(reservation1Json));
        Response response2 =
            target("/restaurants/" + restaurantId + "/reservations")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(reservation2Json))) {

      assertEquals(201, response1.getStatus());
      assertEquals(201, response2.getStatus());

      String location1 = response1.getHeaderString("Location");
      String location2 = response2.getHeaderString("Location");

      String id1 = extractReservationIdFromLocation(location1);
      String id2 = extractReservationIdFromLocation(location2);

      assertNotEquals(id1, id2);
    }
  }

  @Test
  public void givenEndTimeAfterClosing_whenPost_thenReturns400() {
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
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("closes before"));
    }
  }

  @Test
  public void givenGroupSizeZero_whenPost_thenReturns400() {
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
        target("/restaurants/" + restaurantId + "/reservations")
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
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("name"));
    }
  }

  @Test
  public void givenInvalidEmail_whenPost_thenReturns400() {
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
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("email"));
    }
  }

  @Test
  public void givenInvalidPhone_whenPost_thenReturns400() {
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
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("INVALID_PARAMETER") || body.contains("phone"));
    }
  }

  @Test
  public void givenMissingDate_whenPost_thenReturns400() {
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
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("date"));
    }
  }

  @Test
  public void givenMissingCustomer_whenPost_thenReturns400() {
    String reservationJson =
        """
        {
          "date": "2025-10-30",
          "startTime": "18:00:00",
          "groupSize": 4
        }
        """;

    try (Response response =
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(400, response.getStatus());
      String body = response.readEntity(String.class);
      assertTrue(body.contains("MISSING_PARAMETER") || body.contains("customer"));
    }
  }

  @Test
  public void givenNonExistentRestaurant_whenPost_thenReturns404() {
    String fakeRestaurantId = "nonexistent-restaurant-id";
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
        target("/restaurants/" + fakeRestaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(reservationJson))) {
      assertEquals(404, response.getStatus());
    }
  }

  @Test
  public void givenMissingBody_whenPost_thenReturns400() {
    try (Response response =
        target("/restaurants/" + restaurantId + "/reservations")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null))) {
      assertEquals(400, response.getStatus());
    }
  }

  private String createTestRestaurant() {
    return createRestaurantWithHours("11:00:00", "19:00:00");
  }

  private String createRestaurantWithHours(String openingHour, String closingHour) {
    String restaurantJson =
        String.format(
            """
            {
              "name": "Test Restaurant",
              "capacity": 50,
              "hours": {
                "open": "%s",
                "close": "%s"
              }
            }
            """,
            openingHour, closingHour);

    try (Response response =
        target("/restaurants")
            .request(MediaType.APPLICATION_JSON)
            .header("Owner", OWNER_ID)
            .post(Entity.json(restaurantJson))) {
      assertEquals(201, response.getStatus());
      String location = response.getHeaderString("Location");
      return extractRestaurantIdFromLocation(location);
    }
  }

  private String extractRestaurantIdFromLocation(String location) {
    return location.substring(location.lastIndexOf("/") + 1);
  }

  private String extractReservationIdFromLocation(String location) {
    return location.substring(location.lastIndexOf("/") + 1);
  }
}
