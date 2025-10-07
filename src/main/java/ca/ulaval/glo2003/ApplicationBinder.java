package ca.ulaval.glo2003;

import ca.ulaval.glo2003.application.reservation.ReservationService;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bindAsContract(RestaurantService.class).in(Singleton.class);
    bind(InMemoryRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
    bindAsContract(RestaurantDomainService.class).in(Singleton.class);

    bindAsContract(ReservationService.class).in(Singleton.class);
    bind(InMemoryReservationRepository.class).to(ReservationRepository.class).in(Singleton.class);
    bindAsContract(ReservationFactory.class).in(Singleton.class);
  }
}
