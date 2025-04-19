package ru.mareanexx.travelogue.domain.authentication.dto

import java.util.*

data class AuthResponse(
    val userUuid: UUID,
    val email: String,
    val token: String
)