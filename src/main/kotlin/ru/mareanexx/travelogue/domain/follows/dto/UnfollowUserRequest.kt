package ru.mareanexx.travelogue.domain.follows.dto

data class UnfollowUserRequest(
    val followerId: Int,
    val followingId: Int
)