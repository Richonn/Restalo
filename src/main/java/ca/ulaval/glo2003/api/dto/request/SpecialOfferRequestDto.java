package ca.ulaval.glo2003.api.dto.request;

import ca.ulaval.glo2003.api.dto.common.SpecialOfferDto;
import jakarta.validation.constraints.NotNull;

public record SpecialOfferRequestDto(@NotNull(message = "Missing offer") SpecialOfferDto offer) {}
