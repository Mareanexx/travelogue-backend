package ru.mareanexx.travelogue.domain.notifications.dto

data class RecipientDTO(
    val id: Int,
    val fcmToken: String?,
    val tripId: Int
)