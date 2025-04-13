package ru.mareanexx.travelogue.domain.profile.dto

import java.util.*

data class NewProfileRequest(
    val username: String,
    val fullName: String,
    val bio: String,
    val userUUID: UUID // FK на user
)