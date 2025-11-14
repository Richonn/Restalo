package ca.ulaval.glo2003.application.restaurant;

import dev.morphia.Datastore;

public interface TransactionManager {
  void executeInTransaction(TransactionOperation<Void> operation);

  @FunctionalInterface
  interface TransactionOperation<T> {
    void apply(Datastore datastore);
  }
}
