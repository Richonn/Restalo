package ca.ulaval.glo2003.infra.persistence.mongo;

public class MongoConfig {
  private static final String MONGO_CLUSTER_URL_ENV = "MONGO_CLUSTER_URL";
  private static final String MONGO_DATABASE_ENV = "MONGO_DATABASE";

  private final String clusterUrl;
  private final String database;

  public MongoConfig() {
    this.clusterUrl = getRequiredEnvVariable(MONGO_CLUSTER_URL_ENV);
    this.database = getRequiredEnvVariable(MONGO_DATABASE_ENV);
  }

  private String getRequiredEnvVariable(String name) {
    String value = System.getenv(name);
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
