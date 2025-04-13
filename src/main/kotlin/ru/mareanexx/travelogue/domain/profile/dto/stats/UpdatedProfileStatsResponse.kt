package ru.mareanexx.travelogue.domain.profile.dto.stats

data class UpdatedProfileStatsResponse(
    val tripsNumber: Int,
    val followersNumber: Int,
    val followingNumber: Int
)