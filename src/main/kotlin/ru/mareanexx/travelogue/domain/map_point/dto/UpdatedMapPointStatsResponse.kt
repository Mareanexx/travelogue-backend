package ru.mareanexx.travelogue.domain.map_point.dto

data class UpdatedMapPointStatsResponse(
    val id: Int,
    val likesNumber: Int,
    val commentsNumber: Int
)