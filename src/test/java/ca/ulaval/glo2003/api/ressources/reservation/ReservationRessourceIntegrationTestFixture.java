package ca.ulaval.glo2003.api.ressources.reservation;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ReservationRessourceIntegrationTestFixture {

  private String RESERVATION_DATE = "2025-10-30";
  private String RESERVATION_START_TIME = "18:00:00";
  private int RESERVATION_GROUP_SIZE = 4;
  private String RESERVATION_CUSTOMER_NAME = "John Doe";
  private String RESERVATION_CUSTOMER_EMAIL = "john@example.com";
  private String RESERVATION_CUSTOMER_PHONE = "1234567890";

  private Boolean INCLUDE_CUSTOMER = true;
  private Boolean INCLUDE_RESERVATION_DATE = true;
  private Boolean INCLUDE_HEADER = false;
  private Boolean INCLUDE_BODY = true;

  private String PATH = "";
  private String OWNER_ID;
  private String DATE_QUERY = null;
  private String CUSTOMER_NAME_QUERY = null;

  public ReservationRessourceIntegrationTestFixture(String ownerId) {
    this.OWNER_ID = ownerId;
  }

  public Response buildPostResponse(WebTarget target) {
    return buildPostResponse(target, buildReservationJson());
  }

  public Response buildGetResponse(WebTarget target) {
    WebTarget webTarget = target.path(PATH);

    if (DATE_QUERY != null) {
      webTarget = webTarget.queryParam("date", DATE_QUERY);
    }
    if (CUSTOMER_NAME_QUERY != null) {
      webTarget = webTarget.queryParam("customerName", CUSTOMER_NAME_QUERY);
    }

    Invocation.Builder requestBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }
    return requestBuilder.get();
  }

  public Response buildDeleteResponse(WebTarget target) {
    Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);
    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }
    return requestBuilder.delete();
  }

  public ReservationRessourceIntegrationTestFixture withDateQueryParam(String dateQuery) {
    this.DATE_QUERY = dateQuery;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withCustomerNameQueryParam(
      String customerNameQuery) {
    this.CUSTOMER_NAME_QUERY = customerNameQuery;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withIncludeReservationDate(
      boolean includeReservationDate) {
    INCLUDE_RESERVATION_DATE = includeReservationDate;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withIncludeCustomer(boolean includeCustomer) {
    this.INCLUDE_CUSTOMER = includeCustomer;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withPath(String path) {
    this.PATH = path;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withOwnerId(String ownerId) {
    this.OWNER_ID = ownerId;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withIncludeHeaders(boolean includeHeaders) {
    this.INCLUDE_HEADER = includeHeaders;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withIncludeBody(boolean includeBody) {
    this.INCLUDE_BODY = includeBody;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withReservationDate(String reservationDate) {
    this.RESERVATION_DATE = reservationDate;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withReservationStartTime(
      String reservationStartTime) {
    this.RESERVATION_START_TIME = reservationStartTime;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withReservationGroupSize(
      int reservationGroupSize) {
    this.RESERVATION_GROUP_SIZE = reservationGroupSize;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withReservationCustomerName(
      String reservationCustomerName) {
    this.RESERVATION_CUSTOMER_NAME = reservationCustomerName;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withReservationCustomerEmail(
      String reservationCustomerEmail) {
    this.RESERVATION_CUSTOMER_EMAIL = reservationCustomerEmail;
    return this;
  }

  public ReservationRessourceIntegrationTestFixture withReservationCustomerPhone(
      String reservationCustomerPhone) {
    this.RESERVATION_CUSTOMER_PHONE = reservationCustomerPhone;
    return this;
  }

  private Response buildPostResponse(WebTarget target, String json) {
    Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);

    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }

    if (INCLUDE_BODY) {
      return requestBuilder.post(Entity.json(json));
    }

    return requestBuilder.post(Entity.entity("null", MediaType.APPLICATION_JSON_TYPE));
  }

  private String buildReservationJson() {
    String dateJson = "";
    if (INCLUDE_RESERVATION_DATE) {
      dateJson =
          """
          ,
             "date": "%s"
          """
              .formatted(RESERVATION_DATE);
    }

    String customerJson = "";
    if (INCLUDE_CUSTOMER) {
      customerJson =
          """
              ,
              "customer": {
                "name": "%s",
                "email": "%s",
                "phoneNumber": "%s"
              }"""
              .formatted(
                  RESERVATION_CUSTOMER_NAME,
                  RESERVATION_CUSTOMER_EMAIL,
                  RESERVATION_CUSTOMER_PHONE);
    }
    return """
            {
              "startTime": "%s",
              "groupSize": %d%s%s
            }
            """
        .formatted(RESERVATION_START_TIME, RESERVATION_GROUP_SIZE, customerJson, dateJson);
  }
}
