package ca.ulaval.glo2003.api.dto.response;

import ca.ulaval.glo2003.api.dto.common.HoursDto;

public record OrderRestaurantResponseDto(String id, String name, HoursDto hour) {}
