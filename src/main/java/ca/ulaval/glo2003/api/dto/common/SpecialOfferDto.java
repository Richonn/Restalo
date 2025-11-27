package ca.ulaval.glo2003.api.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SpecialOfferDto(
    @NotNull(message = "The title of the offer is required")
        @NotBlank(message = "The title of the offer cannot be blank")
        String title,
    @NotNull(message = "The description of the offer is required")
        @NotBlank(message = "The description of the offer cannot be blank")
        String description,
    @NotNull(message = "The starting date of the offer is required")
        @NotBlank(message = "The starting date of the offer cannot be blank")
        LocalDate validFrom,
    @NotNull(message = "The ending date of the offer is required")
        @NotBlank(message = "The ending date of the offer cannot be blank")
        LocalDate validUntil) {}
