package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.exceptions.mappers.IllegalArgumentExceptionMapper;
import ca.ulaval.glo2003.infra.monitoring.SentryConfig;
import ca.ulaval.glo2003.infra.persistence.PersistenceConfig;
import io.github.cdimascio.dotenv.Dotenv;
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

  public static void main(String[] args) {
    String baseUri = getBaseUri();
    Dotenv.configure().ignoreIfMissing().systemProperties().load();

    SentryConfig.initialize();

    System.out.printf(
        "Starting application with persistence mode: %s%n",
        PersistenceConfig.getPersistenceMode().getValue());

    startServer();
    System.out.printf("Jersey app started with endpoints available at %s%n", baseUri);
  }
}
