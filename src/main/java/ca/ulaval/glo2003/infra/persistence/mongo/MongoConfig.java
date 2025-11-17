package ca.ulaval.glo2003.infra.persistence.mongo;

import jakarta.inject.Singleton;

@Singleton
public class MongoConfig {
  private static final String MONGO_CLUSTER_URL_ENV = "MONGO_CLUSTER_URL";
  private static final String MONGO_DATABASE_ENV = "MONGO_DATABASE";

  public static final int SERVER_SELECTION_TIMEOUT_SECONDS = 5;
  public static final int MAX_CONNECTION_IDLE_TIME_SECONDS = 10;
  public static final int CONNECTION_POOL_MIN_SIZE = 1;
  public static final int CONNECTION_POOL_MAX_SIZE = 10;

  private final String clusterUrl;
  private final String database;

  public MongoConfig() {
    this.clusterUrl = getRequiredEnvVariable(MONGO_CLUSTER_URL_ENV);
    this.database = getRequiredEnvVariable(MONGO_DATABASE_ENV);
  }

  private String getRequiredEnvVariable(String name) {
    String value = System.getProperty(name);
    if (value == null || value.trim().isEmpty()) {
      value = System.getenv(name);
    }
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalStateException(
          String.format(
              "Required environment variable '%s' is not set. "
                  + "Please set it before starting the application in mongo mode.",
              name));
    }
    return value;
  }

  public String getClusterUrl() {
    return clusterUrl;
  }

  public String getDatabase() {
    return database;
  }
}
