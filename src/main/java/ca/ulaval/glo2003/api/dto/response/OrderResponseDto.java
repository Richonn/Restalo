package ca.ulaval.glo2003.api.dto.response;

import ca.ulaval.glo2003.api.dto.common.CustomerDto;

public record OrderResponseDto(
    String number,
    String date,
    String menu,
    CustomerDto customer,
    OrderRestaurantResponseDto restaurant) {}
