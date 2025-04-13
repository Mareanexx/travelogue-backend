package ru.mareanexx.travelogue.domain.map_point.dto

import java.time.LocalDateTime

data class NewMapPointRequest(
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val arrivalDate: LocalDateTime,
    val tripId: Int
)