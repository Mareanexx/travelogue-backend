package ru.mareanexx.travelogue.domain.trip.dto

import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDate

data class NewTripRequest(
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val profileId: Int, // FK на user's profile
    val tagList: List<NewTag>?
)