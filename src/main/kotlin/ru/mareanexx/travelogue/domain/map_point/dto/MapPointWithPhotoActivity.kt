package ru.mareanexx.travelogue.domain.map_point.dto

import java.time.OffsetDateTime

data class MapPointWithPhotoActivity(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val arrivalDate: OffsetDateTime,
    val tripId: Int,
    val previewPhotoPath: String
)