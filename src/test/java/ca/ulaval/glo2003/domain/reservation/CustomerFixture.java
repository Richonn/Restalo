package ca.ulaval.glo2003.domain.reservation;

public class CustomerFixture {

  private String NAME = "John Deer";
  private String EMAIL = "john.deer@gmail.com";
  private String PHONE_NUMBER = "1234567890";

  public Customer build() {
    return new Customer(NAME, EMAIL, PHONE_NUMBER);
  }

  public CustomerFixture withName(String name) {
    NAME = name;
    return this;
  }

  public CustomerFixture withEmail(String email) {
    EMAIL = email;
    return this;
  }

  public CustomerFixture withPhoneNumber(String PhoneNumber) {
    PHONE_NUMBER = PhoneNumber;
    return this;
  }
}
