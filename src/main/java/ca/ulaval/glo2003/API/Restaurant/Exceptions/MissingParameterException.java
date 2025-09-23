package ca.ulaval.glo2003.API.Restaurant.Exceptions;

public class MissingParameterException extends IllegalArgumentException {
  public MissingParameterException(String message) {
    super(message);
  }
}
