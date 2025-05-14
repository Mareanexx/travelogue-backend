package ru.mareanexx.travelogue.domain.profile.dto

import jakarta.validation.constraints.NotBlank
import java.util.*

data class NewProfileRequest(
    @field:NotBlank(message = "Username must not be blank")
    val username: String,

    @field:NotBlank(message = "Full name must not be blank")
    val fullName: String,
    @field:NotBlank(message = "Bio must not be blank")
    val bio: String,

    val userUUID: UUID, // FK на user
)