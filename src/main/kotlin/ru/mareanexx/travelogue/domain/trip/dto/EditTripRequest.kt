package ru.mareanexx.travelogue.domain.trip.dto

import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDate

data class EditTripRequest(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate?,
    val type: TripVisibilityType? = null,
    val status: TripTimeStatus? = null,
    var tagList: List<NewTag>? = null
)