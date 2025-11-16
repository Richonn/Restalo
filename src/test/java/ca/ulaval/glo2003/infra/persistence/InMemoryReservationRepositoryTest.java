package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryReservationRepositoryTest extends AbstractReservationRepositoryTest {

  private ReservationRepository reservationRepository;

  @BeforeEach
  public void setUp() {
    reservationRepository = new InMemoryReservationRepository();
  }

  @Override
  protected ReservationRepository getRepository() {
    return reservationRepository;
  }

  @Override
  protected void clearRepository() {
    reservationRepository = new InMemoryReservationRepository();
  }
}
