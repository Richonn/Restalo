package ca.ulaval.glo2003.api.exceptions;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundRequestMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
  }
}
