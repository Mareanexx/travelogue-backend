package ru.mareanexx.travelogue.domain.map_point.projection

import java.time.OffsetDateTime

interface MapPointWithPreviewProjection {
    fun id(): Int
    fun longitude(): Double
    fun latitude(): Double
    fun arrivalDate(): OffsetDateTime
    fun tripId(): Int
    fun previewPhoto(): String
}