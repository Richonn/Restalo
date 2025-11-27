package ca.ulaval.glo2003.api.assembler;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;
import ca.ulaval.glo2003.domain.reservation.Customer;

public class CustomerAssembler {
  public static CustomerDto toCustomerDto(Customer customer) {
    return new CustomerDto(customer.getName(), customer.getEmail(), customer.getPhoneNumber());
  }
}
