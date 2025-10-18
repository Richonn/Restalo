package ca.ulaval.glo2003.api.exceptions;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ErrorResponseTest {

  @Test
  void givenErrorAndDescription_whenCreateErrorResponse_thenFieldsAreSet() {
    ErrorResponse errorResponse = new ErrorResponse("ERROR_CODE", "Error description");

    assertThat(errorResponse.getError()).isEqualTo("ERROR_CODE");
    assertThat(errorResponse.getDescription()).isEqualTo("Error description");
  }

  @Test
  void givenNoArguments_whenCreateErrorResponse_thenFieldsAreNull() {
    ErrorResponse errorResponse = new ErrorResponse();

    assertNull(errorResponse.getError());
    assertNull(errorResponse.getDescription());
  }

  @Test
  void givenValidError_whenSetError_thenErrorIsSet() {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setError("NEW_ERROR_CODE");

    assertThat(errorResponse.getError()).isEqualTo("NEW_ERROR_CODE");
  }

  @Test
  void givenValidDescription_whenSetDescription_thenDescriptionIsSet() {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setDescription("New error description");

    assertThat(errorResponse.getDescription()).isEqualTo("New error description");
  }

  @Test
  void givenNullError_whenSetError_thenErrorIsNull() {
    ErrorResponse errorResponse = new ErrorResponse("ERROR_CODE", "Description");

    errorResponse.setError(null);

    assertNull(errorResponse.getError());
  }

  @Test
  void givenNullDescription_whenSetDescription_thenDescriptionIsNull() {
    ErrorResponse errorResponse = new ErrorResponse("ERROR_CODE", "Description");

    errorResponse.setDescription(null);

    assertNull(errorResponse.getDescription());
  }

  @Test
  void givenEmptyError_whenSetError_thenErrorIsEmpty() {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setError("");

    assertThat(errorResponse.getError()).isEqualTo("");
  }

  @Test
  void givenEmptyDescription_whenSetDescription_thenDescriptionIsEmpty() {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setDescription("");

    assertThat(errorResponse.getDescription()).isEqualTo("");
  }

  @Test
  void givenMultipleSetCalls_whenSetError_thenLastValueIsKept() {
    ErrorResponse errorResponse = new ErrorResponse("FIRST_ERROR", "Description");

    errorResponse.setError("SECOND_ERROR");
    errorResponse.setError("THIRD_ERROR");

    assertThat(errorResponse.getError()).isEqualTo("THIRD_ERROR");
  }

  @Test
  void givenMultipleSetCalls_whenSetDescription_thenLastValueIsKept() {
    ErrorResponse errorResponse = new ErrorResponse("ERROR_CODE", "First description");

    errorResponse.setDescription("Second description");
    errorResponse.setDescription("Third description");

    assertThat(errorResponse.getDescription()).isEqualTo("Third description");
  }

  @Test
  void givenErrorResponse_whenSetBothFields_thenBothFieldsAreUpdated() {
    ErrorResponse errorResponse = new ErrorResponse("OLD_ERROR", "Old description");

    errorResponse.setError("NEW_ERROR");
    errorResponse.setDescription("New description");

    assertThat(errorResponse.getError()).isEqualTo("NEW_ERROR");
    assertThat(errorResponse.getDescription()).isEqualTo("New description");
  }
}
