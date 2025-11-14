package ca.ulaval.glo2003;

import ca.ulaval.glo2003.application.reservation.ReservationService;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.application.restaurant.TransactionManager;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryTransactionManager;
import ca.ulaval.glo2003.infra.persistence.PersistenceConfig;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoConfig;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoConnection;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoReservationRepository;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoTransactionManager;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {

    if (PersistenceConfig.isInMemoryMode()) {
      bindAsContract(RestaurantService.class).in(Singleton.class);
      bindRestaurantRepository();
      bindAsContract(RestaurantDomainService.class).in(Singleton.class);

      bindAsContract(ReservationService.class).in(Singleton.class);
      bindReservationRepository();
      bindAsContract(ReservationFactory.class).in(Singleton.class);
      bind(InMemoryTransactionManager.class).to(TransactionManager.class).in(Singleton.class);

    } else {
      bind(MongoTransactionManager.class).to(TransactionManager.class).in(Singleton.class);
      bind(MongoConfig.class).to(MongoConfig.class).in(Singleton.class);
      bindAsContract(MongoConnection.class).in(Singleton.class);

      bindAsContract(RestaurantService.class).in(Singleton.class);
      bindRestaurantRepository();
      bindAsContract(RestaurantDomainService.class).in(Singleton.class);

      bindAsContract(ReservationService.class).in(Singleton.class);
      bindReservationRepository();
      bindAsContract(ReservationFactory.class).in(Singleton.class);
    }
  }

  private void bindRestaurantRepository() {
    if (PersistenceConfig.isInMemoryMode()) {
      bind(InMemoryRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
    } else {
      bind(MongoRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
    }
  }

  private void bindReservationRepository() {
    if (PersistenceConfig.isInMemoryMode()) {
      bind(InMemoryReservationRepository.class).to(ReservationRepository.class).in(Singleton.class);
    } else {
      bind(MongoReservationRepository.class).to(ReservationRepository.class).in(Singleton.class);
    }
  }
}
