package ru.mareanexx.travelogue.domain.authentication.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    val email: String,

    @Size(min = 6, max = 30, message = "Password must be at least 6 characters long")
    val password: String
)