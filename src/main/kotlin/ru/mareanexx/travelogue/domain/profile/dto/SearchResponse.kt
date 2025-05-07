package ru.mareanexx.travelogue.domain.profile.dto

data class SearchResponse(
    val profiles: List<SearchProfile>,
    val trips: List<SearchTrip>
)

data class SearchProfile(
    val id: Int,
    val avatar: String?,
    val username: String,
    val bio: String,
    val isFollowing: Boolean
)

data class SearchTrip(
    val id: Int,
    val coverPhoto: String,
    val name: String,
    val profileId: Int,
    val avatar: String?,
    val username: String
)