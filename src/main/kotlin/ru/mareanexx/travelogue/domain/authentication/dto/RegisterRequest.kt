package ru.mareanexx.travelogue.domain.authentication.dto

data class RegisterRequest(
    val email: String,
    val password: String
)