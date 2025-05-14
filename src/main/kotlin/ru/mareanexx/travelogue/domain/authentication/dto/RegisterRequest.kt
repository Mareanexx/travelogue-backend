package ru.mareanexx.travelogue.domain.authentication.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    @Size(min = 6, max = 250, message = "Email cant be that long")
    val email: String,

    @Size(min = 6, max = 30, message = "Password must be at least 6 characters long")
    val password: String
)