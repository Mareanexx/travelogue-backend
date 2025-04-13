package ru.mareanexx.travelogue.domain.profile.dto.fcm

data class UpdateTokenRequest(
    val profileId: Int,
    val fcmToken: String?
)