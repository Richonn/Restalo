package ca.ulaval.glo2003.api.exceptions.mappers;

import ca.ulaval.glo2003.api.dto.response.ErrorResponse;
import ca.ulaval.glo2003.domain.exceptions.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    ErrorResponse err = new ErrorResponse("NOT_FOUND", exception.getMessage());
    return Response.status(Response.Status.NOT_FOUND)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
