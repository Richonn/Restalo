package ca.ulaval.glo2003.domain.reservation;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationTimeTest {

  private static ReservationTimeFixture reservationTimeFixture;

  @BeforeEach
  public void setUp() {
    reservationTimeFixture = new ReservationTimeFixture();
  }

  @Test
  void givenAStartTime_whenAdjustStartTime15MinSlice_thenAdjustStartTime15MinSlice() {
    ReservationTime a_reservationTime =
        reservationTimeFixture.withStart(LocalTime.of(10, 5, 0)).build();
    LocalTime expectedStartTime = LocalTime.of(10, 15, 0);

    a_reservationTime.adjustStartTimeToIntervalSlice();

    assertThat(a_reservationTime.getStartTime()).isEqualTo(expectedStartTime);
  }
}
