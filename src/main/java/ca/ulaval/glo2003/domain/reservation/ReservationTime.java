package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
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

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public void validateStartTimeIsDefined() {
    if (startTime == null) {
      throw new MissingParameterException("The start time of the reservation is required");
    }
  }

  public void adjustStartTimeTo15minSlice() {
    int minutes = startTime.getMinute();
    int modulo = minutes % 15;

    if (modulo != 0) {
      startTime = startTime.plusMinutes(15 - modulo);
    }
  }
}
