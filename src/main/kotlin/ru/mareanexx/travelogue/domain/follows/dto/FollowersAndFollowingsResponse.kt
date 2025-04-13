package ru.mareanexx.travelogue.domain.follows.dto

data class FollowersAndFollowingsResponse(
    val followers: List<FollowerDTO>,
    val followings: List<FollowingDTO>
)