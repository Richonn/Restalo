package ca.ulaval.glo2003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MainTest {

    private HttpServer server;

    @AfterEach
    public void tearDown() {
        if (server != null && server.isStarted()) {
            server.shutdownNow();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test
    public void givenBaseUri_thenBaseUriIsCorrect() {
        String expectedUri = "http://0.0.0.0:8080/";

        assertEquals(expectedUri, Main.BASE_URI);
    }
}