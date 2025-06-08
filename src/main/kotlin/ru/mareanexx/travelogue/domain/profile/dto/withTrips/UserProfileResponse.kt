package ru.mareanexx.travelogue.domain.profile.dto.withTrips

import ru.mareanexx.travelogue.domain.trip.dto.TripWithoutTags

data class UserProfileResponse(
    val profile: OthersProfile,
    val trips: List<TripWithoutTags>
)

data class OthersProfile(
    val id: Int,
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String?,
    val coverPhoto: String?,
    val followersNumber: Int,
    val followingNumber: Int,
    val tripsNumber: Int,
    val isFollowing: Boolean
)