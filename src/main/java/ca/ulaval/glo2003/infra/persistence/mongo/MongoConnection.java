package ca.ulaval.glo2003.infra.persistence.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import java.util.concurrent.TimeUnit;

public class MongoConnection {
  private static volatile MongoConnection instance;
  private final MongoClient mongoClient;
  private final Datastore datastore;

  private MongoConnection(MongoConfig config) {
    this.mongoClient = createMongoClient(config.getClusterUrl());
    this.datastore = createDatastore(config.getDatabase());
  }

  public static MongoConnection getInstance() {
    if (instance == null) {
      synchronized (MongoConnection.class) {
        if (instance == null) {
          MongoConfig config = new MongoConfig();
          instance = new MongoConnection(config);
        }
      }
    }
    return instance;
  }

  private MongoClient createMongoClient(String clusterUrl) {
    try {
      ConnectionString connectionString = new ConnectionString(clusterUrl);

      MongoClientSettings settings =
          MongoClientSettings.builder()
              .applyConnectionString(connectionString)
              .applyToClusterSettings(
                  builder ->
                      builder.serverSelectionTimeout(
                          MongoConfig.SERVER_SELECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS))
              .applyToConnectionPoolSettings(
                  builder -> {
                    builder.maxConnectionIdleTime(
                        MongoConfig.MAX_CONNECTION_IDLE_TIME_SECONDS, TimeUnit.SECONDS);
                    builder.minSize(MongoConfig.CONNECTION_POOL_MIN_SIZE);
                    builder.maxSize(MongoConfig.CONNECTION_POOL_MAX_SIZE);
                  })
              .build();

      return MongoClients.create(settings);
    } catch (Exception e) {
      throw new IllegalStateException("Unable to connect to MongoDB", e);
    }
  }

  private Datastore createDatastore(String databaseName) {
    return Morphia.createDatastore(mongoClient, databaseName);
  }

  public MongoClient getMongoClient() {
    return mongoClient;
  }

  public Datastore getDatastore() {
    return datastore;
  }

  public synchronized void close() {
    if (mongoClient != null) {
      try {
        mongoClient.close();
        instance = null;
      } catch (Exception e) {
        System.err.println("Error closing MongoDB connection: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
