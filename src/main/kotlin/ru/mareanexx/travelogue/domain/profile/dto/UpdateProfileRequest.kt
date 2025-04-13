package ru.mareanexx.travelogue.domain.profile.dto

data class UpdateProfileRequest(
    val id: Int,
    val username: String? = null,
    val fullName: String? = null,
    val bio: String? = null,
)