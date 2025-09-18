package ca.ulaval.glo2003.Restaurant;

public class ErrorResponse {
  public String error;
  public String description;

  public ErrorResponse(String error, String description) {
    this.error = error;
    this.description = description;
  }
}
