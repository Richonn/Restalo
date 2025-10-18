package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalTime;

public class OpenedHoursFixture {

  LocalTime FROM = LocalTime.of(11, 0, 0);
  LocalTime TO = LocalTime.of(13, 0, 0);

  public OpenedHours build() {
    return new OpenedHours(FROM, TO);
  }

  public OpenedHoursFixture withFrom(LocalTime from) {
    this.FROM = from;
    return this;
  }

  public OpenedHoursFixture withTo(LocalTime to) {
    this.TO = to;
    return this;
  }
}
