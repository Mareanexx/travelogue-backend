package ru.mareanexx.travelogue.support.utils.test.requests

data class TestProfileRequest(
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String,
    val cover: String,

    val followersNumber: Int = 0,
    val followingNumber: Int = 0,
    val tripsNumber: Int = 0
)