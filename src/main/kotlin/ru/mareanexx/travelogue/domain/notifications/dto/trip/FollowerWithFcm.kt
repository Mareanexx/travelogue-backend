package ru.mareanexx.travelogue.domain.notifications.dto.trip

data class FollowerWithFcm(
    val profileId: Int,
    val fcmToken: String?
)