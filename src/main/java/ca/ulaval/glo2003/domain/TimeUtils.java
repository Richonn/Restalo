package ca.ulaval.glo2003.domain;

import java.time.LocalTime;

public final class TimeUtils {

  private static final int slotInterval = 15;

  public static int getSlotInterval() {
    return slotInterval;
  }

  public static LocalTime adjustTimeToSlotIntervalSliceUp(LocalTime time) {
    int minutes = time.getMinute();
    int modulo = minutes % slotInterval;

    if (modulo != 0) {
      return time.plusMinutes(slotInterval - modulo);
    }
    return time;
  }

  public static LocalTime adjustTimeToSlotIntervalSliceDown(LocalTime time) {
    int minutes = time.getMinute();
    if (minutes != 0) {
      int modulo = slotInterval % minutes;

      if (modulo != 0) {
        return time.minusMinutes(slotInterval - modulo);
      }
    }
    return time;
  }
}
