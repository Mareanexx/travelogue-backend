package ru.mareanexx.travelogue.domain.notifications.dto

data class RecipientDTO(
    val profileId: Int,
    val fcmToken: String?,
    val tripId: Int
)