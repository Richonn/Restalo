package ca.ulaval.glo2003.api.ressources.specialOffer;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;

public class SpecialOfferResourceIntegrationTestFixture {

  private String VALID_FROM = LocalDate.now().toString();
  private String VALID_UNTIL = LocalDate.now().plusDays(2).toString();
  private String TITLE = "Offer";
  private String DESCRIPTION = "Description";

  private String OWNER_ID = "ownerId";
  private String PATH = "";
  private Boolean INCLUDE_HEADER = true;
  private Boolean INCLUDE_BODY = true;
  private Boolean INCLUDE_OFFER = true;

  public SpecialOfferResourceIntegrationTestFixture(String ownerId) {
    this.OWNER_ID = ownerId;
  }

  public Response buildPostSpecialOfferResponse(WebTarget target) {
    return buildPostResponse(target, buildSpecialOfferJson());
  }

  public SpecialOfferResourceIntegrationTestFixture withTitle(String title) {
    this.TITLE = title;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withValidFrom(String validFrom) {
    this.VALID_FROM = validFrom;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withValidUntil(String validUntil) {
    this.VALID_UNTIL = validUntil;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withDescription(String description) {
    this.DESCRIPTION = description;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withPath(String path) {
    this.PATH = path;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withOwnerId(String ownerId) {
    this.OWNER_ID = ownerId;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withIncludeBody(boolean includeBody) {
    this.INCLUDE_BODY = includeBody;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withIncludeOfferBody(boolean includeOffer) {
    this.INCLUDE_OFFER = includeOffer;
    return this;
  }

  public SpecialOfferResourceIntegrationTestFixture withIncludeHeaders(boolean includeHeaders) {
    this.INCLUDE_HEADER = includeHeaders;
    return this;
  }

  private Response buildPostResponse(WebTarget target, String json) {
    Invocation.Builder requestBuilder = target.path(PATH).request(MediaType.APPLICATION_JSON);

    if (INCLUDE_HEADER) {
      requestBuilder = requestBuilder.header("Owner", OWNER_ID);
    }
    if (INCLUDE_BODY) {
      return requestBuilder.post(Entity.json(json));
    }

    return requestBuilder.post(Entity.entity("null", MediaType.APPLICATION_JSON_TYPE));
  }

  private String buildSpecialOfferJson() {
    String offerJson = "";
    if (INCLUDE_OFFER) {
      offerJson =
          """
                        "offer": {
                          "title": "%s",
                          "description": "%s",
                          "validFrom": "%s",
                          "validUntil": "%s"
                        }"""
              .formatted(TITLE, DESCRIPTION, VALID_FROM, VALID_UNTIL);
    }
    return """
            {
              %s
            }
            """
        .formatted(offerJson);
  }
}
