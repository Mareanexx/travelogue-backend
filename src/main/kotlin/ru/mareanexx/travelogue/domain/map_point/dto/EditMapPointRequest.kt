package ru.mareanexx.travelogue.domain.map_point.dto

import java.time.OffsetDateTime

data class EditMapPointRequest(
    val id: Int,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val name: String? = null,
    val description: String? = null,
    val arrivalDate: OffsetDateTime? = null,
)