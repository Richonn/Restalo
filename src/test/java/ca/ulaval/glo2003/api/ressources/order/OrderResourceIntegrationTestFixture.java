package ca.ulaval.glo2003.api.ressources.order;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

public class OrderResourceIntegrationTestFixture {

    private String DATE = LocalDate.now().toString();
    private String MENU = "menu";
    private String CUSTOMER_NAME = "John Deer";
    private String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private String CUSTOMER_PHONE = "1234567890";

    private String PATH = "";
    private Boolean INCLUDE_BODY = true;
    private Boolean INCLUDE_CUSTOMER = true;


    public Response buildPostOrderResponse(WebTarget target) {
        return buildPostResponse(target, buildOrderJson());
    }

    public OrderResourceIntegrationTestFixture withOrderMenu(String menu) {
        this.MENU = menu;
        return this;
    }

    public OrderResourceIntegrationTestFixture withOrderDate(String date) {
        this.DATE = date;
        return this;
    }

    public OrderResourceIntegrationTestFixture withCustomerName(String customerName) {
        this.CUSTOMER_NAME = customerName;
        return this;
    }

    public OrderResourceIntegrationTestFixture withCustomerEmail(String customerEmail) {
        this.CUSTOMER_EMAIL = customerEmail;
        return this;
    }

    public OrderResourceIntegrationTestFixture withPath(String path) {
        this.PATH = path;
        return this;
    }

    public OrderResourceIntegrationTestFixture withIncludeBody(boolean includeBody) {
        this.INCLUDE_BODY = includeBody;
        return this;
    }

    public OrderResourceIntegrationTestFixture withIncludeCustomer(boolean includeCustomer) {
        this.INCLUDE_CUSTOMER = includeCustomer;
        return this;
    }

    private Response buildPostResponse(WebTarget target, String json) {
        Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);

        if (INCLUDE_BODY) {
            return requestBuilder.post(Entity.json(json));
        }
        return requestBuilder.post(Entity.entity("null", MediaType.APPLICATION_JSON_TYPE));
    }

    private String buildOrderJson() {
        String customerJson = "";
        if (INCLUDE_CUSTOMER) {
            customerJson =
                    """
                        ,
                        "customer": {
                          "name": "%s",
                          "email": "%s",
                          "phoneNumber": "%s"
                        }"""
                            .formatted(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE);
        }
        return """
            {
              "date": "%s",
              "menu": "%s"%s
            }
            """
                .formatted(DATE, MENU, customerJson);
    }
}
