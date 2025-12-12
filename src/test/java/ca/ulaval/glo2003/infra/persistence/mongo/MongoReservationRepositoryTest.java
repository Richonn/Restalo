package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.infra.persistence.AbstractReservationRepositoryTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class MongoReservationRepositoryTest extends AbstractReservationRepositoryTest {

  private static final String MONGO_DOCKER_IMAGE = "mongo:7.0";
  private static final String MONGO_CLUSTER_URL_PROPERTY = "MONGO_CLUSTER_URL";
  private static final String MONGO_DATABASE_PROPERTY = "MONGO_DATABASE";
  private static final String TEST_DATABASE_NAME = "test_reservation_db";

  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse(MONGO_DOCKER_IMAGE));

  private static MongoConnection mongoConnection;
  private static ReservationRepository reservationRepository;

  @BeforeAll
  static void initializeMongoConnection() {
    mongoDBContainer.start();

    System.setProperty(MONGO_CLUSTER_URL_PROPERTY, mongoDBContainer.getReplicaSetUrl());
    System.setProperty(MONGO_DATABASE_PROPERTY, TEST_DATABASE_NAME);

    mongoConnection = new MongoConnection();
    reservationRepository = new MongoReservationRepository(mongoConnection);
  }

  @AfterAll
  static void closeMongoConnection() {
    if (mongoConnection != null) {
      mongoConnection.close();
    }
    mongoDBContainer.stop();
  }

  @Override
  protected ReservationRepository getRepository() {
    return reservationRepository;
  }

  @Override
  protected void clearRepository() {
    try {
      mongoConnection.getDatastore().getDatabase().drop();
      mongoConnection.close();
      mongoConnection = new MongoConnection();
      reservationRepository = new MongoReservationRepository(mongoConnection);
    } catch (Exception e) {
    }
  }
}
