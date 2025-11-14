package ca.ulaval.glo2003.infra.persistence.mongo;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MongoLifecycleListener implements ServletContextListener {

  @Inject private MongoConnection mongoConnection;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    if (mongoConnection != null) {
      System.out.println("MongoDB connection initialized.");
    } else {
      System.out.println("MongoDB connection failed to initialize.");
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

    if (mongoConnection != null) {
      mongoConnection.close();
      System.out.println("MongoDB connection closed.");
    }
  }
}
