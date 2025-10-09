package ca.ulaval.glo2003.domain.reservation;

import java.time.LocalTime;

public class ReservationTimeFixture {

  private LocalTime START = LocalTime.of(11, 0, 0);
  private LocalTime END = LocalTime.of(12, 0, 0);

  public ReservationTime build() {
    return new ReservationTime(START, END);
  }

  public ReservationTimeFixture withStart(LocalTime start) {
    START = start;
    return this;
  }

  public ReservationTimeFixture withEnd(LocalTime end) {
    END = end;
    return this;
  }
}
