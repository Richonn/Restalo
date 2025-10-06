package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.LocalTime;

public class Hours {
  private final LocalTime open;
  private final LocalTime close;

  public Hours(LocalTime open, LocalTime close) {
    this.open = open;
    this.close = close;
  }

  public LocalTime getOpeningHour() {
    return open;
  }

  public LocalTime getClosingHour() {
    return close;
  }

  public void validateFields() {
    validateFieldsAreDefined();
    validateFieldsAreValid();
  }

  private void validateFieldsAreDefined() {
    if (open == null) {
      throw new MissingParameterException("The openingHour of the restaurant is required");
    }
    if (close == null) {
      throw new MissingParameterException("The closingHour of the restaurant is required");
    }
  }

  private void validateFieldsAreValid() {
    if (close.isBefore(open)) {
      throw new InvalidParameterException(
          "The restaurant closing hour cannot be before the opening hour");
    }
    if (open.plusHours(1).compareTo(close) > 0) {
      throw new InvalidParameterException("The restaurant needs to be open for at least one hour");
    }
  }

  public boolean isFullyOpenDuring(LocalTime from, LocalTime to) {

    if (from != null && to != null) {
      return !open.isAfter(from) && !close.isBefore(to);
    } else if (from != null) {
      return !open.isAfter(from) && close.isAfter(from);
    } else if (to != null) {
      return open.isBefore(to) && !close.isBefore(to);
    }
    return true;
  }
}
