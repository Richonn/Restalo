package ca.ulaval.glo2003.domain.restaurant;

public class RestaurantFixture {

  private String NAME = "a_name";
  private Integer CAPACITY = 2;
  private String OWNERID = "a_id";

  public Restaurant build(Hours hours) {
    return new Restaurant(NAME, CAPACITY, hours, OWNERID);
  }

  public RestaurantFixture withName(String name) {
    NAME = name;
    return this;
  }

  public RestaurantFixture withCapacity(Integer capacity) {
    CAPACITY = capacity;
    return this;
  }

  public RestaurantFixture withOwnerId(String ownerId) {
    OWNERID = ownerId;
    return this;
  }
}
