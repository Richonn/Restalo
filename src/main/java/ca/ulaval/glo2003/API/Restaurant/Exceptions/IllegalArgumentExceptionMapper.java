package ca.ulaval.glo2003.API.Restaurant.Exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

  @Override
  public Response toResponse(IllegalArgumentException exception) {
    ErrorResponse err = new ErrorResponse("INVALID_PARAMETER", exception.getMessage());
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
