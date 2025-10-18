package ca.ulaval.glo2003.api.exceptions;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProcessingExceptionMapperTest {

  private ProcessingExceptionMapper mapper;

  @BeforeEach
  public void setUp() {
    mapper = new ProcessingExceptionMapper();
  }

  @Test
  void givenProcessingException_whenToResponse_thenReturns400() {
    ProcessingException exception = new ProcessingException("Invalid parameter");

    try (Response response = mapper.toResponse(exception)) {
      assertEquals(400, response.getStatus());
    }
  }

  @Test
  void givenProcessingException_whenToResponse_thenReturnsJsonMediaType() {
    ProcessingException exception = new ProcessingException("Invalid parameter");

    try (Response response = mapper.toResponse(exception)) {
      assertNotNull(response.getMediaType());
      assertThat(response.getMediaType().toString()).contains(MediaType.APPLICATION_JSON);
    }
  }

  @Test
  void givenProcessingException_whenToResponse_thenErrorResponseContainsCorrectCode() {
    ProcessingException exception = new ProcessingException("Invalid parameter");

    ErrorResponse errorResponse;
    try (Response response = mapper.toResponse(exception)) {
      errorResponse = (ErrorResponse) response.getEntity();
    }

    assertThat(errorResponse.getError()).isEqualTo(ErrorCodes.ERROR_INVALID_PARAMETER);
  }

  @Test
  void givenProcessingException_whenToResponse_thenErrorResponseContainsCorrectDescription() {
    ProcessingException exception = new ProcessingException("Invalid parameter");

    ErrorResponse errorResponse;
    try (Response response = mapper.toResponse(exception)) {
      errorResponse = (ErrorResponse) response.getEntity();
    }

    assertThat(errorResponse.getDescription()).isEqualTo("The parameter type is invalid");
  }

  @Test
  void givenProcessingExceptionWithDifferentMessage_whenToResponse_thenDescriptionIsAlwaysSame() {
    ProcessingException exception1 = new ProcessingException("Message 1");
    ProcessingException exception2 = new ProcessingException("Message 2");

    try (Response response1 = mapper.toResponse(exception1);
        Response response2 = mapper.toResponse(exception2)) {

      ErrorResponse errorResponse1 = (ErrorResponse) response1.getEntity();
      ErrorResponse errorResponse2 = (ErrorResponse) response2.getEntity();

      assertThat(errorResponse1.getDescription()).isEqualTo("The parameter type is invalid");
      assertThat(errorResponse2.getDescription()).isEqualTo("The parameter type is invalid");
    }
  }

  @Test
  void givenProcessingExceptionWithNullMessage_whenToResponse_thenReturns400() {
    ProcessingException exception = new ProcessingException((String) null);

    try (Response response = mapper.toResponse(exception)) {
      assertEquals(400, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  void givenProcessingException_whenToResponse_thenEntityIsNotNull() {
    ProcessingException exception = new ProcessingException("Invalid parameter");

    try (Response response = mapper.toResponse(exception)) {
      assertNotNull(response.getEntity());
      assertThat(response.getEntity()).isInstanceOf(ErrorResponse.class);
    }
  }

  @Test
  void givenMultipleCalls_whenToResponse_thenEachReturnsIndependentResponse() {
    ProcessingException exception1 = new ProcessingException("First exception");
    ProcessingException exception2 = new ProcessingException("Second exception");

    try (Response response1 = mapper.toResponse(exception1);
        Response response2 = mapper.toResponse(exception2)) {

      assertEquals(400, response1.getStatus());
      assertEquals(400, response2.getStatus());
      assertNotNull(response1.getEntity());
      assertNotNull(response2.getEntity());
    }
  }
}
