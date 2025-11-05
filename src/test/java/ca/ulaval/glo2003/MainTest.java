package ca.ulaval.glo2003;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MainTest {

  @Test
  public void givenNoPortEnvVar_thenBaseUriUsesDefaultPort() {
    String expectedUri = "http://0.0.0.0:8080/";

    assertEquals(expectedUri, Main.getBaseUri());
  }
}
