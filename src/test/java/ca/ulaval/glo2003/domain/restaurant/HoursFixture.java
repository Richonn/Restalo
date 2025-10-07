package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalTime;

public class HoursFixture {

  private LocalTime OPEN = LocalTime.of(11, 0, 0);
  private LocalTime CLOSE = LocalTime.of(12, 0, 0);
  private Integer RESERVATIONLENGTH = 60;

  public Hours build() {
    return new Hours(OPEN, CLOSE, RESERVATIONLENGTH);
  }

  public HoursFixture withOpen(LocalTime open) {
    OPEN = open;
    return this;
  }

  public HoursFixture withClose(LocalTime close) {
    CLOSE = close;
    return this;
  }

  public HoursFixture withReservationLength(int reservationLength) {
    RESERVATIONLENGTH = reservationLength;
    return this;
  }
}
