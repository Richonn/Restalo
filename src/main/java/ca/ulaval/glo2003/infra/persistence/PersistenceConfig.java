package ca.ulaval.glo2003.infra.persistence;

public class PersistenceConfig {
  private static final String PERSISTENCE_PROPERTY = "persistence";

  public static PersistenceMode getPersistenceMode() {
    String persistenceValue = System.getProperty(PERSISTENCE_PROPERTY);
    return PersistenceMode.fromString(persistenceValue);
  }

  public static boolean isInMemoryMode() {
    return getPersistenceMode() == PersistenceMode.INMEMORY;
  }

  public static boolean isMongoMode() {
    return getPersistenceMode() == PersistenceMode.MONGO;
  }
}
