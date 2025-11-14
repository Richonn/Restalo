package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.application.restaurant.TransactionManager;
import jakarta.inject.Singleton;

@Singleton
public class InMemoryTransactionManager implements TransactionManager {
  @Override
  public void executeInTransaction(TransactionOperation<Void> operation) {
    operation.apply(null);
  }
}
