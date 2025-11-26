package ca.ulaval.glo2003.api.ressources.order;

import ca.ulaval.glo2003.api.constants.ApiValidationMessages;
import ca.ulaval.glo2003.api.dto.common.OrderDto;
import ca.ulaval.glo2003.application.order.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

  private final OrderService orderService;

  private static final String BASE_URL = "http://0.0.0.0:8080";
  private static final String ORDER_BASE = BASE_URL + "/commandes";
  private static final String ORDER_BY_ID = ORDER_BASE + "/%s";

  @Inject
  public OrderResource(OrderService orderService) {
    this.orderService = orderService;
  }

  @POST
  @Path("restaurants/{id}/commandes")
  public Response createRestaurant(
      @Valid @NotNull(message = ApiValidationMessages.MISSING_BODY) OrderDto orderDto,
      @PathParam("id") String restaurantId) {

    String orderGeneratedId = orderService.createOrder(restaurantId, orderDto);

    return Response.status(Response.Status.CREATED)
        .header("Location", String.format(ORDER_BY_ID, orderGeneratedId))
        .build();
  }
}
