package ca.ulaval.glo2003.api.health;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HealthResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .register(HealthResource.class);
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void givenHealthEndpoint_whenGet_thenReturns200() {
        try (Response response = target("/health").request(MediaType.APPLICATION_JSON).get()) {
            assertEquals(200, response.getStatus());
        }
    }

    @Test
    public void givenHealthEndpoint_whenGet_thenReturnsJsonMediaType() {
        try (Response response = target("/health").request(MediaType.APPLICATION_JSON).get()) {
            assertNotNull(response.getMediaType());
            assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        }
    }

    @Test
    public void givenHealthEndpoint_whenGet_thenEntityIsNotNull() {
        try (Response response = target("/health").request(MediaType.APPLICATION_JSON).get()) {
            assertNotNull(response.getEntity());
        }
    }

    @Test
    public void givenMultipleCalls_whenGet_thenAlwaysReturns200() {
        try (Response response1 = target("/health").request(MediaType.APPLICATION_JSON).get();
             Response response2 = target("/health").request(MediaType.APPLICATION_JSON).get();
             Response response3 = target("/health").request(MediaType.APPLICATION_JSON).get()) {

            assertEquals(200, response1.getStatus());
            assertEquals(200, response2.getStatus());
            assertEquals(200, response3.getStatus());
        }
    }
}