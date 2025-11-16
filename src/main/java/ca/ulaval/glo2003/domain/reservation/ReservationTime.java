package ca.ulaval.glo2003.domain.reservation;

import java.time.LocalTime;

public class ReservationTime {

  private LocalTime startTime;
  private LocalTime endTime;

  public ReservationTime(LocalTime startTime, LocalTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }
}
