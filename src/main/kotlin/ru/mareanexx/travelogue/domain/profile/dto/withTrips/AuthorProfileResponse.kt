package ru.mareanexx.travelogue.domain.profile.dto.withTrips

import ru.mareanexx.travelogue.domain.profile.dto.ProfileDTO
import ru.mareanexx.travelogue.domain.tags.dto.TagResponse
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDate

data class AuthorProfileResponse(
    val profile: ProfileDTO,
    val trips: List<AuthorTrip>
)

data class AuthorTrip(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val stepsNumber: Int,
    val daysNumber: Int,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String,
    val profileId: Int,
    val tagList: List<TagResponse>?
)