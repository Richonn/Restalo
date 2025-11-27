package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.OrderRepository;
import ca.ulaval.glo2003.infra.persistence.AbstractOrderRepositoryTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class MongoOrderRepositoryTest extends AbstractOrderRepositoryTest {

  private static final String MONGO_DOCKER_IMAGE = "mongo:7.0";
  private static final String MONGO_CLUSTER_URL_PROPERTY = "MONGO_CLUSTER_URL";
  private static final String MONGO_DATABASE_PROPERTY = "MONGO_DATABASE";
  private static final String TEST_DATABASE_NAME = "test_order_db";

  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse(MONGO_DOCKER_IMAGE));

  private static OrderRepository orderRepository;

  @BeforeEach
  public void setUp() {
    super.setUp();
  }

  @BeforeAll
  static void initializeMongoConnection() {
    mongoDBContainer.start();

    System.setProperty(MONGO_CLUSTER_URL_PROPERTY, mongoDBContainer.getReplicaSetUrl());
    System.setProperty(MONGO_DATABASE_PROPERTY, TEST_DATABASE_NAME);

    try {
      MongoConnection.getInstance().close();
    } catch (Exception e) {
    }

    orderRepository = new MongoOrderRepository(MongoConnection.getInstance());
  }

  @AfterAll
  static void closeMongoConnection() {
    MongoConnection.getInstance().close();
    mongoDBContainer.stop();
  }

  @Override
  protected OrderRepository getOrderRepository() {
    return orderRepository;
  }

  @Override
  protected void clearOrderRepository() {
    try {
      MongoConnection.getInstance().getDatastore().getDatabase().drop();
      MongoConnection.getInstance().close();
      orderRepository = new MongoOrderRepository(MongoConnection.getInstance());
    } catch (Exception e) {
    }
  }
}
