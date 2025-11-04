package ca.ulaval.glo2003.api.ressources.reservation;

import ca.ulaval.glo2003.api.dto.common.ReservationDto;
import ca.ulaval.glo2003.api.dto.response.ReservationResponseDto;
import ca.ulaval.glo2003.application.reservation.ReservationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {
  private final ReservationService reservationService;

  private static final String OWNER_HEADER = "Owner";
  private static final String BASE_URL = "http://0.0.0.0:8080";
  private static final String RESERVATION_BASE = BASE_URL + "/reservations";
  private static final String RESERVATION_BY_ID = RESERVATION_BASE + "/%s";

  @Inject
  public ReservationResource(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Path("restaurants/{id}/reservations")
  @POST
  public Response createReservation(
      ReservationDto reservationDto, @PathParam("id") String restaurantId) {

    String reservationGeneratedId =
        reservationService.createReservation(reservationDto, restaurantId);

    return Response.status(Response.Status.CREATED)
        .header("Location", String.format(RESERVATION_BY_ID, reservationGeneratedId))
        .build();
  }

  @GET
  @Path("reservations/{number}")
  public Response getReservation(@PathParam("number") String reservationNumber) {

    ReservationResponseDto reservation = reservationService.getReservationById(reservationNumber);

    return Response.status(Response.Status.OK).entity(reservation).build();
  }

  @GET
  @Path("restaurants/{id}/reservations")
  public Response searchReservations(
      @HeaderParam(OWNER_HEADER) String ownerId,
      @PathParam("id") String restaurantId,
      @QueryParam("date") String dateString,
      @QueryParam("customerName") String customerName) {
    return Response.status(Response.Status.OK)
        .entity(
            reservationService.getReservationBySearch(
                ownerId, restaurantId, dateString, customerName))
        .build();
  }
}
