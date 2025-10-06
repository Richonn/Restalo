package ca.ulaval.glo2003.domain.exceptions;

public class InvalidParameterException extends IllegalArgumentException {
  public InvalidParameterException(String message) {
    super(message);
  }
}
