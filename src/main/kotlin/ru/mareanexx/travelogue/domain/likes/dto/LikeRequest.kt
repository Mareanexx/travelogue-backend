package ru.mareanexx.travelogue.domain.likes.dto

data class LikeRequest(
    val profileId: Int,
    val mapPointId: Int
)