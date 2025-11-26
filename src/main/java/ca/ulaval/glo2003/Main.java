package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.exceptions.mappers.IllegalArgumentExceptionMapper;
import ca.ulaval.glo2003.infra.persistence.PersistenceConfig;
import ca.ulaval.glo2003.infra.persistence.mongo.MongoConnection;
import io.github.cdimascio.dotenv.Dotenv;
import io.sentry.Sentry;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
  public static String getBaseUri() {
    String port = System.getenv("PORT");
    if (port == null || port.isEmpty()) {
      port = "8080";
    }
    return "http://0.0.0.0:" + port + "/";
  }

  public static HttpServer startServer() {
    String baseUri = getBaseUri();

    final ResourceConfig rc =
        new ResourceConfig()
            .packages("ca.ulaval.glo2003")
            .register(IllegalArgumentExceptionMapper.class);

    rc.register(new ApplicationBinder());

    return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), rc);
  }

  private static void initializeSentry() {
    String dsnFromProperty = System.getProperty("SENTRY_DSN");
    String dsnFromEnv = System.getenv("SENTRY_DSN");
    final String sentryDsn =
        (dsnFromProperty != null && !dsnFromProperty.isEmpty()) ? dsnFromProperty : dsnFromEnv;

    if (sentryDsn != null && !sentryDsn.isEmpty()) {
      System.out.println("Initializing Sentry with DSN: " + sentryDsn.substring(0, 30) + "...");
      Sentry.init(
          options -> {
            options.setDsn(sentryDsn);
            options.setTracesSampleRate(1.0);
            options.setEnvironment(PersistenceConfig.isMongoMode() ? "production" : "development");
            options.setDebug(true);
          });
      System.out.println("Sentry initialized successfully");
    } else {
      System.out.println("SENTRY_DSN not configured, error tracking disabled");
    }
  }

  public static void main(String[] args) {
    String baseUri = getBaseUri();
    Dotenv.configure().ignoreIfMissing().systemProperties().load();

    initializeSentry();

    System.out.printf(
        "Starting application with persistence mode: %s%n",
        PersistenceConfig.getPersistenceMode().getValue());

    if (PersistenceConfig.isMongoMode()) {
      Runtime.getRuntime()
          .addShutdownHook(
              new Thread(
                  () -> {
                    System.out.println("Closing MongoDB connection...");
                    MongoConnection.getInstance().close();
                  }));
    }

    startServer();
    System.out.printf("Jersey app started with endpoints available at %s%n", baseUri);
  }
}
