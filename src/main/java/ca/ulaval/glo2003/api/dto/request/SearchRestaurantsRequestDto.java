package ca.ulaval.glo2003.api.dto.request;

import ca.ulaval.glo2003.api.dto.common.OpenedHoursDto;

public record SearchRestaurantsRequestDto(String name, OpenedHoursDto opened) {}
