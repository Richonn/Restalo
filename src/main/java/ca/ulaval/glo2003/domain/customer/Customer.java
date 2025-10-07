package ca.ulaval.glo2003.domain.customer;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.util.regex.Pattern;

public class Customer {

  private final String name;
  private final String email;
  private final String phoneNumber;
  private static final String EMAIL_REGEX =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
  private static final String PHONE_NUMBER_REGEX = "^\\d{10}$";
  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

  public Customer(String name, String email, String phoneNumber) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public void validateFields() {
    validateFieldsAreDefined();
    validateFieldsAreValid();
  }

  private void validateFieldsAreDefined() {
    if (name == null) {
      throw new MissingParameterException("The name of the customer is required");
    }
    if (email == null) {
      throw new MissingParameterException("The email of the customer is required");
    }
    if (phoneNumber == null) {
      throw new MissingParameterException("The phone number of the customer is required");
    }
  }

  private void validateFieldsAreValid() {
    if (name.isBlank()) {
      throw new InvalidParameterException("The name of the customer cannot be blank");
    }
    if (email.isBlank()) {
      throw new InvalidParameterException("The email of the customer cannot be blank");
    }
    if (phoneNumber.isBlank()) {
      throw new InvalidParameterException("The phone number of the customer cannot be blank");
    }
    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new InvalidParameterException("The email address of the customer is not valid");
    }
    if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
      throw new InvalidParameterException("The phone number of the customer is not valid");
    }
  }
}
