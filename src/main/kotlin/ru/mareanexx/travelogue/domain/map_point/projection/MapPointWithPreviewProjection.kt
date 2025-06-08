package ru.mareanexx.travelogue.domain.map_point.projection

import java.time.OffsetDateTime

data class MapPointWithPreviewProjection(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val arrivalDate: OffsetDateTime,
    val tripId: Int,
    val previewPhoto: String
)