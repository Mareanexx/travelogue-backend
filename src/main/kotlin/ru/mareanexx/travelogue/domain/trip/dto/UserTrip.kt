package ru.mareanexx.travelogue.domain.trip.dto

import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import java.time.LocalDate

data class UserTrip(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val stepsNumber: Int,
    val daysNumber: Int,
    val status: TripTimeStatus,
    val coverPhoto: String
)