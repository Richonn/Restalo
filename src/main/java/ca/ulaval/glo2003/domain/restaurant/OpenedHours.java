package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalTime;

public class OpenedHours {

  private final LocalTime from;
  private final LocalTime to;

  public OpenedHours(LocalTime from, LocalTime to) {
    this.from = from;
    this.to = to;
  }

  public LocalTime getFrom() {
    return from;
  }

  public LocalTime getTo() {
    return to;
  }

  public void isTimeRangeValid() {
    if (from != null && to != null && from.isAfter(to)) {
      throw new InvalidParameterException("Closing hour cannot be before opening hours");
    }
  }
}
