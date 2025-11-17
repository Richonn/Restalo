package ca.ulaval.glo2003.api.dto.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CustomerDto(
    @NotNull(message = "The name of the customer is required")
        @NotBlank(message = "The name of the customer cannot be blank")
        String name,
    @NotNull(message = "The email of the customer is required")
        @Email(message = "The email address of the customer is not valid")
        String email,
    @NotNull(message = "The phone number of the customer is required")
        @Pattern(regexp = "^\\d{10}$", message = "The phone number of the customer is not valid")
        String phoneNumber) {}
