package ca.ulaval.glo2003.api.dto.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OrderDto(
    @NotNull(message = "The date of the order is required") LocalDate date,
    @NotNull(message = "The menu of the order is required")
        @NotBlank(message = "The menu of the order cannot be blank")
        String menu,
    @Valid @NotNull(message = "Missing customer body") CustomerDto customer) {}
