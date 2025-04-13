package ru.mareanexx.travelogue.domain.profile.dto

data class InspiringProfileResponse(
    val id: Int,
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String?,
    val coverPhoto: String?,
    val followersNumber: Int,
    val followingNumber: Int,
    val tripsNumber: Int,
)