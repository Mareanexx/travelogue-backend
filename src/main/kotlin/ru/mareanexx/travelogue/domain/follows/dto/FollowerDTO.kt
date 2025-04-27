package ru.mareanexx.travelogue.domain.follows.dto

data class Follows(
    val id: Int,
    val username: String,
    val avatar: String?,
    val bio: String,
    val isFollowingBack: Boolean = false
)