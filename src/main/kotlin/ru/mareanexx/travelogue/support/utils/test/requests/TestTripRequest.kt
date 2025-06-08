package ru.mareanexx.travelogue.support.utils.test.requests

import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDate

data class TestTripRequest(
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,

    val stepsNumber: Int = 0,
    val daysNumber: Int = 0,

    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String,
    val tagList: List<NewTag>
)