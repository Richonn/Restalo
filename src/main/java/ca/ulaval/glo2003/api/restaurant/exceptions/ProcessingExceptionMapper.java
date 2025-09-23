package ca.ulaval.glo2003.api.restaurant.exceptions;

import static ca.ulaval.glo2003.API.Restaurant.Exceptions.ErrorCodes.*;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

  @Override
  public Response toResponse(ProcessingException exception) {
    ErrorResponse err = new ErrorResponse(ERROR_INVALID_PARAMETER, "The parameter type is invalid");
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
