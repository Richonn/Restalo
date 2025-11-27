package ca.ulaval.glo2003.api.ressources.restaurant;

import ca.ulaval.glo2003.api.dto.request.SpecialOfferRequestDto;
import ca.ulaval.glo2003.application.restaurant.SpecialOfferService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecialOfferResource {

  private final SpecialOfferService specialOfferService;

  private static final String OWNER_HEADER = "Owner";
  private static final String BASE_URL = "http://0.0.0.0:8080";
  private static final String OFFER_BASE = BASE_URL + "/restaurants/%s/%s";

  @Inject
  public SpecialOfferResource(SpecialOfferService specialOfferService) {
    this.specialOfferService = specialOfferService;
  }

  @POST
  @Path("restaurants/{id}/special-offers")
  public Response createSpecialOffer(
      @NotNull @NotBlank @HeaderParam(OWNER_HEADER) String ownerId,
      @PathParam("id") String restaurantId,
      @Valid @NotNull SpecialOfferRequestDto request) {
    String offerId =
        specialOfferService.createSpecialOffer(
            ownerId,
            restaurantId,
            request.offer().title(),
            request.offer().description(),
            request.offer().validFrom(),
            request.offer().validUntil());

    return Response.status(Response.Status.CREATED)
        .header("Location", String.format(OFFER_BASE, restaurantId, offerId))
        .build();
  }
}
