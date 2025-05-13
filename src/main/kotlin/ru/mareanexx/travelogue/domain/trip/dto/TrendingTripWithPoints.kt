package ru.mareanexx.travelogue.domain.trip.dto

import ru.mareanexx.travelogue.domain.map_point.dto.MapPointWithPhotoActivity
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import java.time.LocalDate

data class TrendingTripWithPoints(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val stepsNumber: Int,
    val daysNumber: Int,
    val status: TripTimeStatus,
    val coverPhoto: String,

    val profileId: Int,
    val username: String,
    val avatar: String?,

    val mapPoints: List<MapPointWithPhotoActivity>
)