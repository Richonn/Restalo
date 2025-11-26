package ca.ulaval.glo2003.api.exceptions.mappers;

import static ca.ulaval.glo2003.api.exceptions.ErrorCodes.ERROR_MISSING_PARAMETER;

import ca.ulaval.glo2003.api.dto.response.ErrorResponse;
import io.sentry.Sentry;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    Sentry.captureException(exception);
    Sentry.flush(2000);
    List<String> messages =
        exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
    String message = messages.getLast();

    ErrorResponse err = new ErrorResponse(ERROR_MISSING_PARAMETER, message);
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
