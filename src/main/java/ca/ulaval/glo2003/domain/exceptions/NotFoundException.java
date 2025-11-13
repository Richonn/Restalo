package ca.ulaval.glo2003.domain.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}
