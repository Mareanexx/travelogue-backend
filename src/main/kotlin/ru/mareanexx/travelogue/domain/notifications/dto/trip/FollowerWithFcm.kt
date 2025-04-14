package ru.mareanexx.travelogue.domain.notifications.dto.trip

data class FollowerWithFcm(
    val id: Int,
    val fcmToken: String?
)