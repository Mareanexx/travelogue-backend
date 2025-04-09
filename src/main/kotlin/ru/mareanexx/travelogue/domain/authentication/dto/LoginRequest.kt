package ru.mareanexx.travelogue.domain.authentication.dto

data class LoginRequest(
    val email: String,
    val password: String
)