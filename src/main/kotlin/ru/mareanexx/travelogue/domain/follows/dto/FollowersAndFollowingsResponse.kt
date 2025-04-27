package ru.mareanexx.travelogue.domain.follows.dto

data class FollowersAndFollowingsResponse(
    val followers: List<Follows>,
    val followings: List<Follows>
)