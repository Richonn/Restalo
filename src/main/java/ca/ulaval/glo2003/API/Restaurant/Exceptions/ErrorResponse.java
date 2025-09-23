package ca.ulaval.glo2003.API.Restaurant.Exceptions;

public class ErrorResponse {
  private String error;
  private String description;

  public ErrorResponse() {}

  public ErrorResponse(String error, String description) {
    this.error = error;
    this.description = description;
  }

  public String getError() {
    return error;
  }

  public String getDescription() {
    return description;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
