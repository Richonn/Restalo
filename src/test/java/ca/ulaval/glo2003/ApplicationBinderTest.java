package ca.ulaval.glo2003;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import ca.ulaval.glo2003.application.reservation.ReservationService;
import ca.ulaval.glo2003.application.restaurant.RestaurantService;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantDomainService;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.reservation.ReservationFactory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationBinderTest {

    private ServiceLocator serviceLocator;

    @BeforeEach
    public void setUp() {
        serviceLocator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    }

    @Test
    public void givenApplicationBinder_whenGetRestaurantService_thenReturnsInstance() {
        RestaurantService service = serviceLocator.getService(RestaurantService.class);

        assertNotNull(service);
    }

    @Test
    public void givenApplicationBinder_whenGetRestaurantServiceTwice_thenReturnsSameInstance() {
        RestaurantService service1 = serviceLocator.getService(RestaurantService.class);
        RestaurantService service2 = serviceLocator.getService(RestaurantService.class);

        assertSame(service1, service2);
    }

    @Test
    public void givenApplicationBinder_whenGetRestaurantRepository_thenReturnsInMemoryImplementation() {
        RestaurantRepository repository = serviceLocator.getService(RestaurantRepository.class);

        assertNotNull(repository);
    }

    @Test
    public void givenApplicationBinder_whenGetRestaurantRepositoryTwice_thenReturnsSameInstance() {
        RestaurantRepository repository1 = serviceLocator.getService(RestaurantRepository.class);
        RestaurantRepository repository2 = serviceLocator.getService(RestaurantRepository.class);

        assertSame(repository1, repository2);
    }

    @Test
    public void givenApplicationBinder_whenGetRestaurantDomainService_thenReturnsInstance() {
        RestaurantDomainService service = serviceLocator.getService(RestaurantDomainService.class);

        assertNotNull(service);
    }

    @Test
    public void givenApplicationBinder_whenGetRestaurantDomainServiceTwice_thenReturnsSameInstance() {
        RestaurantDomainService service1 = serviceLocator.getService(RestaurantDomainService.class);
        RestaurantDomainService service2 = serviceLocator.getService(RestaurantDomainService.class);

        assertSame(service1, service2);
    }

    @Test
    public void givenApplicationBinder_whenGetReservationService_thenReturnsInstance() {
        ReservationService service = serviceLocator.getService(ReservationService.class);

        assertNotNull(service);
    }

    @Test
    public void givenApplicationBinder_whenGetReservationServiceTwice_thenReturnsSameInstance() {
        ReservationService service1 = serviceLocator.getService(ReservationService.class);
        ReservationService service2 = serviceLocator.getService(ReservationService.class);

        assertSame(service1, service2);
    }

    @Test
    public void givenApplicationBinder_whenGetReservationRepository_thenReturnsInMemoryImplementation() {
        ReservationRepository repository = serviceLocator.getService(ReservationRepository.class);

        assertNotNull(repository);
    }

    @Test
    public void givenApplicationBinder_whenGetReservationRepositoryTwice_thenReturnsSameInstance() {
        ReservationRepository repository1 = serviceLocator.getService(ReservationRepository.class);
        ReservationRepository repository2 = serviceLocator.getService(ReservationRepository.class);

        assertSame(repository1, repository2);
    }

    @Test
    public void givenApplicationBinder_whenGetReservationFactory_thenReturnsInstance() {
        ReservationFactory factory = serviceLocator.getService(ReservationFactory.class);

        assertNotNull(factory);
    }

    @Test
    public void givenApplicationBinder_whenGetReservationFactoryTwice_thenReturnsSameInstance() {
        ReservationFactory factory1 = serviceLocator.getService(ReservationFactory.class);
        ReservationFactory factory2 = serviceLocator.getService(ReservationFactory.class);

        assertSame(factory1, factory2);
    }
}