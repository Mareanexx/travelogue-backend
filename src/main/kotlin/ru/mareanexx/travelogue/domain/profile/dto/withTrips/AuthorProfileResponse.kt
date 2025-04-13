package ru.mareanexx.travelogue.domain.profile.dto.withTrips

import ru.mareanexx.travelogue.domain.profile.dto.ProfileDTO
import ru.mareanexx.travelogue.domain.trip.dto.AuthorTrip

data class AuthorProfileResponse(
    val profile: ProfileDTO,
    val trips: List<AuthorTrip>
)