package ca.ulaval.glo2003.api.exceptions.mappers;

import static ca.ulaval.glo2003.api.exceptions.ErrorCodes.ERROR_MISSING_PARAMETER;

import ca.ulaval.glo2003.api.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    ErrorResponse err = new ErrorResponse(ERROR_MISSING_PARAMETER, exception.getMessage());
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
