package ca.ulaval.glo2003.api.restaurant.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Handle missing parameter exception. */
@Provider
public class MissingParameterExceptionMapper implements ExceptionMapper<MissingParameterException> {

  @Override
  public Response toResponse(MissingParameterException exception) {
    ErrorResponse err = new ErrorResponse("MISSING_PARAMETER", exception.getMessage());
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
