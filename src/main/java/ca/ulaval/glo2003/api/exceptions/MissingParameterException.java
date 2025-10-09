package ca.ulaval.glo2003.api.exceptions;

public class MissingParameterException extends IllegalArgumentException {
  public MissingParameterException(String message) {
    super(message);
  }
}
