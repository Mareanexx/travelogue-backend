package ru.mareanexx.travelogue.domain.follows.dto

data class FollowerDTO(
    val id: Int,
    val username: String,
    val avatar: String?,
    val bio: String
)

data class FollowingDTO(
    val id: Int,
    val username: String,
    val avatar: String?,
    val bio: String
)