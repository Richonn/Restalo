package ca.ulaval.glo2003.Domain;

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
}
