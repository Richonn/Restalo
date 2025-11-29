package ca.ulaval.glo2003.api.ressources.order;

import ca.ulaval.glo2003.api.dto.common.OrderDto;
import ca.ulaval.glo2003.api.dto.response.OrderResponseDto;
import ca.ulaval.glo2003.application.order.OrderService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OrderResourceIntegrationTest extends JerseyTest {

    private static final String ORDER_ID = "orderId";
    private static final String RESTAURANT_ID = "restaurantId";
    private static final String CREATE_ORDER_PATH = "restaurants/%s/commandes";
    private static final String GET_ORDER_PATH = "/commandes/%s";

    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;
    private static final int HTTP_NO_CONTENT = 204;

    private static final String MISSING_PARAMETER_ERROR = "MISSING_PARAMETER";
    private static final String INVALID_PARAMETER_ERROR = "INVALID_PARAMETER";

    private OrderService orderService;
    private OrderResourceIntegrationTestFixture orderResourceIntegrationTestFixture;

    @Override
    protected Application configure() {
        orderService = mock(OrderService.class);

        return new ResourceConfig()
                .register(
                        new AbstractBinder() {
                            @Override
                            protected void configure() {
                                bind(orderService).to(OrderService.class);
                            }
                        })
                .register(OrderResource.class)
                .packages("ca.ulaval.glo2003.api.exceptions");
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        orderResourceIntegrationTestFixture = new OrderResourceIntegrationTestFixture();
        super.setUp();
    }

    @Test
    void givenValidOrder_whenCreateOrder_thenReturns201WithLocation() {
        when(orderService.createOrder(any(), any())).thenReturn(ORDER_ID);

        try (Response response = orderResourceIntegrationTestFixture.withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID)).buildPostOrderResponse(target())) {
            assertThat(HTTP_CREATED).isEqualTo(response.getStatus());
            String location = response.getHeaderString("Location");
            assertNotNull(location);
            assertTrue(location.contains(String.format(GET_ORDER_PATH, ORDER_ID)));
            verify(orderService).createOrder(eq(RESTAURANT_ID), any(OrderDto.class));
        }
    }

    @Test
    public void givenMissingBody_whenCreateOrder_thenReturns400() {
        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withIncludeBody(false)
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
        }
    }

    @Test
    public void givenMissingCustomerBody_whenCreateOrder_thenReturns400() {
        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withIncludeCustomer(false)
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
        }
    }

    @Test
    public void givenBlankMenu_whenCreateOrder_thenReturns400() {

        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withOrderMenu("")
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
            String body = response.readEntity(String.class);
            assertTrue(body.contains(MISSING_PARAMETER_ERROR) && body.contains("menu"));
        }
    }

    @Test
    public void givenBlankDate_whenCreateOrder_thenReturns400() {

        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withOrderDate("")
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
            String body = response.readEntity(String.class);
            assertTrue(body.contains(INVALID_PARAMETER_ERROR) && body.contains("invalid"));
        }
    }

    @Test
    public void givenInvalidDateFormat_whenCreateOrder_thenReturns400() {

        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withOrderDate("invalid")
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
            String body = response.readEntity(String.class);
            assertTrue(body.contains(INVALID_PARAMETER_ERROR) && body.contains("invalid"));
        }
    }

    @Test
    public void givenBlankCustomerName_whenCreateOrder_thenReturns400() {

        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withCustomerName("")
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
            String body = response.readEntity(String.class);
            assertTrue(body.contains(MISSING_PARAMETER_ERROR) && body.contains("name"));
        }
    }

    @Test
    public void givenInvalidCustomerEmailFormat_whenCreateOrder_thenReturns400() {

        try (Response response =
                     orderResourceIntegrationTestFixture
                             .withPath(String.format(CREATE_ORDER_PATH, RESTAURANT_ID))
                             .withCustomerEmail("invalid")
                             .buildPostOrderResponse(target())) {
            assertEquals(HTTP_BAD_REQUEST, response.getStatus());
            String body = response.readEntity(String.class);
            assertTrue(body.contains(INVALID_PARAMETER_ERROR) && body.contains("email"));
        }
    }

    @Test
    public void givenExistingOrder_whenGetOrderById_thenReturns200() {
        when(orderService.getOrderById(ORDER_ID)).thenReturn(mock(OrderResponseDto.class));

        try(Response response = orderResourceIntegrationTestFixture.withPath(String.format(GET_ORDER_PATH, ORDER_ID)).buildGetResponse(target())) {
            assertEquals(HTTP_OK, response.getStatus());
            assertThat(response.getEntity()).isNotNull();
            verify(orderService).getOrderById(eq(ORDER_ID));
        }
    }

    @Test
    public void givenNoExistingOrder_whenGetOrderById_thenReturns404() {
        when(orderService.getOrderById(ORDER_ID)).thenThrow(new NotFoundException());

        try(Response response = orderResourceIntegrationTestFixture.withPath(String.format(GET_ORDER_PATH, ORDER_ID)).buildGetResponse(target())) {
            assertEquals(HTTP_NOT_FOUND, response.getStatus());
            verify(orderService).getOrderById(eq(ORDER_ID));
        }
    }
}
