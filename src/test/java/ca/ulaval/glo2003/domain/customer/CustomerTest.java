package ca.ulaval.glo2003.domain.customer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {

  private static CustomerFixture customerFixture;

  @BeforeEach
  public void setUp() {
    customerFixture = new CustomerFixture();
  }

  @Test
  void givenValidCustomer_whenValidateFields_thenNoExceptions() {
    Customer a_customer = customerFixture.build();

    assertDoesNotThrow(a_customer::validateFields);
  }

  @Test
  void givenNullName_whenValidateFields_thenThrowsMissingParameterException() {
    Customer a_customer = customerFixture.withName(null).build();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The name of the customer is required");
  }

  @Test
  void givenNullEmail_whenValidateFields_thenThrowsMissingParameterException() {
    Customer a_customer = customerFixture.withEmail(null).build();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The email of the customer is required");
  }

  @Test
  void givenNullPhoneNumber_whenValidateFields_thenThrowsMissingParameterException() {
    Customer a_customer = customerFixture.withPhoneNumber(null).build();

    MissingParameterException exception =
        assertThrows(MissingParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The phone number of the customer is required");
  }

  @Test
  void givenInvalidName_whenValidateFields_thenThrowsInvalidParameterException() {
    Customer a_customer = customerFixture.withName("").build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The name of the customer cannot be blank");
  }

  @Test
  void givenBlankEmail_whenValidateFields_thenThrowsInvalidParameterException() {
    Customer a_customer = customerFixture.withEmail("").build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The email of the customer cannot be blank");
  }

  @Test
  void givenBlankPhoneNumber_whenValidateFields_thenThrowsInvalidParameterException() {
    Customer a_customer = customerFixture.withPhoneNumber("").build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage())
        .isEqualTo("The phone number of the customer cannot be blank");
  }

  @Test
  void givenInvalidEmail_whenValidateFields_thenThrowsInvalidParameterException() {
    Customer a_customer = customerFixture.withEmail("john!.deer@gmail.com").build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The email address of the customer is not valid");
  }

  @Test
  void givenInvalidPhoneNumber_whenValidateFields_thenThrowsInvalidParameterException() {
    Customer a_customer = customerFixture.withPhoneNumber("01234567890").build();

    InvalidParameterException exception =
        assertThrows(InvalidParameterException.class, a_customer::validateFields);

    assertThat(exception).isInstanceOf(InvalidParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("The phone number of the customer is not valid");
  }
}
