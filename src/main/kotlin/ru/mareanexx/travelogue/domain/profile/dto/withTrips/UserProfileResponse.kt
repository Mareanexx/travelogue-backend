package ru.mareanexx.travelogue.domain.profile.dto.withTrips

import ru.mareanexx.travelogue.domain.profile.dto.ProfileDTO
import ru.mareanexx.travelogue.domain.trip.dto.UserTrip

data class UserProfileResponse(
    val profile: ProfileDTO,
    val trips: List<UserTrip>
)