package ca.ulaval.glo2003.API.Restaurant.Exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Handle null pointer exception. */
@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {

  @Override
  public Response toResponse(NullPointerException exception) {
    ErrorResponse err = new ErrorResponse("MISSING_PARAMETER", exception.getMessage());
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
