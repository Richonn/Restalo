package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.TimeUtils;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hours {
  private final LocalTime open;
  private final LocalTime close;
  private final int reservationDuration;

  private static final int DEFAULT_RESERVATION_DURATION = 60;

  public Hours(LocalTime open, LocalTime close, Integer reservationDuration) {
    this.open = open;
    this.close = close;
    this.reservationDuration =
        Objects.requireNonNullElse(reservationDuration, DEFAULT_RESERVATION_DURATION);
  }

  public LocalTime getOpeningHour() {
    return open;
  }

  public LocalTime getClosingHour() {
    return close;
  }

  public int getReservationDuration() {
    return reservationDuration;
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

  public LocalTime defineReservationEndTime(LocalTime startTime) {
    if (startTime.isBefore(open)) {
      throw new InvalidParameterException(
          "The restaurant is not open for the selected reservation start time");
    }
    if (startTime.plusMinutes(reservationDuration).isAfter(close)) {
      throw new InvalidParameterException(
          "The restaurant closes before the end of the reservation");
    }
    return startTime.plusMinutes(reservationDuration);
  }

  public List<LocalTime> generateAllSlotIntervalSlices() {
    List<LocalTime> slots = new ArrayList<>();

    LocalTime current = TimeUtils.adjustTimeToSlotIntervalSliceUp(open);

    while (current.isBefore(TimeUtils.adjustTimeToSlotIntervalSliceDown(close))) {
      slots.add(current);
      current = current.plusMinutes(TimeUtils.getSlotInterval());
    }

    return slots;
  }
}
