package ca.ulaval.glo2003.api.exceptions;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo2003.api.exceptions.mappers.NullPointerExceptionMapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NullPointerExceptionMapperTest {

  private NullPointerExceptionMapper mapper;

  @BeforeEach
  public void setUp() {
    mapper = new NullPointerExceptionMapper();
  }

  @Test
  void givenNullPointerException_whenToResponse_thenReturns400WithErrorResponse() {
    NullPointerException exception =
        new NullPointerException("Cannot invoke method on null object");

    try (Response response = mapper.toResponse(exception)) {

      assertEquals(400, response.getStatus());
      assertNotNull(response.getEntity());
      assertThat(response.getMediaType().toString()).contains(MediaType.APPLICATION_JSON);
    }
  }

  @Test
  void givenNullPointerException_whenToResponse_thenErrorResponseContainsCorrectCode() {
    NullPointerException exception =
        new NullPointerException("Cannot invoke method on null object");

    ErrorResponse errorResponse;
    try (Response response = mapper.toResponse(exception)) {
      errorResponse = (ErrorResponse) response.getEntity();
    }

    assertThat(errorResponse.getError()).isEqualTo(ErrorCodes.ERROR_MISSING_PARAMETER);
  }

  @Test
  void givenNullPointerException_whenToResponse_thenErrorResponseContainsExceptionMessage() {
    String expectedMessage = "Cannot invoke method on null object";
    NullPointerException exception = new NullPointerException(expectedMessage);

    ErrorResponse errorResponse;
    try (Response response = mapper.toResponse(exception)) {
      errorResponse = (ErrorResponse) response.getEntity();
    }

    assertThat(errorResponse.getDescription()).isEqualTo(expectedMessage);
  }

  @Test
  void givenNullPointerExceptionWithNullMessage_whenToResponse_thenReturns400() {
    NullPointerException exception = new NullPointerException();

    try (Response response = mapper.toResponse(exception)) {

      assertEquals(400, response.getStatus());
      assertNotNull(response.getEntity());
    }
  }

  @Test
  void
      givenNullPointerExceptionWithEmptyMessage_whenToResponse_thenReturns400WithEmptyDescription() {
    NullPointerException exception = new NullPointerException("");

    ErrorResponse errorResponse;
    try (Response response = mapper.toResponse(exception)) {
      errorResponse = (ErrorResponse) response.getEntity();

      assertEquals(400, response.getStatus());
    }
    assertThat(errorResponse.getDescription()).isEqualTo("");
  }

  @Test
  void givenMultipleCalls_whenToResponse_thenEachReturnsIndependentResponse() {
    NullPointerException exception1 = new NullPointerException("First error");
    NullPointerException exception2 = new NullPointerException("Second error");

    try (Response response1 = mapper.toResponse(exception1);
        Response response2 = mapper.toResponse(exception2)) {

      ErrorResponse errorResponse1 = (ErrorResponse) response1.getEntity();
      ErrorResponse errorResponse2 = (ErrorResponse) response2.getEntity();

      assertThat(errorResponse1.getDescription()).isEqualTo("First error");
      assertThat(errorResponse2.getDescription()).isEqualTo("Second error");
    }
  }
}
