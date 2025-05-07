package ru.mareanexx.travelogue.domain.map_point.dto

import java.time.OffsetDateTime

data class NewMapPointRequest(
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val arrivalDate: OffsetDateTime,
    val tripId: Int
)