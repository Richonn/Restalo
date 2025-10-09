package ca.ulaval.glo2003.domain;

public class RestaurantDomainServiceFixture {

  private String OWNER_ID = "132";

  public String build() {
    return OWNER_ID;
  }

  public RestaurantDomainServiceFixture withOwnerID(String ownerID) {
    OWNER_ID = ownerID;
    return this;
  }
}
