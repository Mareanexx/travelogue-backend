package ru.mareanexx.travelogue.domain.trip.dto

import ru.mareanexx.travelogue.domain.tags.dto.TagResponse
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDate

data class TripResponse(
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
    var tagList: List<TagResponse>? = null
)