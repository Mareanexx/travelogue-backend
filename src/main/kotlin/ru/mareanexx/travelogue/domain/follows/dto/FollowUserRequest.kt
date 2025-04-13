package ru.mareanexx.travelogue.domain.follows.dto

data class FollowUserRequest(
    val followerId: Int,
    val followingId: Int
)