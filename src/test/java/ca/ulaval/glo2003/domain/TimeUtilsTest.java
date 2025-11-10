package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeUtilsTest {

  private int slotInterval;

  @BeforeEach
  void setUp() {
    slotInterval = TimeUtils.getSlotInterval();
  }

  @Test
  void
      givenTimeThatIsNotAtSlotIntervalSlice_whenAdjustTimeToSlotIntervalSliceUp_thenTimeIsAtSlotIntervalSlice() {
    LocalTime time = LocalTime.of(11, 10, 0);
    LocalTime adjustedTime = TimeUtils.adjustTimeToSlotIntervalSliceUp(time);
    LocalTime expectedAdjustedTime = LocalTime.of(11, slotInterval, 0);

    assertThat(adjustedTime).isEqualTo(expectedAdjustedTime);
  }

  @Test
  void givenTimeThatIsAtSlotIntervalSlice_whenAdjustTimeToSlotIntervalSliceUp_thenNoChanges() {
    LocalTime time = LocalTime.of(11, slotInterval, 0);
    LocalTime adjustedTime = TimeUtils.adjustTimeToSlotIntervalSliceUp(time);

    assertThat(adjustedTime).isEqualTo(time);
  }

  @Test
  void
      givenTimeThatIsNotAtSlotIntervalSlice_whenAdjustTimeToSlotIntervalSliceDown_thenTimeIsAtSlotIntervalSlice() {
    LocalTime time = LocalTime.of(11, 10, 0);
    LocalTime adjustedTime = TimeUtils.adjustTimeToSlotIntervalSliceDown(time);
    LocalTime expectedAdjustedTime = LocalTime.of(11, 0, 0);

    assertThat(adjustedTime).isEqualTo(expectedAdjustedTime);
  }

  @Test
  void givenTimeThatIsAtSlotIntervalSlice_whenAdjustTimeToSlotIntervalSliceDown_thenNoChanges() {
    LocalTime time = LocalTime.of(11, 0, 0);
    LocalTime adjustedTime = TimeUtils.adjustTimeToSlotIntervalSliceDown(time);

    assertThat(adjustedTime).isEqualTo(time);
  }
}
