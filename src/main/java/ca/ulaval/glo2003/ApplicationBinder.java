package ca.ulaval.glo2003;

import ca.ulaval.glo2003.application.order.OrderService;
import ca.ulaval.glo2003.application.reservation.ReservationService;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.application.specialOffer.SpecialOfferService;
import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.SpecialOfferDomainService;
import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.order.OrderFactory;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOfferFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryOrderRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.InMemorySpecialOfferRepository;
import ca.ulaval.glo2003.infra.persistence.PersistenceConfig;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoConnection;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoOrderRepository;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoReservationRepository;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoSpecialOfferRepository;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    if (!PersistenceConfig.isInMemoryMode()) {
      bindAsContract(MongoConnection.class).in(Singleton.class);
    }

    bindAsContract(RestaurantService.class).in(Singleton.class);
    bindRestaurantRepository();
    bindAsContract(RestaurantDomainService.class).in(Singleton.class);
    bindAsContract(RestaurantFactory.class).in(Singleton.class);

    bindAsContract(ReservationService.class).in(Singleton.class);
    bindReservationRepository();
    bindAsContract(ReservationFactory.class).in(Singleton.class);

    bindAsContract(OrderService.class).in(Singleton.class);
    bindOrderRepository();
    bindAsContract(OrderFactory.class).in(Singleton.class);

    bindAsContract(SpecialOfferService.class).in(Singleton.class);
    bindSpecialOfferRepository();
    bindAsContract(SpecialOfferDomainService.class).in(Singleton.class);
    bindAsContract(SpecialOfferFactory.class).in(Singleton.class);
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

  private void bindOrderRepository() {
    if (PersistenceConfig.isInMemoryMode()) {
      bind(InMemoryOrderRepository.class).to(OrderRepository.class).in(Singleton.class);
    } else {
      bind(MongoOrderRepository.class).to(OrderRepository.class).in(Singleton.class);
    }
  }

  private void bindSpecialOfferRepository() {
    if (PersistenceConfig.isInMemoryMode()) {
      bind(InMemorySpecialOfferRepository.class)
          .to(SpecialOfferRepository.class)
          .in(Singleton.class);
    } else {
      bind(MongoSpecialOfferRepository.class).to(SpecialOfferRepository.class).in(Singleton.class);
    }
  }
}
