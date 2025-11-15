package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.ReservationDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.mappers.ReservationDocumentMapper;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MongoReservationRepository implements ReservationRepository {
  private final Datastore datastore;

  @Inject
  public MongoReservationRepository(MongoConnection mongoConnection) {
    this.datastore = mongoConnection.getDatastore();
    datastore.getMapper().map(ReservationDocument.class);
    datastore.ensureIndexes();
  }

  @Override
  public void save(Reservation reservation) {
    ReservationDocument document = ReservationDocumentMapper.toDocument(reservation);
    datastore.save(document);
  }

  @Override
  public List<Reservation> getReservationListOfRestaurant(String restaurantId) {
    return datastore
        .find(ReservationDocument.class)
        .filter(Filters.eq("restaurantId", restaurantId))
        .stream()
        .map(ReservationDocumentMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Reservation getReservationById(String reservationId) {
    ReservationDocument document =
        datastore.find(ReservationDocument.class).filter(Filters.eq("_id", reservationId)).first();

    return ReservationDocumentMapper.toDomain(document);
  }

  @Override
  public void delete(String reservationId) {
    datastore.find(ReservationDocument.class).filter(Filters.eq("_id", reservationId)).delete();
  }
}
