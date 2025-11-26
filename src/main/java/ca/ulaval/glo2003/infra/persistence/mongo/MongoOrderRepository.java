package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.domain.order.Order;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.OrderDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.mappers.OrderDocumentMapper;
import dev.morphia.Datastore;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MongoOrderRepository implements OrderRepository {
  private final Datastore datastore;

  @Inject
  MongoOrderRepository(MongoConnection mongoConnection) {
    this.datastore = mongoConnection.getDatastore();
  }

  @Override
  public void save(Order order) {
    OrderDocument orderDocument = OrderDocumentMapper.toDocument(order);
    datastore.save(orderDocument);
  }
}
