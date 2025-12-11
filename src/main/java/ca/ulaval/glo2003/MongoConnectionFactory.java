package ca.ulaval.glo2003;

import ca.ulaval.glo2003.infra.persistence.mongo.MongoConnection;
import org.glassfish.hk2.api.Factory;

public class MongoConnectionFactory implements Factory<MongoConnection> {

  @Override
  public MongoConnection provide() {
    return MongoConnection.getInstance();
  }

  @Override
  public void dispose(MongoConnection instance) {
  }
}
