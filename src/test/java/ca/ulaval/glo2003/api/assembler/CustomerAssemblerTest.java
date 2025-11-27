package ca.ulaval.glo2003.api.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.domain.reservation.Customer;
import org.junit.jupiter.api.Test;

public class CustomerAssemblerTest {

  private final String NAME = "John Deer";
  private final String EMAIL = "john.deer@gmail.com";
  private final String PHONE = "1234567890";

  private final Customer customer = new Customer(NAME, EMAIL, PHONE);

  @Test
  void givenValidCustomer_whenToCustomerDto_thenMapsCorrectly() {
    CustomerDto dto = CustomerAssembler.toCustomerDto(customer);

    assertThat(dto.name()).isEqualTo(NAME);
    assertThat(dto.email()).isEqualTo(EMAIL);
    assertThat(dto.phoneNumber()).isEqualTo(PHONE);
  }
}
