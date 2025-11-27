package ca.ulaval.glo2003.infra.monitoring;

import ca.ulaval.glo2003.infra.persistence.PersistenceConfig;
import io.sentry.Sentry;

public class SentryConfig {
  private static final String SENTRY_DSN_PROPERTY = "SENTRY_DSN";

  public static void initialize() {
    String dsnFromProperty = System.getProperty(SENTRY_DSN_PROPERTY);
    String dsnFromEnv = System.getenv(SENTRY_DSN_PROPERTY);
    final String sentryDsn =
        (dsnFromProperty != null && !dsnFromProperty.isEmpty()) ? dsnFromProperty : dsnFromEnv;

    if (sentryDsn != null && !sentryDsn.isEmpty()) {
      System.out.println("Initializing Sentry with DSN: " + sentryDsn.substring(0, 30) + "...");
      Sentry.init(
          options -> {
            options.setDsn(sentryDsn);
            options.setTracesSampleRate(1.0);
            options.setEnvironment(PersistenceConfig.isMongoMode() ? "production" : "development");
          });
      System.out.println("Sentry initialized successfully");
    } else {
      System.out.println("SENTRY_DSN not configured, error tracking disabled");
    }
  }
}
