package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.application.restaurant.TransactionManager;
import dev.morphia.Datastore;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MongoTransactionManager implements TransactionManager {

  private final MongoConnection mongoConnection;

  @Inject
  public MongoTransactionManager(MongoConnection mongoConnection) {
    this.mongoConnection = mongoConnection;
  }

  @Override
  public void executeInTransaction(TransactionOperation<Void> operation) {
    Datastore datastore = mongoConnection.getDatastore();
    datastore.withTransaction(
        txnDatastore -> {
          operation.apply(txnDatastore);
          return null;
        });
  }
}
