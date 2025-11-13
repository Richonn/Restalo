package ca.ulaval.glo2003.infra.persistence.mongo.documents;

import dev.morphia.annotations.Embedded;

@Embedded
public class CustomerDocument {
  private String name;
  private String email;
  private String phoneNumber;

  public CustomerDocument() {}

  public CustomerDocument(String name, String email, String phoneNumber) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
