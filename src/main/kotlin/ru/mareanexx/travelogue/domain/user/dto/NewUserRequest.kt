package ru.mareanexx.travelogue.domain.user.dto

import ru.mareanexx.travelogue.domain.user.types.UserRole

data class NewUserRequest(
    val email: String,
    val password: String,
    val role: UserRole
)